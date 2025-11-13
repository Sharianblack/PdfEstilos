<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %><%--
  Created by IntelliJ IDEA.
  User: ndadri
  Date: 13/11/2025
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--traemos la sesion de scriplets--%>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>
<html>
<head>
    <title>Carro de compras</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles.css">
</head>
<body>
<h1>Carro de compras</h1>
<%
    if (detalleCarro == null || detalleCarro.getItem().isEmpty()) {%>
<p>Lo sentimos no hay productos en el carro de compras !</p>
<%} else {%>
<table>
    <tr>
        <td>IdProducto</td>
        <td>Nombre</td>
        <td>Precio</td>
        <td>Cantidad</td>
        <td>Subtotal</td>
    </tr>
    <%
        for (ItemCarro item : detalleCarro.getItem()){
    %>
    <tr>
        <td><%=item.getProducto().getIdProducto()%></td>
        <td><%=item.getProducto().getNombre()%></td>
        <td><%=item.getProducto().getPrecio()%></td>
        <td><%=item.getCantidad()%></td>
        <td><%=item.getSubtotal()%></td>
    </tr>
    <% } %>
    <tr>
        <td colspan="4" style="text-align: right;"><b>Subtotal:</b></td>
        <!-- se usa el "String.format("%.2f") para que solo se muestren 2 decimales -->
        <td><%= String.format("%.2f", detalleCarro.getSubtotal()) %></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align: right;"><b>IVA (15%):</b></td>
        <td><%= String.format("%.2f", detalleCarro.getIva()) %></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align: right">Total: </td>
        <td><%=String.format("%.2f", detalleCarro.getTotal())%></td>
    </tr>
</table>
<% } %>
<p><a href="<%=request.getContextPath()%>/productos">Seguir Comprando</a></p>
<p><a href="<%=request.getContextPath()%>/index.html">Volver</a></p>
</body>
</html>
