<%@page import="in.co.rays.proj4.controller.QueueCtl"%>
<%@page import="in.co.rays.proj4.bean.QueueBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Queue</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.QUEUE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.QueueBean"
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
				Queue
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
					<th align="left">Queue Code<span style="color: red">*</span></th>
					<td><input type="text" name="queueCode"
						placeholder="Enter Queue Code"
						value="<%=DataUtility.getStringData(bean.getQueueCode())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("queueCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Queue Name<span style="color: red">*</span></th>
					<td><input type="text" name="queueName"
						placeholder="Enter Queue Name"
						value="<%=DataUtility.getStringData(bean.getQueueName())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("queueName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Queue Size<span style="color: red">*</span></th>
					<td><input type="text" name="queueSize"
						placeholder="Enter Queue Size"
						value="<%=DataUtility.getStringData(bean.getQueueSize())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("queueSize", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Queue Status<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("queueStatus", bean.getQueueStatus(), statusMap)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("queueStatus", request)%>
					</font></td>
				</tr>

				<!-- ✅ BUTTON ROW FIXED -->
				<tr>
					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation" value="<%=QueueCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
						value="<%=QueueCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation" value="<%=QueueCtl.OP_SAVE%>">

						<input type="submit" name="operation"
						value="<%=QueueCtl.OP_RESET%>"> <%
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
