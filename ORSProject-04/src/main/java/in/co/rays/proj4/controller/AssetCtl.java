package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.AssetBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.AssetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "AssetCtl", urlPatterns = { "/ctl/AssetCtl" })
public class AssetCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AssetCtl.class);

	/**
	 * Preload dropdown values
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("AssetCtl preload started");

		HashMap<String, String> statusMap = new HashMap<>();
		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");

		request.setAttribute("statusMap", statusMap);

		log.debug("AssetCtl preload completed");
	}

	/**
	 * Validate form data
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("AssetCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("assetCode"))) {
			request.setAttribute("assetCode",
					PropertyReader.getValue("error.require", "Asset Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assetName"))) {
			request.setAttribute("assetName",
					PropertyReader.getValue("error.require", "Asset Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assignedTo"))) {
			request.setAttribute("assignedTo",
					PropertyReader.getValue("error.require", "Assigned To"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("issueDate"))) {
			request.setAttribute("issueDate",
					PropertyReader.getValue("error.require", "Issue Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assetStatus"))) {
			request.setAttribute("assetStatus",
					PropertyReader.getValue("error.require", "Asset Status"));
			pass = false;
		}

		log.debug("AssetCtl validate completed : " + pass);
		return pass;
	}

	/**
	 * Populate AssetBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("AssetCtl populateBean started");

		AssetBean bean = new AssetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setAssetCode(DataUtility.getString(request.getParameter("assetCode")));
		bean.setAssetName(DataUtility.getString(request.getParameter("assetName")));
		bean.setAssignedTo(DataUtility.getString(request.getParameter("assignedTo")));
		bean.setIssueDate(DataUtility.getDate(request.getParameter("issueDate")));
		bean.setAssetStatus(DataUtility.getString(request.getParameter("assetStatus")));

		populateDTO(bean, request);

		log.debug("AssetCtl populateBean completed");
		return bean;
	}

	/**
	 * Load Asset by PK
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("AssetCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));
		AssetModel model = new AssetModel();

		if (id > 0) {
			try {
				AssetBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, req);
			} catch (ApplicationException e) {
				log.error("Error in AssetCtl doGet", e);
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

		log.info("AssetCtl doPost operation : " + op);

		AssetModel model = new AssetModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			AssetBean bean = (AssetBean) populateBean(req);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Asset added successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Asset Code already exists", req);
			} catch (ApplicationException e) {
				log.error("Error while adding Asset", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			AssetBean bean = (AssetBean) populateBean(req);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, req);
				ServletUtility.setSuccessMessage("Asset updated successfully", req);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, req);
				ServletUtility.setErrorMessage("Asset Code already exists", req);
			} catch (ApplicationException e) {
				log.error("Error while updating Asset", e);
				ServletUtility.handleException(e, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ASSET_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ASSET_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	/**
	 * Return Asset View Page
	 */
	@Override
	protected String getView() {
		return ORSView.ASSET_VIEW;
	}
}