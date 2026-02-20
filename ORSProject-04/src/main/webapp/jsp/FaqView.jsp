<%@page import="in.co.rays.proj4.controller.FaqCtl"%>
<%@page import="in.co.rays.proj4.bean.FaqBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add FAQ</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.FAQ_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.FaqBean"
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
				FAQ
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
					<th align="left">Faq Code<span style="color: red">*</span></th>
					<td><input type="text" name="faqCode"
						placeholder="Enter Faq Code"
						value="<%=DataUtility.getStringData(bean.getFaqCode())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("faqCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Question<span style="color: red">*</span></th>
					<td><input type="text" name="question"
						placeholder="Enter Question"
						value="<%=DataUtility.getStringData(bean.getQuestion())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("question", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Answer<span style="color: red">*</span></th>
					<td><input type="text" name="answer"
						placeholder="Enter Answer"
						value="<%=DataUtility.getStringData(bean.getAnswer())%>">
					</td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("answer", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Faq Status<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("faqStatus", bean.getFaqStatus(), statusMap)%></td>
					<td style="position: fixed;"><font color="red">
							<%=ServletUtility.getErrorMessage("faqStatus", request)%>
					</font></td>
				</tr>

				<tr>
					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%>
						<input type="submit" name="operation" value="<%=FaqCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=FaqCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" value="<%=FaqCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=FaqCtl.OP_RESET%>">
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