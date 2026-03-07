<%@page import="in.co.rays.proj4.controller.ClientCtl"%>
<%@page import="in.co.rays.proj4.bean.ClientBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Client</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.CLIENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ClientBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> priorityMap = (HashMap<String, String>) request.getAttribute("priorityMap");
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
				Client
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

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Client Name<span style="color: red">*</span></th>
					<td><input type="text" name="clientName"
						placeholder="Enter Client Name"
						value="<%=DataUtility.getStringData(bean.getClientName())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("clientName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Address<span style="color: red">*</span></th>
					<td><input type="text" name="address"
						placeholder="Enter Address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("address", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Phone<span style="color: red">*</span></th>
					<td><input type="text" name="phone"
						placeholder="Enter Phone"
						value="<%=DataUtility.getStringData(bean.getPhone())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("phone", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Priority<span style="color: red">*</span></th>
					<td>
						<%=HTMLUtility.getList("priortiy", bean.getPriortiy(), priorityMap)%>
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("priortiy", request)%>
					</font></td>
				</tr>

				<tr>
					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%>
						<input type="submit" name="operation" value="<%=ClientCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=ClientCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" value="<%=ClientCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=ClientCtl.OP_RESET%>">
						<%
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