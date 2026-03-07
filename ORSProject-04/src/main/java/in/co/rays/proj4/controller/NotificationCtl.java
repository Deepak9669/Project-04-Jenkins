package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.NotificationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.NotificationModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * NotificationCtl handles add, update, view and validation of Notification.
 */

@WebServlet(name = "NotificationCtl", urlPatterns = { "/ctl/NotificationCtl" })
public class NotificationCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(NotificationCtl.class);

	/**
	 * Validate Notification Form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("NotificationCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("notificationCode"))) {
			request.setAttribute("notificationCode",
					PropertyReader.getValue("error.require", "Notification Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("message"))) {
			request.setAttribute("message",
					PropertyReader.getValue("error.require", "Message"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("sentTo"))) {
			request.setAttribute("sentTo",
					PropertyReader.getValue("error.require", "Sent To"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("sentTime"))) {
			request.setAttribute("sentTime",
					PropertyReader.getValue("error.require", "Sent Time"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("sentTime"))) {
			request.setAttribute("sentTime",
					PropertyReader.getValue("error.date", "Sent Time"));
			pass = false;
		}

		log.debug("NotificationCtl validate completed : " + pass);
		return pass;
	}

	/**
	 * Populate Bean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("NotificationCtl populateBean started");

		NotificationBean bean = new NotificationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setNotificationCode(
				DataUtility.getString(request.getParameter("notificationCode")));
		bean.setMessage(DataUtility.getString(request.getParameter("message")));
		bean.setSentTo(DataUtility.getString(request.getParameter("sentTo")));
		bean.setSentTime(
				DataUtility.getDate(request.getParameter("sentTime")));

		populateDTO(bean, request);

		log.debug("NotificationCtl populateBean completed");
		return bean;
	}

	/**
	 * Load Notification
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("NotificationCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));
		NotificationModel model = new NotificationModel();

		if (id > 0) {
			try {
				NotificationBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (ApplicationException e) {
				log.error("Error in NotificationCtl doGet", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Handle POST Operations
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));
		long id = DataUtility.getLong(req.getParameter("id"));

		log.info("NotificationCtl doPost operation : " + op);

		NotificationModel model = new NotificationModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			NotificationBean bean = (NotificationBean) populateBean(req);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage(
						"Notification added successfully", req);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage(
						"Notification already exists", req);

			} catch (ApplicationException e) {
				log.error("Error while adding notification", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			NotificationBean bean = (NotificationBean) populateBean(req);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage(
						"Notification updated successfully", req);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage(
						"Notification already exists", req);

			} catch (ApplicationException e) {
				log.error("Error while updating notification", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.NOTIFICATION_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.NOTIFICATION_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Get View
	 */
	@Override
	protected String getView() {
		return ORSView.NOTIFICATION_VIEW;
	}
}