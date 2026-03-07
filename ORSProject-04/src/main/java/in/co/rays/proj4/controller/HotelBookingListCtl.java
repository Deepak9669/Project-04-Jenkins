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
import in.co.rays.proj4.bean.HotelBookingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.HotelBookingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * HotelBookingListCtl handles listing, searching, deleting and pagination
 */
@WebServlet(name = "HotelBookingListCtl", urlPatterns = { "/ctl/HotelBookingListCtl" })
public class HotelBookingListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(HotelBookingListCtl.class);

    /**
     * ================= PRELOAD =================
     * Booking Status dropdown load
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("HotelBookingListCtl preload started");

        HashMap<String, String> statusMap = new HashMap<>();

        statusMap.put("Confirmed", "Confirmed");
        statusMap.put("Pending", "Pending");
        statusMap.put("Cancelled", "Cancelled");

        request.setAttribute("statusMap", statusMap);

        log.debug("HotelBookingListCtl preload completed");
    }

    /**
     * ================= POPULATE BEAN =================
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("HotelBookingListCtl populateBean started");

        HotelBookingBean bean = new HotelBookingBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setGuestName(DataUtility.getString(request.getParameter("guestName")));
        bean.setBookingStatus(DataUtility.getString(request.getParameter("bookingStatus")));

        log.debug("HotelBookingListCtl populateBean completed");
        return bean;
    }

    /**
     * ================= DO GET =================
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("HotelBookingListCtl doGet started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        HotelBookingBean bean = (HotelBookingBean) populateBean(req);
        HotelBookingModel model = new HotelBookingModel();

        try {

            List<HotelBookingBean> list = model.search(bean, pageNo, pageSize);
            List<HotelBookingBean> next = model.search(bean, pageNo + 1, pageSize);

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
            log.error("Error in HotelBookingListCtl doGet", e);
            ServletUtility.handleException(e, req, resp);
        }
    }

    /**
     * ================= DO POST =================
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("HotelBookingListCtl doPost started");

        int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        pageNo = (pageNo == 0) ? 1 : pageNo;

        HotelBookingBean bean = (HotelBookingBean) populateBean(req);
        HotelBookingModel model = new HotelBookingModel();

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
                ServletUtility.redirect(ORSView.HOTELBOOKING_CTL, req, resp);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    for (String id : ids) {
                        model.delete(DataUtility.getLong(id));
                    }

                    ServletUtility.setSuccessMessage("Booking deleted successfully", req);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", req);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)
                    || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.HOTELBOOKING_LIST_CTL, req, resp);
                return;
            }

            List<HotelBookingBean> list = model.search(bean, pageNo, pageSize);
            List<HotelBookingBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", req);
            }

            ServletUtility.setBean(bean, req);
            ServletUtility.setList(list, req);
            ServletUtility.setPageNo(pageNo, req);
            ServletUtility.setPageSize(pageSize, req);
            req.setAttribute("nextListSize", next.size());

        } catch (ApplicationException e) {
            log.error("Error in HotelBookingListCtl doPost", e);
            ServletUtility.handleException(e, req, resp);
            return;
        }

        log.info("HotelBookingListCtl doPost completed");
        ServletUtility.forward(getView(), req, resp);
    }

    /**
     * ================= GET VIEW =================
     */
    @Override
    protected String getView() {
        return ORSView.HOTELBOOKING_LIST_VIEW;
    }
}