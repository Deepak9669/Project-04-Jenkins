package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.util.ServletUtility;

/**
 * WelcomeCtl Controller is responsible for displaying  
 * the Welcome Page after successful login.
 *
 * It simply forwards the request to Welcome View.
 *
 * @author Deepak Verma
 * @version 1.0
 */
@WebServlet(name = "WelcomeCtl", urlPatterns = { "/WelcomeCtl" })
public class WelcomeCtl extends BaseCtl {

    /**
     * Handles GET request and forwards to Welcome page.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page of Welcome screen.
     *
     * @return Welcome View Page path
     */
    @Override
    protected String getView() {
        return ORSView.WELCOME_VIEW;
    }
}
