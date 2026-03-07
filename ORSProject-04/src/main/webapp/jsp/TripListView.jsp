<%@page import="in.co.rays.proj4.controller.TripListCtl"%>
<%@page import="in.co.rays.proj4.bean.TripBean"%>
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
<title>Trip List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TripBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Trip List
		</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.TRIP_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				Object n = request.getAttribute("nextListSize");
				int nextListSize = (n != null) ? DataUtility.getInt(n.toString()) : 0;

				HashMap<String,String> statusMap =
				(HashMap<String,String>) request.getAttribute("statusMap");

				List<TripBean> list =
				(List<TripBean>) ServletUtility.getList(request);

				if (list != null && !list.isEmpty()) {

					Iterator<TripBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- ================= SEARCH BAR ================= -->

			<table style="width: 100%">
				<tr>
					<td align="center">

						<label><b>Trip Code :</b></label>
						<input type="text" name="tripCode"
							placeholder="Enter Trip Code"
							value="<%=ServletUtility.getParameter("tripCode", request)%>">&emsp;

						<label><b>Driver Name :</b></label>
						<input type="text" name="driverName"
							placeholder="Enter Driver Name"
							value="<%=ServletUtility.getParameter("driverName", request)%>">&emsp;

						<label><b>Status :</b></label>
						<%=HTMLUtility.getList("tripStatus",
								String.valueOf(bean.getTripStatus()),
								statusMap)%>&emsp;

						<input type="submit" name="operation"
							value="<%=TripListCtl.OP_SEARCH%>">

						<input type="submit" name="operation"
							value="<%=TripListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<!-- ================= TABLE ================= -->

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="20%">Trip Code</th>
					<th width="20%">Driver Name</th>
					<th width="20%">Trip Date</th>
					<th width="15%">Trip Status</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						bean = (TripBean) it.next();
				%>

				<tr>
					<td style="text-align: center;">
						<input type="checkbox" class="case" name="ids"
							value="<%=bean.getId()%>">
					</td>
					<td><%=index++%></td>
					<td><%=bean.getTripCode()%></td>
					<td><%=bean.getDriverName()%></td>
					<td><%=bean.getTripDate()%></td>
					<td><%=bean.getTripStatus()%></td>

					<td style="text-align: center;">
						<a href="TripCtl?id=<%=bean.getId()%>">Edit</a>
					</td>
				</tr>

				<%
					}
				%>
			</table>

			<!-- ================= PAGINATION ================= -->

			<table style="width: 100%">
				<tr>

					<td style="width: 25%">
						<input type="submit" name="operation"
							value="<%=TripListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=TripListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=TripListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%" align="right">
						<input type="submit" name="operation"
							value="<%=TripListCtl.OP_NEXT%>"
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
							value="<%=TripListCtl.OP_BACK%>">
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