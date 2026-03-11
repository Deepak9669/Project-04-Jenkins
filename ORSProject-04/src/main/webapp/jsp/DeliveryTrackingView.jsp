<%@page import="in.co.rays.proj4.controller.DeliveryTrackingCtl"%>
<%@page import="in.co.rays.proj4.bean.DeliveryTrackingBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Delivery Tracking</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.DELIVERY_TRACKING_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.DeliveryTrackingBean" scope="request"></jsp:useBean>

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

				Delivery Tracking

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

					<th align="left">Order Number<span style="color: red">*</span>
					</th>

					<td><input type="text" name="orderNumber"
						placeholder="Enter Order Number"
						value="<%=DataUtility.getStringData(bean.getOrderNumber())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("orderNumber", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Customer Name<span style="color: red">*</span>
					</th>

					<td><input type="text" name="customerName"
						placeholder="Enter Customer Name"
						value="<%=DataUtility.getStringData(bean.getCustomerName())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("customerName", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Delivery Status<span style="color: red">*</span>
					</th>

					<td><%=HTMLUtility.getList("deliveryStatus", bean.getDeliveryStatus(), statusMap)%></td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("deliveryStatus", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Delivery Date<span style="color: red">*</span>
					</th>

					<td><input type="date" name="deliveryDate"
						value="<%=DataUtility.getDateString(bean.getDeliveryDate())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("deliveryDate", request)%>

					</font></td>

				</tr>


				<tr>

					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=DeliveryTrackingCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=DeliveryTrackingCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=DeliveryTrackingCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=DeliveryTrackingCtl.OP_RESET%>"> <%
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