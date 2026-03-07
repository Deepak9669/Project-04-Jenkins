package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.NotificationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.NotificationModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * NotificationListCtl handles list, search, pagination and delete operations
 * for Notification.
 */

@WebServlet(name = "NotificationListCtl", urlPatterns = { "/ctl/NotificationListCtl" })
public class NotificationListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(NotificationListCtl.class);

	/**
	 * Populate NotificationBean for Search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("NotificationListCtl populateBean started");

		NotificationBean bean = new NotificationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setNotificationCode(
				DataUtility.getString(request.getParameter("notificationCode")));
		bean.setMessage(
				DataUtility.getString(request.getParameter("message")));
		bean.setSentTo(
				DataUtility.getString(request.getParameter("sentTo")));
		bean.setSentTime(
				DataUtility.getDate(request.getParameter("sentTime")));

		log.debug("NotificationListCtl populateBean completed");
		return bean;
	}

	/**
	 * Handles GET request
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("NotificationListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(
				PropertyReader.getValue("page.size"));

		NotificationBean bean = (NotificationBean) populateBean(req);
		NotificationModel model = new NotificationModel();

		try {

			List<NotificationBean> list =
					model.search(bean, pageNo, pageSize);

			List<NotificationBean> next =
					model.search(bean, pageNo + 1, pageSize);

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
			log.error("Error in NotificationListCtl doGet", e);
			ServletUtility.handleException(e, req, resp);
		}
	}

	/**
	 * Handles POST request
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("NotificationListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(
				PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		NotificationBean bean = (NotificationBean) populateBean(req);
		NotificationModel model = new NotificationModel();

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

				ServletUtility.redirect(
						ORSView.NOTIFICATION_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {
						model.delete(DataUtility.getLong(id));
					}

					ServletUtility.setSuccessMessage(
							"Notification deleted successfully", req);

				} else {

					ServletUtility.setErrorMessage(
							"Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.NOTIFICATION_LIST_CTL, req, resp);
				return;
			}

			List<NotificationBean> list =
					model.search(bean, pageNo, pageSize);

			List<NotificationBean> next =
					model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {
			log.error("Error in NotificationListCtl doPost", e);
			ServletUtility.handleException(e, req, resp);
			return;
		}

		log.info("NotificationListCtl doPost completed");
		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Get View
	 */
	@Override
	protected String getView() {
		return ORSView.NOTIFICATION_LIST_VIEW;
	}
}