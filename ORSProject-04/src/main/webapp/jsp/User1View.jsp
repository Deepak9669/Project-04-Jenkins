<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.User1Ctl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add User1</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.USER1_CTL%>" method="post">
		<%@include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.User1Bean"
			scope="request"></jsp:useBean>


		<div align="center">
			<h1 align="center" style="margin-bottom: -10; color: navy;">User1</h1>
			<div style="height: 15px; margin-bottom: 12px">
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
				<h3>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>
			<table>
				<tr>
					<th>Name</th>
					<td><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"
						placeholder="enter your name"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				<tr>
					<th>Phone No</th>
					<td><input type="text" name="phoneno" value="<%=DataUtility.getStringData(bean.getPhoneno()) %>"
						placeholder="enter your phone no"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("phoneno", request) %></font></td>
				</tr>

				<tr>
					<th>Email Id</th>
					<td><input type="email" name="email" value="<%=DataUtility.getStringData(bean.getEmail()) %>"
						placeholder="enter your gmail"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email", request) %></font></td>

				</tr>
				<tr>
					<th>Address</th>
					<td><input type="text" name="address" value="<%=DataUtility.getStringData(bean.getAddress()) %>"
						placeholder="enter your address"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request) %></font></td>

				</tr>

				<tr>
					<th>status</th>


					<td><input type="text" name="status" value="<%=DataUtility.getStringData(bean.getStatus()) %>"
						placeholder="enter your status"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("status", request) %></font></td>

				</tr>
				<tr>
					<th>Gender</th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
						
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request) %></font></td>

				</tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=User1Ctl.OP_SAVE%>"><input type="submit"
						name="operation" value="<%=User1Ctl.OP_RESET%>"></td>


				</tr>



			</table>


		</div>
	</form>
	<%@ include file="Footer.jsp" %>
</body>
</html>