package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BrokerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BrokerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BrokerCtl", urlPatterns = { "/ctl/BrokerCtl" })
public class BrokerCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(BrokerCtl.class);

    /**
     * Preload dropdown values
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("BrokerCtl preload started");

        HashMap<String, String> companyMap = new HashMap<>();

        companyMap.put("Angel Broking", "Angel Broking");
        companyMap.put("Zerodha", "Zerodha");
        companyMap.put("Upstox", "Upstox");
        companyMap.put("Groww", "Groww");

        request.setAttribute("companyMap", companyMap);

        log.debug("BrokerCtl preload completed");
    }

    /**
     * Validate form data
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("brokerId"))) {
            request.setAttribute("brokerId",
                    PropertyReader.getValue("error.require", "Broker Id"));
            pass = false;
        }else if (!DataValidator.isInteger(request.getParameter("brokerId"))) {
            request.setAttribute("brokerId",
                    "Broker id is invalid");
                pass = false;
            }

        if (DataValidator.isNull(request.getParameter("brokerName"))) {
            request.setAttribute("brokerName",
                    PropertyReader.getValue("error.require", "Broker Name"));
            pass = false;
            
        }else if (!DataValidator.isName(request.getParameter("brokerName"))) {
                request.setAttribute("brokerName",
                    "Broker Name is invalid");
                pass = false;
            }
			
		

        if (DataValidator.isNull(request.getParameter("contactNumber"))) {
            request.setAttribute("contactNumber",
                    PropertyReader.getValue("error.require", "Contact Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("company"))) {
            request.setAttribute("company",
                    PropertyReader.getValue("error.require", "Company"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate BrokerBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        BrokerBean bean = new BrokerBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setBrokerId(DataUtility.getLong(request.getParameter("brokerId")));
        bean.setBrokerName(DataUtility.getString(request.getParameter("brokerName")));
        bean.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));
        bean.setCompany(DataUtility.getString(request.getParameter("company")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Load Broker by PK
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long id = DataUtility.getLong(req.getParameter("id"));
        BrokerModel model = new BrokerModel();

        if (id > 0) {
            try {
                BrokerBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, req);
            } catch (ApplicationException e) {
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

        BrokerModel model = new BrokerModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            BrokerBean bean = (BrokerBean) populateBean(req);

            try {
                model.add(bean);
                ServletUtility.setSuccessMessage("Broker added successfully", req);
                ServletUtility.setBean(bean, req);
            } catch (DuplicateRecordException e) {
                ServletUtility.setErrorMessage("Broker Name already exists", req);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, req, resp);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            BrokerBean bean = (BrokerBean) populateBean(req);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setSuccessMessage("Broker updated successfully", req);
            } catch (DuplicateRecordException e) {
                ServletUtility.setErrorMessage("Broker Name already exists", req);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, req, resp);
                return;
            }

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.BROKER_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.BROKER_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.BROKER_VIEW;
    }
}