package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DeliveryTrackingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.DeliveryTrackingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DeliveryTrackingCtl", urlPatterns = { "/ctl/DeliveryTrackingCtl" })
public class DeliveryTrackingCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DeliveryTrackingCtl.class);

	/**
	 * Preload dropdown values
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("DeliveryTrackingCtl preload started");

		HashMap<String, String> statusMap = new HashMap<>();

		statusMap.put("Pending", "Pending");
		statusMap.put("Shipped", "Shipped");
		statusMap.put("Delivered", "Delivered");
		statusMap.put("Cancelled", "Cancelled");

		request.setAttribute("statusMap", statusMap);

		log.debug("DeliveryTrackingCtl preload completed");
	}

	/**
	 * Validate form data
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("DeliveryTrackingCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("orderNumber"))) {
			request.setAttribute("orderNumber",
					PropertyReader.getValue("error.require", "Order Number"));
			pass = false;
		} else if (! DataValidator.isInteger(request.getParameter("orderNumber"))) {
			request.setAttribute("orderNumber",
					PropertyReader.getValue("error.require", "Only Integer Number"));
			pass = false;
			
		}

		if (DataValidator.isNull(request.getParameter("customerName"))) {
			request.setAttribute("customerName",
					PropertyReader.getValue("error.require", "Customer Name"));
			pass = false;
		} else if (! DataValidator.isName(request.getParameter("customerName"))) {
			request.setAttribute("customerName",
					PropertyReader.getValue("error.require", "Only alphavetical value"));
			pass = false;
			
		}

		if (DataValidator.isNull(request.getParameter("deliveryStatus"))) {
			request.setAttribute("deliveryStatus",
					PropertyReader.getValue("error.require", "Delivery Status"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("deliveryDate"))) {
			request.setAttribute("deliveryDate",
					PropertyReader.getValue("error.require", "Delivery Date"));
			pass = false;
		}

		log.debug("DeliveryTrackingCtl validate completed : " + pass);
		return pass;
	}

	/**
	 * Populate DeliveryTrackingBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("DeliveryTrackingCtl populateBean started");

		DeliveryTrackingBean bean = new DeliveryTrackingBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setOrderNumber(DataUtility.getInt(request.getParameter("orderNumber")));
		bean.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
		bean.setDeliveryStatus(DataUtility.getString(request.getParameter("deliveryStatus")));
		bean.setDeliveryDate(DataUtility.getDate(request.getParameter("deliveryDate")));

		populateDTO(bean, request);

		log.debug("DeliveryTrackingCtl populateBean completed");

		return bean;
	}

	/**
	 * Load record by PK
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("DeliveryTrackingCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));

		DeliveryTrackingModel model = new DeliveryTrackingModel();

		if (id > 0) {

			try {

				DeliveryTrackingBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, req);

			} catch (ApplicationException e) {

				log.error("Error in DeliveryTrackingCtl doGet", e);

				ServletUtility.handleException(e, req, resp);

				return;

			}

		}

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * Handle POST operations
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("DeliveryTrackingCtl doPost started");

		String op = DataUtility.getString(req.getParameter("operation"));

		long id = DataUtility.getLong(req.getParameter("id"));

		DeliveryTrackingModel model = new DeliveryTrackingModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			DeliveryTrackingBean bean = (DeliveryTrackingBean) populateBean(req);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, req);

				ServletUtility.setSuccessMessage("Delivery Tracking added successfully", req);

			} catch (ApplicationException e) {

				log.error("Error while adding DeliveryTracking", e);

				ServletUtility.handleException(e, req, resp);

				return;

			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			DeliveryTrackingBean bean = (DeliveryTrackingBean) populateBean(req);

			try {

				if (id > 0) {

					model.update(bean);

				}

				ServletUtility.setBean(bean, req);

				ServletUtility.setSuccessMessage("Delivery Tracking updated successfully", req);

			} catch (ApplicationException e) {

				log.error("Error while updating DeliveryTracking", e);

				ServletUtility.handleException(e, req, resp);

				return;

			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DELIVERY_TRACKING_CTL, req, resp);

			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DELIVERY_TRACKING_LIST_CTL, req, resp);

			return;

		}

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * Return view page
	 */
	@Override
	protected String getView() {

		return ORSView.DELIVERY_TRACKING_VIEW;

	}

}