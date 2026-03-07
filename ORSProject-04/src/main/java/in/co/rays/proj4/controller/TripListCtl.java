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
import in.co.rays.proj4.bean.TripBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TripModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * TripListCtl handles listing, searching, deleting and pagination of Trip
 */
@WebServlet(name = "TripListCtl", urlPatterns = { "/ctl/TripListCtl" })
public class TripListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(TripListCtl.class);

    /**
     * ================= PRELOAD =================
     * Trip Status dropdown load
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("TripListCtl preload started");

        TripModel model = new TripModel();
        HashMap<String, String> statusMap = new HashMap<>();

        try {
            List<TripBean> list = model.list();

            for (TripBean bean : list) {
                statusMap.put(bean.getTripStatus(), bean.getTripStatus());
            }

            request.setAttribute("statusMap", statusMap);

        } catch (ApplicationException e) {
            log.error("Error in preload TripListCtl", e);
        }

        log.debug("TripListCtl preload completed");
    }

    /**
     * ================= POPULATE BEAN =================
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("TripListCtl populateBean started");

        TripBean bean = new TripBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTripCode(DataUtility.getString(request.getParameter("tripCode")));
        bean.setDriverName(DataUtility.getString(request.getParameter("driverName")));
        bean.setTripStatus(DataUtility.getString(request.getParameter("tripStatus")));

        log.debug("TripListCtl populateBean completed");
        return bean;
    }

    /**
     * ================= DO GET =================
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("TripListCtl doGet started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        TripBean bean = (TripBean) populateBean(req);
        TripModel model = new TripModel();

        try {

            List<TripBean> list = model.search(bean, pageNo, pageSize);
            List<TripBean> next = model.search(bean, pageNo + 1, pageSize);

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
            log.error("Error in TripListCtl doGet", e);
            ServletUtility.handleException(e, req, resp);
        }
    }

    /**
     * ================= DO POST =================
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("TripListCtl doPost started");

        int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        pageNo = (pageNo == 0) ? 1 : pageNo;

        TripBean bean = (TripBean) populateBean(req);
        TripModel model = new TripModel();

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
                ServletUtility.redirect(ORSView.TRIP_CTL, req, resp);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    for (String id : ids) {
                        model.delete(DataUtility.getLong(id));
                    }

                    ServletUtility.setSuccessMessage("Trip deleted successfully", req);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", req);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)
                    || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.TRIP_LIST_CTL, req, resp);
                return;
            }

            List<TripBean> list = model.search(bean, pageNo, pageSize);
            List<TripBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", req);
            }

            ServletUtility.setBean(bean, req);
            ServletUtility.setList(list, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());

        } catch (ApplicationException e) {
            log.error("Error in TripListCtl doPost", e);
            ServletUtility.handleException(e, req, resp);
            return;
        }

        log.info("TripListCtl doPost completed");
        ServletUtility.forward(getView(), req, resp);
    }

    /**
     * ================= GET VIEW =================
     */
    @Override
    protected String getView() {
        return ORSView.TRIP_LIST_VIEW;
    }
}