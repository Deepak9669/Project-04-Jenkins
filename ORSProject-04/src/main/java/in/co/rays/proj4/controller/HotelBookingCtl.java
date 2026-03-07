package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.HotelBookingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.HotelBookingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "HotelBookingCtl", urlPatterns = { "/ctl/HotelBookingCtl" })
public class HotelBookingCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(HotelBookingCtl.class);

    /**
     * ================= PRELOAD =================
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("HotelBookingCtl preload started");

        // Booking Status Dropdown
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("Confirmed", "Confirmed");
        statusMap.put("Pending", "Pending");
        statusMap.put("Cancelled", "Cancelled");

        request.setAttribute("statusMap", statusMap);

        log.debug("HotelBookingCtl preload completed");
    }

    /**
     * ================= VALIDATE =================
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("HotelBookingCtl validate started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("guestName"))) {
            request.setAttribute("guestName",
                    PropertyReader.getValue("error.require", "Guest Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("roomNb"))) {
            request.setAttribute("roomNb",
                    PropertyReader.getValue("error.require", "Room Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("checkinDate"))) {
            request.setAttribute("checkinDate",
                    PropertyReader.getValue("error.require", "Check-in Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("checkOutDate"))) {
            request.setAttribute("checkOutDate",
                    PropertyReader.getValue("error.require", "Check-out Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("numberOfGuest"))) {
            request.setAttribute("numberOfGuest",
                    PropertyReader.getValue("error.require", "Number Of Guest"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bookingAmount"))) {
            request.setAttribute("bookingAmount",
                    PropertyReader.getValue("error.require", "Booking Amount"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bookingStatus"))) {
            request.setAttribute("bookingStatus",
                    PropertyReader.getValue("error.require", "Booking Status"));
            pass = false;
        }

        log.debug("HotelBookingCtl validate completed : " + pass);
        return pass;
    }

    /**
     * ================= POPULATE BEAN =================
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("HotelBookingCtl populateBean started");

        HotelBookingBean bean = new HotelBookingBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setGuestName(DataUtility.getString(request.getParameter("guestName")));
        bean.setRoomNb(DataUtility.getString(request.getParameter("roomNb")));
        bean.setCheckinDate(DataUtility.getDate(request.getParameter("checkinDate")));
        bean.setCheckOutDate(DataUtility.getDate(request.getParameter("checkOutDate")));
        bean.setNumberOfGuest(DataUtility.getString(request.getParameter("numberOfGuest")));
        bean.setBookingAmount(DataUtility.getString(request.getParameter("bookingAmount")));
        bean.setBookingStatus(DataUtility.getString(request.getParameter("bookingStatus")));

        populateDTO(bean, request);

        log.debug("HotelBookingCtl populateBean completed");
        return bean;
    }

    /**
     * ================= DO GET =================
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("HotelBookingCtl doGet started");

        long id = DataUtility.getLong(req.getParameter("id"));
        HotelBookingModel model = new HotelBookingModel();

        if (id > 0) {
            try {
                HotelBookingBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, req);
            } catch (ApplicationException e) {
                log.error("Error in HotelBookingCtl doGet", e);
                ServletUtility.handleException(e, req, resp);
                return;
            }
        }

        ServletUtility.forward(getView(), req, resp);
    }

    /**
     * ================= DO POST =================
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String op = DataUtility.getString(req.getParameter("operation"));
        long id = DataUtility.getLong(req.getParameter("id"));

        log.info("HotelBookingCtl doPost operation : " + op);

        HotelBookingModel model = new HotelBookingModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            HotelBookingBean bean = (HotelBookingBean) populateBean(req);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, req);
                ServletUtility.setSuccessMessage("Booking added successfully", req);

            } catch (ApplicationException e) {
                log.error("Error while adding Booking", e);
                ServletUtility.handleException(e, req, resp);
                return;
            } catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            HotelBookingBean bean = (HotelBookingBean) populateBean(req);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, req);
                ServletUtility.setSuccessMessage("Booking updated successfully", req);

            } catch (ApplicationException e) {
                log.error("Error while updating Booking", e);
                ServletUtility.handleException(e, req, resp);
                return;
            }

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.HOTELBOOKING_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.HOTELBOOKING_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    /**
     * ================= GET VIEW =================
     */
    @Override
    protected String getView() {
        return ORSView.HOTELBOOKING_VIEW;
    }
}