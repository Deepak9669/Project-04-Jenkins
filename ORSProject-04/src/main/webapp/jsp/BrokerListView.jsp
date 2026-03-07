<%@page import="in.co.rays.proj4.controller.BrokerListCtl"%>
<%@page import="in.co.rays.proj4.bean.BrokerBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Broker List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.BrokerBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Broker List
		</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
		</div>

		<form action="<%=ORSView.BROKER_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				Object n = request.getAttribute("nextListSize");
				int nextListSize = (n != null)
						? DataUtility.getInt(n.toString()) : 0;

				HashMap<String, String> companyMap =
					(HashMap<String, String>) request.getAttribute("companyMap");

				List<BrokerBean> list =
					(List<BrokerBean>) ServletUtility.getList(request);

				if (list != null && !list.isEmpty()) {

					Iterator<BrokerBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- 🔍 Search Section -->
			<table style="width: 100%">
				<tr>
					<td align="center">

						<label><b>Broker Name :</b></label>
						<input type="text" name="brokerName"
							placeholder="Enter Broker Name"
							value="<%=ServletUtility.getParameter("brokerName", request)%>">
						&emsp;

						<label><b>Company :</b></label>
						<%=HTMLUtility.getList("company",
								bean.getCompany(),
								companyMap)%>
						&emsp;

						<input type="submit" name="operation"
							value="<%=BrokerListCtl.OP_SEARCH%>">

						<input type="submit" name="operation"
							value="<%=BrokerListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<!-- 📋 Table Section -->
			<table border="1"
				style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%">
						<input type="checkbox" id="selectall" />
					</th>
					<th width="5%">S.No</th>
					<th width="15%">Broker Id</th>
					<th width="20%">Broker Name</th>
					<th width="20%">Contact Number</th>
					<th width="20%">Company</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						bean = (BrokerBean) it.next();
				%>

				<tr>
					<td style="text-align: center;">
						<input type="checkbox" class="case"
							name="ids"
							value="<%=bean.getId()%>">
					</td>

					<td><%=index++%></td>
					<td><%=bean.getBrokerId()%></td>
					<td><%=bean.getBrokerName()%></td>
					<td><%=bean.getContactNumber()%></td>
					<td><%=bean.getCompany()%></td>

					<td style="text-align: center;">
						<a href="BrokerCtl?id=<%=bean.getId()%>">
							Edit
						</a>
					</td>
				</tr>

				<%
					}
				%>

			</table>

			<!-- 🔄 Pagination & Buttons -->
			<table style="width: 100%">
				<tr>

					<td style="width: 25%">
						<input type="submit"
							name="operation"
							value="<%=BrokerListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center"
						style="width: 25%">
						<input type="submit"
							name="operation"
							value="<%=BrokerListCtl.OP_NEW%>">
					</td>

					<td align="center"
						style="width: 25%">
						<input type="submit"
							name="operation"
							value="<%=BrokerListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%"
						align="right">
						<input type="submit"
							name="operation"
							value="<%=BrokerListCtl.OP_NEXT%>"
							<%=nextListSize != 0
								? "" : "disabled"%>>
					</td>

				</tr>
			</table>

			<%
				} else {
			%>

			<table>
				<tr>
					<td align="right">
						<input type="submit"
							name="operation"
							value="<%=BrokerListCtl.OP_BACK%>">
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