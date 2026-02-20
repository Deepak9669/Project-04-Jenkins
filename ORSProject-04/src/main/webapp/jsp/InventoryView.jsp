<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InventoryCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.InventoryBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inventory</title>
</head>

<body>

	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.INVENTORY_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InventoryBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="margin-bottom: -15; color: navy">
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
				Inventory
			</h1>

			<!-- Success / Error -->
			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>
				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<!-- Hidden Fields -->
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<!-- Supplier Name -->
				<tr>
					<th align="left">Supplier Name <span style="color: red">*</span></th>
					<td><input type="text" name="supplierName"
						placeholder="Enter Supplier Name"
						value="<%=DataUtility.getStringData(bean.getSupplierName())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("supplierName", request)%>
					</font></td>
				</tr>

				<!-- Product -->
				<tr>
					<th align="left">Product <span style="color: red">*</span></th>
					<td><input type="text" name="product"
						placeholder="Enter Product"
						value="<%=DataUtility.getStringData(bean.getProduct())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("product", request)%>
					</font></td>
				</tr>

				<!-- Date -->
				<tr>
					<th align="left">Date <span style="color: red">*</span></th>
					<td><input type="text" name="dob" placeholder="dd/mm/yyyy"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%>
					</font></td>
				</tr>

				<!-- Quantity -->
				<tr>
					<th align="left">Quantity <span style="color: red">*</span></th>
					<td><input type="text" name="quantity"
						placeholder="Enter Quantity"
						value="<%=DataUtility.getStringData(bean.getQuantity())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%>
					</font></td>
				</tr>

				<!-- Buttons -->
				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=InventoryCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=InventoryCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

			</table>

		</div>

	</form>

</body>
</html>
