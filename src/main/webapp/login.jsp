<%--
  Created by IntelliJ IDEA.
  User: adria
  Date: 11/nov/2025
  Time: 08:48 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<body>
<div class="container login-box"> <h1>
    Inicio de Sesion
</h1>
    <div>
        <form action="<%= request.getContextPath() %>/login" method="post">
            <div>
                <label for="user">Ingrese el Usuario</label>
                <input type="text" id="username" name="username">
            </div>
            <div>
                <label for="password">Ingrese la contraseña</label>
                <input type="password" id="password" name="password">
            </div>
            <div>
                <input type="submit" value="Entrar" class="btn-submit">
            </div>
        </form>
    </div>
</div>
</body>
</html>
