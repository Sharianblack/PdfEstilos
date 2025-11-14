<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carro de compras</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles.css">
</head>
<body>
<div class="container product-list">
    <h1>Carro de compras</h1>

    <%
        if (detalleCarro == null || detalleCarro.getItem().isEmpty()) {
    %>
    <p class="cart-empty">Lo sentimos no hay productos en el carro de compras!</p>
    <div class="cart-actions">
        <a href="<%=request.getContextPath()%>/productos">Ir a productos</a>
        <a href="<%=request.getContextPath()%>/index.html" class="volver">Volver</a>
    </div>
    <%
    } else {
    %>

    <table>
        <thead>
        <tr>
            <th>IdProducto</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (ItemCarro item : detalleCarro.getItem()){
        %>
        <tr>
            <td><%=item.getProducto().getIdProducto()%></td>
            <td><%=item.getProducto().getNombre()%></td>
            <td class="price-column">$<%=String.format("%.2f", item.getProducto().getPrecio())%></td>
            <td><%=item.getCantidad()%></td>
            <td class="price-column">$<%=String.format("%.2f", item.getSubtotal())%></td>
        </tr>
        <% } %>
        <tr class="totales">
            <td colspan="4" style="text-align: right;"><b>Subtotal:</b></td>
            <td class="price-column">$<%= String.format("%.2f", detalleCarro.getSubtotal()) %></td>
        </tr>
        <tr class="totales">
            <td colspan="4" style="text-align: right;"><b>IVA (15%):</b></td>
            <td class="price-column">$<%= String.format("%.2f", detalleCarro.getIva()) %></td>
        </tr>
        <tr class="total-final">
            <td colspan="4" style="text-align: right"><b>TOTAL:</b></td>
            <td class="price-column"><b>$<%=String.format("%.2f", detalleCarro.getTotal())%></b></td>
        </tr>
        </tbody>
    </table>

    <div class="cart-actions">
        <a href="<%=request.getContextPath()%>/descargar-factura" class="btn-download-pdf">
            📄 Descargar Factura PDF
        </a>
        <a href="<%=request.getContextPath()%>/productos">Seguir Comprando</a>
        <a href="<%=request.getContextPath()%>/index.html" class="volver">Volver</a>
    </div>

    <% } %>
</div>
</body>
</html>
