package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.FaqBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FaqModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * FaqCtl handles FAQ add, update, view operations
 */
@WebServlet(name = "FaqCtl", urlPatterns = { "/ctl/FaqCtl" })
public class FaqCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FaqCtl.class);

	/**
	 * Preload dropdown values
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("FaqCtl preload started");

		HashMap<String, String> statusMap = new HashMap<>();
		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");

		request.setAttribute("statusMap", statusMap);

		log.debug("FaqCtl preload completed");
	}

	/**
	 * Validate form data
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("FaqCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("faqCode"))) {
			request.setAttribute("faqCode",
					PropertyReader.getValue("error.require", "Faq Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("question"))) {
			request.setAttribute("question",
					PropertyReader.getValue("error.require", "Question"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("answer"))) {
			request.setAttribute("answer",
					PropertyReader.getValue("error.require", "Answer"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("faqStatus"))) {
			request.setAttribute("faqStatus",
					PropertyReader.getValue("error.require", "Faq Status"));
			pass = false;
		}

		log.debug("FaqCtl validate completed : " + pass);
		return pass;
	}

	/**
	 * Populate FaqBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("FaqCtl populateBean started");

		FaqBean bean = new FaqBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFaqCode(DataUtility.getString(request.getParameter("faqCode")));
		bean.setQuestion(DataUtility.getString(request.getParameter("question")));
		bean.setAnswer(DataUtility.getString(request.getParameter("answer")));
		bean.setFaqStatus(DataUtility.getString(request.getParameter("faqStatus")));

		populateDTO(bean, request);

		log.debug("FaqCtl populateBean completed");
		return bean;
	}

	/**
	 * Load FAQ by PK
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("FaqCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));
		FaqModel model = new FaqModel();

		if (id > 0) {
			try {
				FaqBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (ApplicationException e) {
				log.error("Error in FaqCtl doGet", e);
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

		log.info("FaqCtl doPost operation : " + op);

		FaqModel model = new FaqModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			FaqBean bean = (FaqBean) populateBean(req);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("FAQ added successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("FAQ Code already exists", req);
			} catch (ApplicationException e) {
				log.error("Error while adding FAQ", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			FaqBean bean = (FaqBean) populateBean(req);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("FAQ updated successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("FAQ Code already exists", req);
			} catch (ApplicationException e) {
				log.error("Error while updating FAQ", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FAQ_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FAQ_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Return FAQ View Page
	 */
	@Override
	protected String getView() {
		return ORSView.FAQ_VIEW;
	}
}