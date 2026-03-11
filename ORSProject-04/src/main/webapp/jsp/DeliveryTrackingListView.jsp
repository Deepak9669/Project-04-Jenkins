<%@page import="in.co.rays.proj4.controller.DeliveryTrackingListCtl"%>
<%@page import="in.co.rays.proj4.bean.DeliveryTrackingBean"%>
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
<title>Delivery Tracking List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.DeliveryTrackingBean" scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">

			Delivery Tracking List</h1>

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

		<form action="<%=ORSView.DELIVERY_TRACKING_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);

				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				Object n = request.getAttribute("nextListSize");

				int nextListSize = (n != null) ? DataUtility.getInt(n.toString()) : 0;

				HashMap<String, String> statusMap = (HashMap<String, String>) request.getAttribute("statusMap");

				List<DeliveryTrackingBean> list = (List<DeliveryTrackingBean>) ServletUtility.getList(request);

				if (list != null && !list.isEmpty()) {

					Iterator<DeliveryTrackingBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">

				<tr>

					<td align="center"><label><b>Order Number :</b></label> <input
						type="text" name="orderNumber" placeholder="Enter Order Number"
						value="<%=ServletUtility.getParameter("orderNumber", request)%>">

						&emsp; <label><b>Customer Name :</b></label> <input type="text"
						name="customerName" placeholder="Enter Customer Name"
						value="<%=ServletUtility.getParameter("customerName", request)%>">

						&emsp; <label><b>Status :</b></label> <%=HTMLUtility.getList("deliveryStatus", String.valueOf(bean.getDeliveryStatus()), statusMap)%> &emsp; <input type="submit" name="operation"
						value="<%=DeliveryTrackingListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=DeliveryTrackingListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th width="5%">S.No</th>

					<th width="10%">Order No</th>

					<th width="20%">Customer Name</th>

					<th width="20%">Delivery Status</th>

					<th width="20%">Delivery Date</th>

					<th width="5%">Edit</th>

				</tr>

				<%
					while (it.hasNext()) {

							bean = (DeliveryTrackingBean) it.next();
				%>

				<tr>

					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>

					<td><%=bean.getOrderNumber()%></td>

					<td><%=bean.getCustomerName()%></td>

					<td><%=bean.getDeliveryStatus()%></td>

					<td><%=DataUtility.getDateString(bean.getDeliveryDate())%></td>

					<td style="text-align: center;"><a
						href="DeliveryTrackingCtl?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
					}
				%>

			</table>

			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=DeliveryTrackingListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=DeliveryTrackingListCtl.OP_NEW%>">

					</td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=DeliveryTrackingListCtl.OP_DELETE%>">

					</td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=DeliveryTrackingListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
				} else {
			%>

			<table>

				<tr>

					<td align="right"><input type="submit" name="operation"
						value="<%=DeliveryTrackingListCtl.OP_BACK%>"></td>

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