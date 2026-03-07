<%@page import="in.co.rays.proj4.controller.NotificationListCtl"%>
<%@page import="in.co.rays.proj4.bean.NotificationBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Notification List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.NotificationBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Notification List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.NOTIFICATION_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				Object n = request.getAttribute("nextListSize");
				int nextListSize = (n != null) ? DataUtility.getInt(n.toString()) : 0;

				List<NotificationBean> list = (List<NotificationBean>) ServletUtility.getList(request);

				if (list != null && !list.isEmpty()) {

					Iterator<NotificationBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Notification Code :</b></label> <input
						type="text" name="notificationCode" placeholder="Enter Code"
						value="<%=ServletUtility.getParameter("notificationCode", request)%>">&emsp;

						<label><b>Sent To :</b></label> <input type="text" name="sentTo"
						placeholder="Enter Receiver"
						value="<%=ServletUtility.getParameter("sentTo", request)%>">&emsp;

						<input type="submit" name="operation"
						value="<%=NotificationListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=NotificationListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="15%">Notification Code</th>
					<th width="25%">Message</th>
					<th width="15%">Sent To</th>
					<th width="15%">Sent Time</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {

							bean = (NotificationBean) it.next();
				%>

				<tr>

					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>
					<td><%=bean.getNotificationCode()%></td>
					<td><%=bean.getMessage()%></td>
					<td><%=bean.getSentTo()%></td>
					<td><%=bean.getSentTime()%></td>

					<td style="text-align: center;"><a
						href="NotificationCtl?id=<%=bean.getId()%>">Edit</a></td>

				</tr>

				<%
					}
				%>

			</table>

			<table style="width: 100%">
				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=NotificationListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=NotificationListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=NotificationListCtl.OP_DELETE%>">
					</td>

					<td align="right" style="width: 25%"><input type="submit"
						name="operation" value="<%=NotificationListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

			<%
				} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=NotificationListCtl.OP_BACK%>"></td>
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