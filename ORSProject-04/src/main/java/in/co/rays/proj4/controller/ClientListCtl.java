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
import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ClientModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * ClientListCtl handles listing, searching, deleting and pagination of Client
 */
@WebServlet(name = "ClientListCtl", urlPatterns = { "/ctl/ClientListCtl" })
public class ClientListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ClientListCtl.class);

	/**
	 * ================= PRELOAD =================
	 * Priority dropdown load
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("ClientListCtl preload started");

		ClientModel model = new ClientModel();
		HashMap<String, String> priorityMap = new HashMap<>();

		try {
			List<ClientBean> list = model.list();

			for (ClientBean bean : list) {
				priorityMap.put(bean.getPriortiy(), bean.getPriortiy());
			}

			request.setAttribute("priorityMap", priorityMap);

		} catch (ApplicationException e) {
			log.error("Error in preload ClientListCtl", e);
		}

		log.debug("ClientListCtl preload completed");
	}

	/**
	 * ================= POPULATE BEAN =================
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ClientListCtl populateBean started");

		ClientBean bean = new ClientBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setClientName(DataUtility.getString(request.getParameter("clientName")));
		bean.setPriortiy(DataUtility.getString(request.getParameter("priortiy")));

		log.debug("ClientListCtl populateBean completed");
		return bean;
	}

	/**
	 * ================= DO GET =================
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("ClientListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ClientBean bean = (ClientBean) populateBean(req);
		ClientModel model = new ClientModel();

		try {

			List<ClientBean> list = model.search(bean, pageNo, pageSize);
			List<ClientBean> next = model.search(bean, pageNo + 1, pageSize);

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
			log.error("Error in ClientListCtl doGet", e);
			ServletUtility.handleException(e, req, resp);
		}
	}

	/**
	 * ================= DO POST =================
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("ClientListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		ClientBean bean = (ClientBean) populateBean(req);
		ClientModel model = new ClientModel();

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
				ServletUtility.redirect(ORSView.CLIENT_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {
						model.delete(DataUtility.getLong(id));
					}

					ServletUtility.setSuccessMessage("Client deleted successfully", req);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, req, resp);
				return;
			}

			List<ClientBean> list = model.search(bean, pageNo, pageSize);
			List<ClientBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {
			log.error("Error in ClientListCtl doPost", e);
			ServletUtility.handleException(e, req, resp);
			return;
		}

		log.info("ClientListCtl doPost completed");
		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * ================= GET VIEW =================
	 */
	@Override
	protected String getView() {
		return ORSView.CLIENT_LIST_VIEW;
	}
}