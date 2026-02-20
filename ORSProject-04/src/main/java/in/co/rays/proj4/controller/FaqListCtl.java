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
import in.co.rays.proj4.bean.FaqBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.FaqModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * FaqListCtl handles listing, searching, deleting and pagination of FAQ
 */
@WebServlet(name = "FaqListCtl", urlPatterns = { "/ctl/FaqListCtl" })
public class FaqListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FaqListCtl.class);

	/**
	 * Preload FAQ Status dropdown
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("FaqListCtl preload started");

		FaqModel model = new FaqModel();
		HashMap<String, String> statusMap = new HashMap<>();

		try {
			List<FaqBean> list = model.list();

			for (FaqBean bean : list) {
				statusMap.put(bean.getFaqStatus(), bean.getFaqStatus());
			}

			request.setAttribute("statusMap", statusMap);

		} catch (ApplicationException e) {
			log.error("Error in preload FaqListCtl", e);
		}

		log.debug("FaqListCtl preload completed");
	}

	/**
	 * Populate FaqBean for search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("FaqListCtl populateBean started");

		FaqBean bean = new FaqBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFaqCode(DataUtility.getString(request.getParameter("faqCode")));
		bean.setQuestion(DataUtility.getString(request.getParameter("question")));
		bean.setFaqStatus(DataUtility.getString(request.getParameter("faqStatus")));

		log.debug("FaqListCtl populateBean completed");
		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("FaqListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		FaqBean bean = (FaqBean) populateBean(req);
		FaqModel model = new FaqModel();

		try {

			List<FaqBean> list = model.search(bean, pageNo, pageSize);
			List<FaqBean> next = model.search(bean, pageNo + 1, pageSize);

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
			log.error("Error in FaqListCtl doGet", e);
			ServletUtility.handleException(e, req, resp);
		}
	}

	/**
	 * Handle POST request
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("FaqListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		FaqBean bean = (FaqBean) populateBean(req);
		FaqModel model = new FaqModel();

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
				ServletUtility.redirect(ORSView.FAQ_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {
						model.delete(DataUtility.getLong(id));
					}

					ServletUtility.setSuccessMessage("FAQ deleted successfully", req);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FAQ_LIST_CTL, req, resp);
				return;
			}

			List<FaqBean> list = model.search(bean, pageNo, pageSize);
			List<FaqBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {
			log.error("Error in FaqListCtl doPost", e);
			ServletUtility.handleException(e, req, resp);
			return;
		}

		log.info("FaqListCtl doPost completed");
		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Return FAQ List View
	 */
	@Override
	protected String getView() {
		return ORSView.FAQ_LIST_VIEW;
	}
}