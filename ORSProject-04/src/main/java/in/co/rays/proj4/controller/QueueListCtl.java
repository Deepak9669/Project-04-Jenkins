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
import in.co.rays.proj4.bean.QueueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.QueueModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * QueueListCtl handles listing, searching, deleting and pagination of Queue
 */
@WebServlet(name = "QueueListCtl", urlPatterns = { "/ctl/QueueListCtl" })
public class QueueListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(QueueListCtl.class);

	/**
	 * Preload Queue Status dropdown
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("QueueListCtl preload started");

		QueueModel model = new QueueModel();
		HashMap<String, String> statusMap = new HashMap<>();

		try {
			List<QueueBean> list = model.list();

			for (QueueBean bean : list) {
				statusMap.put(bean.getQueueStatus(), bean.getQueueStatus());
			}

			request.setAttribute("statusMap", statusMap);

		} catch (ApplicationException e) {
			log.error("Error in preload QueueListCtl", e);
		}

		log.debug("QueueListCtl preload completed");
	}

	/**
	 * Populate QueueBean for search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("QueueListCtl populateBean started");

		QueueBean bean = new QueueBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setQueueCode(DataUtility.getString(request.getParameter("queueCode")));
		bean.setQueueName(DataUtility.getString(request.getParameter("queueName")));
		bean.setQueueStatus(DataUtility.getString(request.getParameter("queueStatus")));

		log.debug("QueueListCtl populateBean completed");
		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("QueueListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		QueueBean bean = (QueueBean) populateBean(req);
		QueueModel model = new QueueModel();

		try {

			List<QueueBean> list = model.search(bean, pageNo, pageSize);
			List<QueueBean> next = model.search(bean, pageNo + 1, pageSize);

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
			log.error("Error in QueueListCtl doGet", e);
			ServletUtility.handleException(e, req, resp);
		}
	}

	/**
	 * Handle POST request
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("QueueListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		QueueBean bean = (QueueBean) populateBean(req);
		QueueModel model = new QueueModel();

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
				ServletUtility.redirect(ORSView.QUEUE_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {
						model.delete(DataUtility.getLong(id));
					}

					ServletUtility.setSuccessMessage("Queue deleted successfully", req);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.QUEUE_LIST_CTL, req, resp);
				return;
			}

			List<QueueBean> list = model.search(bean, pageNo, pageSize);
			List<QueueBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {
			log.error("Error in QueueListCtl doPost", e);
			ServletUtility.handleException(e, req, resp);
			return;
		}

		log.info("QueueListCtl doPost completed");
		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Return Queue List View
	 */
	@Override
	protected String getView() {
		return ORSView.QUEUE_LIST_VIEW;
	}
}
