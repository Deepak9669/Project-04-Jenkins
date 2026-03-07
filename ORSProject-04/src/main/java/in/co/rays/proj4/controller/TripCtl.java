package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TripBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TripModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TripCtl", urlPatterns = { "/ctl/TripCtl" })
public class TripCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(TripCtl.class);

    /**
     * ================= PRELOAD =================
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("TripCtl preload started");

        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("Planned", "Planned");
        statusMap.put("Ongoing", "Ongoing");
        statusMap.put("Completed", "Completed");
        statusMap.put("Cancelled", "Cancelled");

        request.setAttribute("statusMap", statusMap);

        log.debug("TripCtl preload completed");
    }

    /**
     * ================= VALIDATE =================
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("TripCtl validate started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("tripCode"))) {
            request.setAttribute("tripCode",
                    PropertyReader.getValue("error.require", "Trip Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("driverName"))) {
            request.setAttribute("driverName",
                    PropertyReader.getValue("error.require", "Driver Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("tripDate"))) {
            request.setAttribute("tripDate",
                    PropertyReader.getValue("error.require", "Trip Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("tripStatus"))) {
            request.setAttribute("tripStatus",
                    PropertyReader.getValue("error.require", "Trip Status"));
            pass = false;
        }

        log.debug("TripCtl validate completed : " + pass);
        return pass;
    }

    /**
     * ================= POPULATE BEAN =================
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("TripCtl populateBean started");

        TripBean bean = new TripBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTripCode(DataUtility.getString(request.getParameter("tripCode")));
        bean.setDriverName(DataUtility.getString(request.getParameter("driverName")));
        bean.setTripDate(DataUtility.getDate(request.getParameter("tripDate")));
        bean.setTripStatus(DataUtility.getString(request.getParameter("tripStatus")));

        populateDTO(bean, request);

        log.debug("TripCtl populateBean completed");
        return bean;
    }

    /**
     * ================= DO GET =================
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("TripCtl doGet started");

        long id = DataUtility.getLong(req.getParameter("id"));
        TripModel model = new TripModel();

        if (id > 0) {
            try {
                TripBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, req);
            } catch (ApplicationException e) {
                log.error("Error in TripCtl doGet", e);
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

        log.info("TripCtl doPost operation : " + op);

        TripModel model = new TripModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            TripBean bean = (TripBean) populateBean(req);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, req);
                ServletUtility.setSuccessMessage("Trip added successfully", req);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, req);
                ServletUtility.setErrorMessage("Trip Code already exists", req);

            } catch (ApplicationException e) {
                log.error("Error while adding Trip", e);
                ServletUtility.handleException(e, req, resp);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            TripBean bean = (TripBean) populateBean(req);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, req);
                ServletUtility.setSuccessMessage("Trip updated successfully", req);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, req);
                ServletUtility.setErrorMessage("Trip Code already exists", req);

            } catch (ApplicationException e) {
                log.error("Error while updating Trip", e);
                ServletUtility.handleException(e, req, resp);
                return;
            }

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.TRIP_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.TRIP_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    /**
     * ================= GET VIEW =================
     */
    @Override
    protected String getView() {
        return ORSView.TRIP_VIEW;
    }
}