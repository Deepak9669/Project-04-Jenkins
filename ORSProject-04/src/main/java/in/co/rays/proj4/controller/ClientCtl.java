package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ClientModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ClientCtl", urlPatterns = { "/ctl/ClientCtl" })
public class ClientCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ClientCtl.class);

	/**
	 * ================= PRELOAD =================
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("ClientCtl preload started");

		HashMap<String, String> priorityMap = new HashMap<>();
		priorityMap.put("High", "High");
		priorityMap.put("Medium", "Medium");
		priorityMap.put("Low", "Low");

		request.setAttribute("priorityMap", priorityMap);

		log.debug("ClientCtl preload completed");
	}

	/**
	 * ================= VALIDATE =================
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ClientCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("clientName"))) {
			request.setAttribute("clientName",
					PropertyReader.getValue("error.require", "Client Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address",
					PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("phone"))) {
			request.setAttribute("phone",
					PropertyReader.getValue("error.require", "Phone"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("priortiy"))) {
			request.setAttribute("priortiy",
					PropertyReader.getValue("error.require", "Priority"));
			pass = false;
		}

		log.debug("ClientCtl validate completed : " + pass);
		return pass;
	}

	/**
	 * ================= POPULATE BEAN =================
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ClientCtl populateBean started");

		ClientBean bean = new ClientBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setClientName(DataUtility.getString(request.getParameter("clientName")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setPhone(DataUtility.getString(request.getParameter("phone")));
		bean.setPriortiy(DataUtility.getString(request.getParameter("priortiy")));

		populateDTO(bean, request);

		log.debug("ClientCtl populateBean completed");
		return bean;
	}

	/**
	 * ================= DO GET =================
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("ClientCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));
		ClientModel model = new ClientModel();

		if (id > 0) {
			try {
				ClientBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (ApplicationException e) {
				log.error("Error in ClientCtl doGet", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * ================= DO POST =================
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));
		long id = DataUtility.getLong(req.getParameter("id"));

		log.info("ClientCtl doPost operation : " + op);

		ClientModel model = new ClientModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ClientBean bean = (ClientBean) populateBean(req);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Client added successfully", req);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Client already exists", req);

			} catch (ApplicationException e) {
				log.error("Error while adding Client", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			ClientBean bean = (ClientBean) populateBean(req);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Client updated successfully", req);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Client already exists", req);

			} catch (ApplicationException e) {
				log.error("Error while updating Client", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * ================= GET VIEW =================
	 */
	@Override
	protected String getView() {
		return ORSView.CLIENT_VIEW;
	}
}