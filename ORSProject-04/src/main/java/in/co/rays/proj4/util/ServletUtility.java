package in.co.rays.proj4.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;

/**
 * ServletUtility is a helper class that provides common operations
 * for Servlets/Controllers such as:
 * <ul>
 *     <li>Forwarding and redirecting</li>
 *     <li>Setting and getting messages</li>
 *     <li>Handling beans and lists in request scope</li>
 *     <li>Pagination helpers (pageNo, pageSize)</li>
 *     <li>Centralized exception handling</li>
 * </ul>
 *
 * It is widely used by all controllers in ORSProject-4.
 *
 * @author Deepak Verma
 * @version 1.0
 */
public class ServletUtility {

	/**
	 * Forwards the request to given page.
	 *
	 * @param page     JSP/Servlet path
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	/**
	 * Sends redirect to given page.
	 *
	 * @param page     target URL
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.sendRedirect(page);
	}

	/**
	 * Gets an error message from request attributes by key.
	 *
	 * @param property attribute name
	 * @param request  HttpServletRequest
	 * @return message or empty string if not found
	 */
	public static String getErrorMessage(String property, HttpServletRequest request) {

		String val = (String) request.getAttribute(property);
		return (val == null) ? "" : val;
	}

	/**
	 * Gets a generic message from request attributes by key.
	 *
	 * @param property attribute name
	 * @param request  HttpServletRequest
	 * @return message or empty string if not found
	 */
	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		return (val == null) ? "" : val;
	}

	/**
	 * Sets global error message in request scope using BaseCtl.MSG_ERROR key.
	 *
	 * @param msg     error message
	 * @param request HttpServletRequest
	 */
	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

	/**
	 * Gets global error message from request scope.
	 *
	 * @param request HttpServletRequest
	 * @return message or empty string
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		return (val == null) ? "" : val;
	}

	/**
	 * Sets global success message in request scope using BaseCtl.MSG_SUCCESS key.
	 *
	 * @param msg     success message
	 * @param request HttpServletRequest
	 */
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	/**
	 * Gets global success message from request scope.
	 *
	 * @param request HttpServletRequest
	 * @return message or empty string
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		return (val == null) ? "" : val;
	}

	/**
	 * Stores a bean object in request scope under attribute "bean".
	 *
	 * @param bean    BaseBean
	 * @param request HttpServletRequest
	 */
	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);
	}

	/**
	 * Retrieves a bean object from request scope.
	 *
	 * @param request HttpServletRequest
	 * @return BaseBean or null
	 */
	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	/**
	 * Safely gets request parameter value.
	 *
	 * @param property parameter name
	 * @param request  HttpServletRequest
	 * @return parameter value or empty string
	 */
	public static String getParameter(String property, HttpServletRequest request) {
		String val = request.getParameter(property);
		return (val == null) ? "" : val;
	}

	/**
	 * Stores list in request scope under attribute "list".
	 *
	 * @param list    List object
	 * @param request HttpServletRequest
	 */
	public static void setList(List list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	/**
	 * Retrieves list from request scope.
	 *
	 * @param request HttpServletRequest
	 * @return List or null
	 */
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	/**
	 * Sets current page number in request.
	 *
	 * @param pageNo  current page number
	 * @param request HttpServletRequest
	 */
	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	/**
	 * Gets current page number from request.
	 *
	 * @param request HttpServletRequest
	 * @return page number, defaults to 0 if not found
	 */
	public static int getPageNo(HttpServletRequest request) {
		Object val = request.getAttribute("pageNo");
		return (val instanceof Integer) ? (Integer) val : 0;
	}

	/**
	 * Sets page size in request.
	 *
	 * @param pageSize page size
	 * @param request  HttpServletRequest
	 */
	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * Gets page size from request.
	 *
	 * @param request HttpServletRequest
	 * @return page size, defaults to 0 if not found
	 */
	public static int getPageSize(HttpServletRequest request) {
		Object val = request.getAttribute("pageSize");
		return (val instanceof Integer) ? (Integer) val : 0;
	}

	/**
	 * Centralized exception handling.
	 * <br>
	 * Stores the exception in request scope and redirects to ERROR_CTL.
	 *
	 * @param e        exception object
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setAttribute("exception", e);
		response.sendRedirect(ORSView.ERROR_CTL);
	}
}
