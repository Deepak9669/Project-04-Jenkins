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
import in.co.rays.proj4.bean.StockBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.StockModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * StockListCtl handles listing, searching, deleting and pagination of Stock
 */

@WebServlet(name = "StockListCtl", urlPatterns = { "/ctl/StockListCtl" })
public class StockListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StockListCtl.class);

	/**
	 * Preload dropdown values
	 */

	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("StockListCtl preload started");

		StockModel model = new StockModel();
		HashMap<String, String> stockMap = new HashMap<>();

		try {

			List<StockBean> list = model.list();

			for (StockBean bean : list) {

				stockMap.put(bean.getStockName(), bean.getStockName());

			}

			request.setAttribute("stockMap", stockMap);

		} catch (ApplicationException e) {

			log.error("Error in preload StockListCtl", e);

		}

		log.debug("StockListCtl preload completed");

	}

	/**
	 * Populate StockBean for search
	 */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("StockListCtl populateBean started");

		StockBean bean = new StockBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setStockName(DataUtility.getString(request.getParameter("stockName")));

		log.debug("StockListCtl populateBean completed");

		return bean;
	}

	/**
	 * Handle GET request
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("StockListCtl doGet started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		StockBean bean = (StockBean) populateBean(req);
		StockModel model = new StockModel();

		try {

			List<StockBean> list = model.search(bean, pageNo, pageSize);
			List<StockBean> next = model.search(bean, pageNo + 1, pageSize);

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

			log.error("Error in StockListCtl doGet", e);

			ServletUtility.handleException(e, req, resp);
		}

	}

	/**
	 * Handle POST request
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("StockListCtl doPost started");

		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		StockBean bean = (StockBean) populateBean(req);
		StockModel model = new StockModel();

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

				ServletUtility.redirect(ORSView.STOCK_CTL, req, resp);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						model.delete(DataUtility.getLong(id));

					}

					ServletUtility.setSuccessMessage("Stock deleted successfully", req);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", req);
				}

			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STOCK_LIST_CTL, req, resp);
				return;

			}

			List<StockBean> list = model.search(bean, pageNo, pageSize);
			List<StockBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", req);
			}

			ServletUtility.setBean(bean, req);
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);

			req.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {

			log.error("Error in StockListCtl doPost", e);

			ServletUtility.handleException(e, req, resp);
			return;

		}

		log.info("StockListCtl doPost completed");

		ServletUtility.forward(getView(), req, resp);

	}

	/**
	 * Return Stock List View
	 */

	@Override
	protected String getView() {

		return ORSView.STOCK_LIST_VIEW;

	}

}