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

/**
 * Servlet principal para mostrar el listado de productos.
 * Mapeado a "/productos.html" y "/productos".
 */
@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // OBTENER DATOS Y ESTADO DE SESIÓN
        // Inicializa el servicio de productos y lista todos los productos.
        ProductoService service = new ProductosServicesImplement();
        List<Producto> productos = service.listar();

        // Inicializa el servicio de login para verificar si hay un usuario logueado.
        LoginService auth = new LoginServiceImplement();
        // Obtiene el nombre de usuario si existe en la sesión/cookies.
        Optional<String> usernameOptional = auth.getUsername(req);

        // CONFIGURACIÓN DE LA RESPUESTA HTML
        resp.setContentType("text/html;charset=UTF-8");
        // Usamos try para asegurar el cierre del PrintWriter.
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Listado de productos</title>");
            // Enlaza la hoja de estilos, asumiendo que está en la raíz del contexto.
            out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
            out.println("</head>");
            out.println("<body>");

            // APLICAR CLASES DE CONTENEDOR Y LISTA DE PRODUCTOS
            out.println("<div class=\"container product-list\">");

            out.println("<h1>Listado de productos</h1>");

            // Muestra un saludo personalizado si el usuario está logueado.
            if(usernameOptional.isPresent()) {
                out.println("<p style='margin-bottom: 20px;'>Hola " + usernameOptional.get() + " Bienvenido</p>");
            }

            // INICIO DE LA TABLA Y ENCABEZADOS
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");

            // Lógica para mostrar las columnas de Precio y Opciones SÓLO si hay usuario.
            if (usernameOptional.isPresent()) {
                // APLICAR CLASE price-column AL ENCABEZADO
                out.println("<th class=\"price-column\">Precio</th>");
                out.println("<th>Opciones</th>");
            } else {
                // Muestra un marcador de posición si no está logueado.
                out.println("<th class=\"hidden-price-placeholder\">Precio (Login requerido)</th>");
            }

            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // ITERACIÓN Y PINTADO DE FILAS DE PRODUCTOS
            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");

                // El precio y el botón de agregar SOLO se muestran si hay un usuario.
                if(usernameOptional.isPresent()) {
                    // APLICAR CLASE price-column A LA CELDA DE PRECIO
                    out.println("<td class=\"price-column\">" + p.getPrecio() + "</td>");

                    // LINK DE AGREGAR AL CARRITO
                    // La parte clave es la concatenación de la URL
                    out.println("<td><a href=\""
                            + req.getContextPath()
                            // para arreglar el error 500 y que se muestre el error 404
                            //es en esta linea lo importante, tiene que ser el mismo href
                            //que como llamamos la clase "AgregarCarroServlet"
                            //en este caso tiene que ser "/agregar-carro?id=?" en ambos
                            // El '?' y el ID van separados del path,
                            // y el ID es el valor del parámetro "idCarro" que espera el otro servlet.
                            + "/agregar-carro?idCarro=" // Cambiado a idCarro, como lo esperas en el servlet
                            + p.getIdProducto()         // Aquí se concatena el ID REAL del producto
                            + "\">Agregar producto al carro</a></td>");
                } else {
                    // APLICAR CLASE hidden-price-placeholder SI NO ESTÁ LOGUEADO
                    // Mostramos un marcador para el precio cuando no hay login.
                    out.println("<td class=\"hidden-price-placeholder\">***</td>");
                    // Celda de opciones: no hay nada si no está logueado.
                    out.println("<td></td>");
                }
                out.println("</tr>");
            });

            // CIERRE DE ETIQUETAS
            out.println("</tbody>");
            out.println("</table>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}