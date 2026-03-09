<%@page import="in.co.rays.proj4.controller.OnlineCourseCtl"%>
<%@page import="in.co.rays.proj4.bean.OnlineCourseBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Online Course</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.ONLINE_COURSE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.OnlineCourseBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> courseMap = (HashMap<String, String>) request.getAttribute("courseMap");
		%>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>

				Update

				<%
					} else {
				%>

				Add

				<%
					}
				%>

				Online Course

			</h1>


			<div style="height: 15px; margin-bottom: 12px">

				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

			</div>


			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>


				<tr>

					<th align="left">Course Title<span style="color: red">*</span></th>

					<td><input type="text" name="courseTitle"
						placeholder="Enter Course Title"
						value="<%=DataUtility.getStringData(bean.getCourseTitle())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("courseTitle", request)%>

					</font></td>

				</tr>



				<tr>

					<th align="left">Module Name<span style="color: red">*</span></th>

					<td><input type="text" name="moduleName"
						placeholder="Enter Module Name"
						value="<%=DataUtility.getStringData(bean.getModuleName())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("moduleName", request)%>

					</font></td>

				</tr>



				<tr>

					<th align="left">Duration<span style="color: red">*</span></th>

					<td><input type="text" name="duration"
						placeholder="Enter Duration"
						value="<%=DataUtility.getStringData(bean.getDuration())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%>

					</font></td>

				</tr>



				<tr>

					<th align="left">Instructor Name<span style="color: red">*</span></th>

					<td><input type="text" name="instructorName"
						placeholder="Enter Instructor Name"
						value="<%=DataUtility.getStringData(bean.getInstructorName())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("instructorName", request)%>

					</font></td>

				</tr>



				<tr>

					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=OnlineCourseCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=OnlineCourseCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=OnlineCourseCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=OnlineCourseCtl.OP_RESET%>"> <%
 	}
 %>

					</td>

				</tr>


			</table>

		</div>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>