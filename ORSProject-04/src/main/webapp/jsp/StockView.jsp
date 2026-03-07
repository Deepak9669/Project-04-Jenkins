<%@page import="in.co.rays.proj4.controller.StockCtl"%>
<%@page import="in.co.rays.proj4.bean.StockBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Stock</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.STOCK_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.StockBean"
			scope="request"></jsp:useBean>

		<%
			HashMap<String, String> typeMap = (HashMap<String, String>) request.getAttribute("typeMap");
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

				Stock

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
					<th align="left">Stock Id<span style="color: red">*</span></th>

					<td><input type="text" name="stockId"
						placeholder="Enter Stock Id"
						value="<%=DataUtility.getStringData(bean.getStockId())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("stockId", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Stock Name<span style="color: red">*</span></th>

					<td><input type="text" name="stockName"
						placeholder="Enter Stock Name"
						value="<%=DataUtility.getStringData(bean.getStockName())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("stockName", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Price<span style="color: red">*</span></th>

					<td><input type="text" name="price" placeholder="Enter Price"
						value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("price", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Quantity<span style="color: red">*</span></th>

					<td><input type="text" name="quantity"
						placeholder="Enter Quantity"
						value="<%=DataUtility.getStringData(bean.getQuantity())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Stock Type<span style="color: red">*</span></th>

					<td><%=HTMLUtility.getList("stockType", "", typeMap)%></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("stockType", request)%>
					</font></td>
				</tr>


				<tr>

					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation" value="<%=StockCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
						value="<%=StockCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation" value="<%=StockCtl.OP_SAVE%>">

						<input type="submit" name="operation"
						value="<%=StockCtl.OP_RESET%>"> <%
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