package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.QueueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.QueueModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * QueueCtl handles Queue add, update, view operations
 */
@WebServlet(name = "QueueCtl", urlPatterns = { "/ctl/QueueCtl" })
public class QueueCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(QueueCtl.class);

	/**
	 * Preload dropdown values
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("QueueCtl preload started");

		HashMap<String, String> statusMap = new HashMap<>();
		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");

		request.setAttribute("statusMap", statusMap);

		log.debug("QueueCtl preload completed");
	}

	/**
	 * Validate form data
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("QueueCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("queueCode"))) {
			request.setAttribute("queueCode",
					PropertyReader.getValue("error.require", "Queue Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("queueName"))) {
			request.setAttribute("queueName",
					PropertyReader.getValue("error.require", "Queue Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("queueSize"))) {
			request.setAttribute("queueSize",
					PropertyReader.getValue("error.require", "Queue Size"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("queueStatus"))) {
			request.setAttribute("queueStatus",
					PropertyReader.getValue("error.require", "Queue Status"));
			pass = false;
		}

		log.debug("QueueCtl validate completed : " + pass);
		return pass;
	}

	/**
	 * Populate QueueBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("QueueCtl populateBean started");

		QueueBean bean = new QueueBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setQueueCode(DataUtility.getString(request.getParameter("queueCode")));
		bean.setQueueName(DataUtility.getString(request.getParameter("queueName")));
		bean.setQueueSize(DataUtility.getString(request.getParameter("queueSize")));
		bean.setQueueStatus(DataUtility.getString(request.getParameter("queueStatus")));

		populateDTO(bean, request);

		log.debug("QueueCtl populateBean completed");
		return bean;
	}

	/**
	 * Load Queue by PK
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("QueueCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));
		QueueModel model = new QueueModel();

		if (id > 0) {
			try {
				QueueBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (ApplicationException e) {
				log.error("Error in QueueCtl doGet", e);
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

		String op = DataUtility.getString(req.getParameter("operation"));
		long id = DataUtility.getLong(req.getParameter("id"));

		log.info("QueueCtl doPost operation : " + op);

		QueueModel model = new QueueModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			QueueBean bean = (QueueBean) populateBean(req);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Queue added successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Queue Code already exists", req);
			} catch (ApplicationException e) {
				log.error("Error while adding Queue", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			QueueBean bean = (QueueBean) populateBean(req);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Queue updated successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Queue Code already exists", req);
			} catch (ApplicationException e) {
				log.error("Error while updating Queue", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.QUEUE_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.QUEUE_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Return Queue View Page
	 */
	@Override
	protected String getView() {
		return ORSView.QUEUE_VIEW;
	}
}
