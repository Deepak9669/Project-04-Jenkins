<%@page import="in.co.rays.proj4.controller.AssetCtl"%>
<%@page import="in.co.rays.proj4.bean.AssetBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Asset</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.ASSET_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.AssetBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");
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
				Asset
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
					<th align="left">Asset Code<span style="color: red">*</span></th>
					<td><input type="text" name="assetCode"
						placeholder="Enter Asset Code"
						value="<%=DataUtility.getStringData(bean.getAssetCode())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("assetCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Asset Name<span style="color: red">*</span></th>
					<td><input type="text" name="assetName"
						placeholder="Enter Asset Name"
						value="<%=DataUtility.getStringData(bean.getAssetName())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("assetName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Assigned To<span style="color: red">*</span></th>
					<td><input type="text" name="assignedTo"
						placeholder="Enter Assigned To"
						value="<%=DataUtility.getStringData(bean.getAssignedTo())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("assignedTo", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Issue Date<span style="color: red">*</span></th>
					<td><input type="date" name="issueDate"
						value="<%=DataUtility.getDateString(bean.getIssueDate())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("issueDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Asset Status<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("assetStatus", bean.getAssetStatus(), statusMap)%></td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("assetStatus", request)%>
					</font></td>
				</tr>

				<tr>
					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%>
						<input type="submit" name="operation" value="<%=AssetCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=AssetCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" value="<%=AssetCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=AssetCtl.OP_RESET%>">
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