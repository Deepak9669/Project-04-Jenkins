package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.OnlineCourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.OnlineCourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "OnlineCourseCtl", urlPatterns = { "/ctl/OnlineCourseCtl" })

public class OnlineCourseCtl extends BaseCtl {

    /**
     * Preload method
     */
    @Override
    protected void preload(HttpServletRequest request) {

        OnlineCourseModel model = new OnlineCourseModel();

        try {
            List list = model.list();
            request.setAttribute("courseList", list);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("courseTitle"))) {
            request.setAttribute("courseTitle",
                    PropertyReader.getValue("error.require", "Course Title"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("moduleName"))) {
            request.setAttribute("moduleName",
                    PropertyReader.getValue("error.require", "Module Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration",
                    PropertyReader.getValue("error.require", "Duration"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("instructorName"))) {
            request.setAttribute("instructorName",
                    PropertyReader.getValue("error.require", "Instructor Name"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate Bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        OnlineCourseBean bean = new OnlineCourseBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setCourseTitle(DataUtility.getString(request.getParameter("courseTitle")));
        bean.setModuleName(DataUtility.getString(request.getParameter("moduleName")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));
        bean.setInstructorName(DataUtility.getString(request.getParameter("instructorName")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * GET METHOD
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        OnlineCourseModel model = new OnlineCourseModel();

        if (id > 0) {

            try {

                OnlineCourseBean bean = model.findByPk(id);

                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {

                e.printStackTrace();

                ServletUtility.handleException(e, request, response);

                return;
            }
        }

        ServletUtility.forward(getView(), request, response);

    }

    /**
     * POST METHOD
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        OnlineCourseModel model = new OnlineCourseModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            OnlineCourseBean bean = (OnlineCourseBean) populateBean(request);

            try {

                model.add(bean);

                ServletUtility.setBean(bean, request);

                ServletUtility.setSuccessMessage("Course added Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);

                ServletUtility.setErrorMessage("Course already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();

                ServletUtility.handleException(e, request, response);

                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            OnlineCourseBean bean = (OnlineCourseBean) populateBean(request);

            try {

                if (id > 0) {

                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);

                ServletUtility.setSuccessMessage("Course updated Successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);

                ServletUtility.setErrorMessage("Course already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();

                ServletUtility.handleException(e, request, response);

                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ONLINE_COURSE_LIST_CTL1, request, response);

            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ONLINE_COURSE_CTL, request, response);

            return;
        }

        ServletUtility.forward(getView(), request, response);

    }

    /**
     * VIEW PAGE
     */
    @Override
    protected String getView() {

        return ORSView.ONLINE_COURSE_VIEW;

    }

}