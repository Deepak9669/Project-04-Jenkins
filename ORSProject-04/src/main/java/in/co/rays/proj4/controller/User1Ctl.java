package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.User1Bean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.User1Model;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "User1Ctl", urlPatterns = { "/User1Ctl" })
public class User1Ctl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("phoneno"))) {
			request.setAttribute("phoneno", PropertyReader.getValue("error.require", "phoneno"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "email"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "address"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "gender"));
			pass = false;

		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		User1Bean bean = new User1Bean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhoneno(DataUtility.getString(request.getParameter("phoneno")));
		 bean.setEmail(DataUtility.getString(request.getParameter("email")));
		 bean.setAddress(DataUtility.getString(request.getParameter("address")));
		 bean.setStatus(DataUtility.getInt(request.getParameter("status")));
		 bean.setPhoneno(DataUtility.getString(request.getParameter("gender")));
		 
		 populateDTO(bean, request);
		
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		User1Model model = new User1Model();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			User1Bean bean = (User1Bean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("user added sucessfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Invalid login id & password", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER1_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.USER1_VIEW;
	}

}
