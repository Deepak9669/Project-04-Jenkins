<%@page import="in.co.rays.proj4.controller.OnlineCourseListCtl"%>
<%@page import="in.co.rays.proj4.bean.OnlineCourseBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BaseBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>

<title>Online Course List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.OnlineCourseBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Online Course List</h1>

		<div style="height: 15px; margin-bottom: 12px">

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

		</div>

		<form action="<%=ORSView.ONLINE_COURSE_LIST_CTL1%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				Object n = request.getAttribute("nextListSize");
				int nextListSize = (n != null) ? DataUtility.getInt(n.toString()) : 0;

				HashMap<String, String> courseMap = (HashMap<String, String>) request.getAttribute("courseMap");

				List<OnlineCourseBean> list = (List<OnlineCourseBean>) ServletUtility.getList(request);

				if (list != null && !list.isEmpty()) {

					Iterator<OnlineCourseBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">

				<tr>

					<td align="center"><label><b>Course Title :</b></label> <%=HTMLUtility.getList("courseTitle", String.valueOf(bean.getCourseTitle()), courseMap)%> &nbsp;&nbsp; <input
						type="submit" name="operation"
						value="<%=OnlineCourseListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=OnlineCourseListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th width="5%">S.No</th>

					<th width="25%">Course Title</th>

					<th width="25%">Module Name</th>

					<th width="15%">Duration</th>

					<th width="20%">Instructor Name</th>

					<th width="10%">Edit</th>

				</tr>

				<%
					while (it.hasNext()) {

							bean = (OnlineCourseBean) it.next();
				%>

				<tr>

					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>

					<td><%=bean.getCourseTitle()%></td>

					<td><%=bean.getModuleName()%></td>

					<td><%=bean.getDuration()%></td>

					<td><%=bean.getInstructorName()%></td>

					<td style="text-align: center"><a
						href="OnlineCourseCtl?id=<%=bean.getId()%>">Edit</a></td>

				</tr>

				<%
					}
				%>

			</table>

			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=OnlineCourseListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=OnlineCourseListCtl.OP_NEW%>">

					</td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=OnlineCourseListCtl.OP_DELETE%>">

					</td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=OnlineCourseListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
				} else {
			%>

			<table>

				<tr>

					<td align="right"><input type="submit" name="operation"
						value="<%=OnlineCourseListCtl.OP_BACK%>"></td>

				</tr>

			</table>

			<%
				}
			%>

		</form>

	</div>

	<%@ include file="Footer.jsp"%>

</body>
</html>