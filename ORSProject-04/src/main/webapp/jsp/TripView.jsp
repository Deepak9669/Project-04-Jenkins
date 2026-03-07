<%@page import="in.co.rays.proj4.controller.TripCtl"%>
<%@page import="in.co.rays.proj4.bean.TripBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Trip</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.TRIP_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TripBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = 
				(HashMap<String, String>) request.getAttribute("statusMap");
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
				Trip
			</h1>

			<div style="height: 15px; margin-bottom: 12px">

				<h3 align="center">
					<font color="red">
						<%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="green">
						<%=ServletUtility.getSuccessMessage(request)%>
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
					<th align="left">Trip Code<span style="color: red">*</span></th>
					<td>
						<input type="text" name="tripCode"
							placeholder="Enter Trip Code"
							value="<%=DataUtility.getStringData(bean.getTripCode())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("tripCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Driver Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="driverName"
							placeholder="Enter Driver Name"
							value="<%=DataUtility.getStringData(bean.getDriverName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("driverName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Trip Date<span style="color: red">*</span></th>
					<td>
						<input type="date" name="tripDate"
							value="<%=DataUtility.getStringData(bean.getTripDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("tripDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Trip Status<span style="color: red">*</span></th>
					<td>
						<%=HTMLUtility.getList("tripStatus", 
								bean.getTripStatus(), statusMap)%>
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("tripStatus", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%>
						<input type="submit" name="operation" 
							value="<%=TripCtl.OP_UPDATE%>">
						<input type="submit" name="operation" 
							value="<%=TripCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" 
							value="<%=TripCtl.OP_SAVE%>">
						<input type="submit" name="operation" 
							value="<%=TripCtl.OP_RESET%>">
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