<%@page import="in.co.rays.proj4.controller.BrokerCtl"%>
<%@page import="in.co.rays.proj4.bean.BrokerBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Broker</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.BROKER_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BrokerBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> companyMap =
			(HashMap<String, String>) request.getAttribute("companyMap");
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
				Broker
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
					<th align="left">Broker Id<span style="color: red">*</span></th>
					<td>
						<input type="text" name="brokerId"
							placeholder="Enter Broker Id"
							value="<%=DataUtility.getStringData(String.valueOf(bean.getBrokerId()))%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("brokerId", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Broker Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="brokerName"
							placeholder="Enter Broker Name"
							value="<%=DataUtility.getStringData(bean.getBrokerName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("brokerName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Contact Number<span style="color: red">*</span></th>
					<td>
						<input type="text" name="contactNumber"
							placeholder="Enter Contact Number"
							value="<%=DataUtility.getStringData(bean.getContactNumber())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("contactNumber", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Company<span style="color: red">*</span></th>
					<td>
						<%=HTMLUtility.getList("company",
								bean.getCompany(), companyMap)%>
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("company", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<td colspan="3" align="center">

						<%
							if (bean != null && bean.getId() > 0) {
						%>
						<input type="submit" name="operation"
							value="<%=BrokerCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=BrokerCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation"
							value="<%=BrokerCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=BrokerCtl.OP_RESET%>">
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