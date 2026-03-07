package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.AssetBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.AssetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * AssetListCtl handles listing, searching, deleting and pagination of Asset
 */
@WebServlet(name = "AssetListCtl", urlPatterns = { "/ctl/AssetListCtl" })
public class AssetListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AssetListCtl.class);

	/**
	 * Preload Asset Status dropdown
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("AssetListCtl preload started");

		AssetModel model = new AssetModel();
		HashMap<String, String> statusMap = new HashMap<>();

		try {
			List<AssetBean> list = model.list();

			for (AssetBean bean : list) {
				statusMap.put(bean.getAssetStatus(), bean.getAssetStatus());
			}

			request.setAttribute("statusMap", statusMap);

		} catch (ApplicationException e) {
			log.error("Error in preload AssetListCtl", e);
		}

		log.debug("AssetListCtl preload completed");
	}

	/**
	 * Populate AssetBean for search
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("AssetListCtl populateBean started");

		AssetBean bean = new AssetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setAssetCode(DataUtility.getString(request.getParameter("assetCode")));
		bean.setAssetName(DataUtility.getString(request.getParameter("assetName")));
		bean.setAssetStatus(DataUtility.getString(request.getParameter("assetStatus")));

		log.debug("AssetListCtl populateBean completed");
		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("AssetListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		AssetBean bean = (AssetBean) populateBean(req);
		AssetModel model = new AssetModel();

		try {

			List<AssetBean> list = model.search(bean, pageNo, pageSize);
			List<AssetBean> next = model.search(bean, pageNo + 1, pageSize);

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
			log.error("Error in AssetListCtl doGet", e);
			ServletUtility.handleException(e, req, resp);
		}
	}

	/**
	 * Handle POST request
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("AssetListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		AssetBean bean = (AssetBean) populateBean(req);
		AssetModel model = new AssetModel();

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
				ServletUtility.redirect(ORSView.ASSET_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {
						model.delete(DataUtility.getLong(id));
					}

					ServletUtility.setSuccessMessage("Asset deleted successfully", req);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ASSET_LIST_CTL, req, resp);
				return;
			}

			List<AssetBean> list = model.search(bean, pageNo, pageSize);
			List<AssetBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {
			log.error("Error in AssetListCtl doPost", e);
			ServletUtility.handleException(e, req, resp);
			return;
		}

		log.info("AssetListCtl doPost completed");
		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Return Asset List View
	 */
	@Override
	protected String getView() {
		return ORSView.ASSET_LIST_VIEW;
	}
}