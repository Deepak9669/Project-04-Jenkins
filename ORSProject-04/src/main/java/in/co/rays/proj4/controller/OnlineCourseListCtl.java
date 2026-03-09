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
import in.co.rays.proj4.bean.OnlineCourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.OnlineCourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "OnlineCourseListCtl", urlPatterns = { "/ctl/OnlineCourseListCtl" })

public class OnlineCourseListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(OnlineCourseListCtl.class);

	/**
	 * Preload dropdown values
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("OnlineCourseListCtl preload started");

		OnlineCourseModel model = new OnlineCourseModel();
		HashMap<String, String> courseMap = new HashMap<>();

		try {

			List<OnlineCourseBean> list = model.list();

			for (OnlineCourseBean bean : list) {

				courseMap.put(bean.getCourseTitle(), bean.getCourseTitle());

			}

			request.setAttribute("courseMap", courseMap);

		} catch (ApplicationException e) {

			log.error("Error in preload OnlineCourseListCtl", e);

		}

		log.debug("OnlineCourseListCtl preload completed");

	}

	/**
	 * Populate bean for search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("OnlineCourseListCtl populateBean started");

		OnlineCourseBean bean = new OnlineCourseBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseTitle(DataUtility.getString(request.getParameter("courseTitle")));

		log.debug("OnlineCourseListCtl populateBean completed");

		return bean;

	}

	/**
	 * Handle GET
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("OnlineCourseListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		OnlineCourseBean bean = (OnlineCourseBean) populateBean(req);
		OnlineCourseModel model = new OnlineCourseModel();

		try {

			List<OnlineCourseBean> list = model.search(bean, pageNo, pageSize);
			List<OnlineCourseBean> next = model.search(bean, pageNo + 1, pageSize);

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

			log.error("Error in OnlineCourseListCtl doGet", e);

			ServletUtility.handleException(e, req, resp);

		}

	}

	/**
	 * Handle POST
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("OnlineCourseListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		OnlineCourseBean bean = (OnlineCourseBean) populateBean(req);
		OnlineCourseModel model = new OnlineCourseModel();

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

				ServletUtility.redirect(ORSView.ONLINE_COURSE_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						model.delete(DataUtility.getLong(id));

					}

					ServletUtility.setSuccessMessage("Course deleted successfully", req);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ONLINE_COURSE_LIST_CTL1, req, resp);
				return;

			}

			List<OnlineCourseBean> list = model.search(bean, pageNo, pageSize);
			List<OnlineCourseBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);

			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {

			log.error("Error in OnlineCourseListCtl doPost", e);

			ServletUtility.handleException(e, req, resp);
			return;

		}

		log.info("OnlineCourseListCtl doPost completed");

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * View page
	 */
	@Override
	protected String getView() {

		return ORSView.ONLINE_COURSE_LIST_VIEW1;

	}

}