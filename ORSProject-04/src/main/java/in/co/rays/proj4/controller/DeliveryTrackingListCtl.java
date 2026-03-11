package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * DeliveryTrackingListCtl handles listing, searching, deleting and pagination
 */
@WebServlet(name = "DeliveryTrackingListCtl", urlPatterns = { "/ctl/DeliveryTrackingListCtl" })
public class DeliveryTrackingListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DeliveryTrackingListCtl.class);

	/**
	 * Preload Delivery Status dropdown
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("DeliveryTrackingListCtl preload started");

		HashMap<String, String> statusMap = new HashMap<>();

		statusMap.put("Pending", "Pending");
		statusMap.put("Shipped", "Shipped");
		statusMap.put("Delivered", "Delivered");
		statusMap.put("Cancelled", "Cancelled");

		request.setAttribute("statusMap", statusMap);

		log.debug("DeliveryTrackingListCtl preload completed");
	}

	/**
	 * Populate bean for search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("DeliveryTrackingListCtl populateBean started");

		DeliveryTrackingBean bean = new DeliveryTrackingBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setOrderNumber(DataUtility.getInt(request.getParameter("orderNumber")));
		bean.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
		bean.setDeliveryStatus(DataUtility.getString(request.getParameter("deliveryStatus")));

		log.debug("DeliveryTrackingListCtl populateBean completed");

		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("DeliveryTrackingListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		DeliveryTrackingBean bean = (DeliveryTrackingBean) populateBean(req);

		DeliveryTrackingModel model = new DeliveryTrackingModel();

		try {

			List<DeliveryTrackingBean> list = model.search(bean, pageNo, pageSize);
			List<DeliveryTrackingBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			ServletUtility.setBean(bean, req);
			req.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), req, resp);

		} catch (ApplicationException e) {

			log.error("Error in DeliveryTrackingListCtl doGet", e);

			ServletUtility.handleException(e, req, resp);

		}
	}

	/**
	 * Handle POST request
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("DeliveryTrackingListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		DeliveryTrackingBean bean = (DeliveryTrackingBean) populateBean(req);

		DeliveryTrackingModel model = new DeliveryTrackingModel();

		String op = DataUtility.getString(req.getParameter("operation"));

		String[] ids = req.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)) {

				pageNo = 1;

			} else if (OP_NEXT.equalsIgnoreCase(op)) {

				pageNo++;

			} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {

				pageNo--;

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.DELIVERY_TRACKING_CTL, req, resp);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						model.delete(DataUtility.getLong(id));

					}

					ServletUtility.setSuccessMessage("Record deleted successfully", req);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", req);

				}

			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.DELIVERY_TRACKING_LIST_CTL, req, resp);

				return;

			}

			List<DeliveryTrackingBean> list = model.search(bean, pageNo, pageSize);

			List<DeliveryTrackingBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {

				ServletUtility.setErrorMessage("No record found", req);

			}

			ServletUtility.setBean(bean, req);

			ServletUtility.setList(list, req);

			ServletUtility.setPageNo(pageNo, req);

			ServletUtility.setPageSize(pageSize, req);

			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {

			log.error("Error in DeliveryTrackingListCtl doPost", e);

			ServletUtility.handleException(e, req, resp);

			return;

		}

		log.info("DeliveryTrackingListCtl doPost completed");

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * Return List View
	 */
	@Override
	protected String getView() {

		return ORSView.DELIVERY_TRACKING_LIST_VIEW;

	}
}