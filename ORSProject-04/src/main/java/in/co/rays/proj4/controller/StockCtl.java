package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.StockBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StockModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "StockCtl", urlPatterns = { "/ctl/StockCtl" })
public class StockCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StockCtl.class);

	/**
	 * Preload Dropdown
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("StockCtl preload started");

		HashMap<String, String> typeMap = new HashMap<>();

		typeMap.put("Electronics", "Electronics");
		typeMap.put("Furniture", "Furniture");
		typeMap.put("Stationary", "Stationary");

		request.setAttribute("typeMap", typeMap);

		log.debug("StockCtl preload completed");

	}

	/**
	 * Validate Form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("StockCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("stockName"))) {
			request.setAttribute("stockName",
					PropertyReader.getValue("error.require", "Stock Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("stockName"))) {
			request.setAttribute("stockName",
					PropertyReader.getValue("error.require", "Stock Name"));
			pass = false;
			
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price",
					PropertyReader.getValue("error.require", "Price"));
			pass = false;
		}

		if (!DataValidator.isDouble(request.getParameter("price"))) {
			request.setAttribute("price", "Price must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity",
					PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		}

		if (!DataValidator.isInteger(request.getParameter("quantity"))) {
			request.setAttribute("quantity", "Quantity must be integer");
			pass = false;
		}

		log.debug("StockCtl validate completed : " + pass);

		return pass;
	}

	/**
	 * Populate Bean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("StockCtl populateBean started");

		StockBean bean = new StockBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setStockId(DataUtility.getLong(request.getParameter("stockId")));
		bean.setStockName(DataUtility.getString(request.getParameter("stockName")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setQuantity(DataUtility.getInt(request.getParameter("quantity")));

		populateDTO(bean, request);

		log.debug("StockCtl populateBean completed");

		return bean;
	}

	/**
	 * Load data by PK
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("StockCtl doGet started");

		long id = DataUtility.getLong(req.getParameter("id"));

		StockModel model = new StockModel();

		if (id > 0) {

			try {

				StockBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, req);

			} catch (ApplicationException e) {

				log.error("Error in StockCtl doGet", e);

				ServletUtility.handleException(e, req, resp);

				return;
			}
		}

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * POST Operations
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));

		long id = DataUtility.getLong(req.getParameter("id"));

		log.info("StockCtl doPost operation : " + op);

		StockModel model = new StockModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			StockBean bean = (StockBean) populateBean(req);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, req);

				ServletUtility.setSuccessMessage("Stock added successfully", req);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, req);

				ServletUtility.setErrorMessage("Stock already exists", req);

			} catch (ApplicationException e) {

				log.error("Error while adding Stock", e);

				ServletUtility.handleException(e, req, resp);

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			StockBean bean = (StockBean) populateBean(req);

			try {

				if (id > 0) {

					model.update(bean);

				}

				ServletUtility.setBean(bean, req);

				ServletUtility.setSuccessMessage("Stock updated successfully", req);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, req);

				ServletUtility.setErrorMessage("Stock already exists", req);

			} catch (ApplicationException e) {

				log.error("Error while updating Stock", e);

				ServletUtility.handleException(e, req, resp);

				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCK_CTL, req, resp);

			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCK_LIST_CTL, req, resp);

			return;

		}

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * View Page
	 */
	@Override
	protected String getView() {

		return ORSView.STOCK_VIEW;

	}

}