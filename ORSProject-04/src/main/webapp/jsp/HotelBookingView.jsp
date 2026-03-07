<%@page import="in.co.rays.proj4.controller.HotelBookingCtl"%>
<%@page import="in.co.rays.proj4.bean.HotelBookingBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Hotel Booking</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.HOTELBOOKING_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.HotelBookingBean"
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
				Hotel Booking
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
					<th align="left">Guest Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="guestName"
							placeholder="Enter Guest Name"
							value="<%=DataUtility.getStringData(bean.getGuestName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("guestName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Room Number<span style="color: red">*</span></th>
					<td>
						<input type="text" name="roomNb"
							placeholder="Enter Room Number"
							value="<%=DataUtility.getStringData(bean.getRoomNb())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("roomNb", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Check-in Date<span style="color: red">*</span></th>
					<td>
						<input type="date" name="checkinDate"
							value="<%=DataUtility.getDateString(bean.getCheckinDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("checkinDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Check-out Date<span style="color: red">*</span></th>
					<td>
						<input type="date" name="checkOutDate"
							value="<%=DataUtility.getDateString(bean.getCheckOutDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("checkOutDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Number Of Guest<span style="color: red">*</span></th>
					<td>
						<input type="text" name="numberOfGuest"
							placeholder="Enter Number Of Guest"
							value="<%=DataUtility.getStringData(bean.getNumberOfGuest())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("numberOfGuest", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Booking Amount<span style="color: red">*</span></th>
					<td>
						<input type="text" name="bookingAmount"
							placeholder="Enter Booking Amount"
							value="<%=DataUtility.getStringData(bean.getBookingAmount())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("bookingAmount", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Booking Status<span style="color: red">*</span></th>
					<td>
						<%=HTMLUtility.getList("bookingStatus",
								bean.getBookingStatus(), statusMap)%>
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("bookingStatus", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<td colspan="3" align="center">

						<%
							if (bean != null && bean.getId() > 0) {
						%>

						<input type="submit" name="operation"
							value="<%=HotelBookingCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=HotelBookingCtl.OP_CANCEL%>">

						<%
							} else {
						%>

						<input type="submit" name="operation"
							value="<%=HotelBookingCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=HotelBookingCtl.OP_RESET%>">

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