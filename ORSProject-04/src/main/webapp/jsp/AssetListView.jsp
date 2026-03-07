<%@page import="in.co.rays.proj4.controller.AssetListCtl"%>
<%@page import="in.co.rays.proj4.bean.AssetBean"%>
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
<title>Asset List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.AssetBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Asset
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.ASSET_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				Object n = request.getAttribute("nextListSize");
				int nextListSize = (n != null) ? DataUtility.getInt(n.toString()) : 0;

				HashMap<String,String> statusMap =
				(HashMap<String,String>) request.getAttribute("statusMap");

				List<AssetBean> list =
				(List<AssetBean>) ServletUtility.getList(request);

				if (list != null && !list.isEmpty()) {

					Iterator<AssetBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center">

						<label><b>Asset Code :</b></label>
						<input type="text" name="assetCode"
							placeholder="Enter Asset Code"
							value="<%=ServletUtility.getParameter("assetCode", request)%>">&emsp;

						<label><b>Asset Name :</b></label>
						<input type="text" name="assetName"
							placeholder="Enter Asset Name"
							value="<%=ServletUtility.getParameter("assetName", request)%>">&emsp;

						<label><b>Status :</b></label>
						<%=HTMLUtility.getList("assetStatus",
								String.valueOf(bean.getAssetStatus()),
								statusMap)%>&emsp;

						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_SEARCH%>"> &nbsp;

						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="15%">Asset Code</th>
					<th width="20%">Asset Name</th>
					<th width="20%">Assigned To</th>
					<th width="15%">Issue Date</th>
					<th width="10%">Status</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						bean = (AssetBean) it.next();
				%>

				<tr>
					<td style="text-align: center;">
						<input type="checkbox" class="case" name="ids"
							value="<%=bean.getId()%>">
					</td>
					<td><%=index++%></td>
					<td><%=bean.getAssetCode()%></td>
					<td><%=bean.getAssetName()%></td>
					<td><%=bean.getAssignedTo()%></td>
					<td><%=DataUtility.getDateString(bean.getIssueDate())%></td>
					<td><%=bean.getAssetStatus()%></td>

					<td style="text-align: center;">
						<a href="AssetCtl?id=<%=bean.getId()%>">Edit</a>
					</td>
				</tr>

				<%
					}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%">
						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%" align="right">
						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_NEXT%>"
							<%=nextListSize != 0 ? "" : "disabled"%>>
					</td>
				</tr>
			</table>

			<%
				} else {
			%>

			<table>
				<tr>
					<td align="right">
						<input type="submit" name="operation"
							value="<%=AssetListCtl.OP_BACK%>">
					</td>
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