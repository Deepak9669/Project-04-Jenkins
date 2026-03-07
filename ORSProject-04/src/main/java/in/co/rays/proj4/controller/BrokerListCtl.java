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
import in.co.rays.proj4.bean.BrokerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.BrokerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * BrokerListCtl handles listing, searching, deleting and pagination of Broker
 */
@WebServlet(name = "BrokerListCtl", urlPatterns = { "/ctl/BrokerListCtl" })
public class BrokerListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(BrokerListCtl.class);

    /**
     * Preload Company dropdown
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("BrokerListCtl preload started");

        BrokerModel model = new BrokerModel();
        HashMap<String, String> companyMap = new HashMap<>();

        try {
            List<BrokerBean> list = model.list();

            for (BrokerBean bean : list) {
                companyMap.put(bean.getCompany(), bean.getCompany());
            }

            request.setAttribute("companyMap", companyMap);

        } catch (ApplicationException e) {
            log.error("Error in preload BrokerListCtl", e);
        }

        log.debug("BrokerListCtl preload completed");
    }

    /**
     * Populate BrokerBean for search
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("BrokerListCtl populateBean started");

        BrokerBean bean = new BrokerBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setBrokerName(DataUtility.getString(request.getParameter("brokerName")));
        bean.setCompany(DataUtility.getString(request.getParameter("company")));

        log.debug("BrokerListCtl populateBean completed");
        return bean;
    }

    /**
     * Handle GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("BrokerListCtl doGet started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        BrokerBean bean = (BrokerBean) populateBean(req);
        BrokerModel model = new BrokerModel();

        try {

            List<BrokerBean> list = model.search(bean, pageNo, pageSize);
            List<BrokerBean> next = model.search(bean, pageNo + 1, pageSize);

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
            log.error("Error in BrokerListCtl doGet", e);
            ServletUtility.handleException(e, req, resp);
        }
    }

    /**
     * Handle POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("BrokerListCtl doPost started");

        int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        pageNo = (pageNo == 0) ? 1 : pageNo;

        BrokerBean bean = (BrokerBean) populateBean(req);
        BrokerModel model = new BrokerModel();

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

                ServletUtility.redirect(ORSView.BROKER_CTL, req, resp);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    for (String id : ids) {
                        model.delete(DataUtility.getLong(id));
                    }

                    ServletUtility.setSuccessMessage("Broker deleted successfully", req);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", req);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)
                    || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.BROKER_LIST_CTL, req, resp);
                return;
            }

            List<BrokerBean> list = model.search(bean, pageNo, pageSize);
            List<BrokerBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", req);
            }

            ServletUtility.setBean(bean, req);
            ServletUtility.setList(list, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());

        } catch (ApplicationException e) {
            log.error("Error in BrokerListCtl doPost", e);
            ServletUtility.handleException(e, req, resp);
            return;
        }

        log.info("BrokerListCtl doPost completed");
        ServletUtility.forward(getView(), req, resp);
    }

    /**
     * Return Broker List View
     */
    @Override
    protected String getView() {
        return ORSView.BROKER_LIST_VIEW;
    }
}