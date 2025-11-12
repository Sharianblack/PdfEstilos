package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.LoginService;
import services.LoginServiceImplement;
import services.ProductoService;
import services.ProductosServicesImplement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductoService service = new ProductosServicesImplement();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Listado de productos</title>");
            out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
            out.println("</head>");
            out.println("<body>");

            // 2. **APLICAR CLASES DE CONTENEDOR Y LISTA DE PRODUCTOS**
            out.println("<div class=\"container product-list\">");

            out.println("<h1>Listado de productos</h1>");

            if(usernameOptional.isPresent()) {
                out.println("<p style='margin-bottom: 20px;'>Hola " + usernameOptional.get() + " Bienvenido</p>");
            }

            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");
            if (usernameOptional.isPresent()) {
                out.println("<th>Precio</th>");
                out.println("<th>opciones</th>");
            }
            if (usernameOptional.isPresent()) {
                // 3. **APLICAR CLASE price-column AL ENCABEZADO**
                out.println("<th class=\"price-column\">Precio</th>");
            } else {
                // Muestra un marcador de posición si no está logueado
                out.println("<th class=\"hidden-price-placeholder\">Precio (Login requerido)</th>");
            }
            out.println("</tr>");
            out.println("</thead>"); // Cerrar thead
            out.println("<tbody>"); // Abrir tbody

            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                if(usernameOptional.isPresent()) {
                    out.println("<td>" + p.getPrecio() + "</td>");
                    out.println("<td><a href=\""
                            +req.getContextPath()
                            +"/agregar-carro?id=?"
                            +p.getIdProducto()
                            + "\">Agregar producto al carro</a></td>");
                }
                if (usernameOptional.isPresent()) {
                    // 4. **APLICAR CLASE price-column A LA CELDA**
                    out.println("<td class=\"price-column\">" + p.getPrecio() + "</td>");
                } else {
                    // 5. **APLICAR CLASE hidden-price-placeholder SI NO ESTÁ LOGUEADO**
                    out.println("<td class=\"hidden-price-placeholder\">***</td>");
                }
                out.println("</tr>");
            });

            out.println("</tbody>");
            out.println("</table>");

            // Cerrar el contenedor
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}