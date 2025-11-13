package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.DetalleCarro;
import models.ItemCarro;
import models.Producto;
import services.ProductoService;
import services.ProductosServicesImplement;

import java.io.IOException;
import java.util.Optional;
/**
 * Servlet encargado de agregar un producto al carrito de compras.
 * Mapeado a la URL "/agregar-carro" y espera un parámetro 'id' en la URL.
 */
//aqui se cambio lo que anteriormente era "/agregar-carro"
//a "/agregar-carro?id=?"
//asi el direccionamiento esta bien, y sale el 404 porque no hay datos aun
@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenemos el ID del producto desde el parámetro "idCarro" de la solicitud.
        Long id = Long.parseLong(req.getParameter("idCarro"));

        // Inicializar el servicio para obtener los detalles del producto.
        ProductoService service = new ProductosServicesImplement();

        // Buscar el producto por su ID usando el servicio. Se usa Optional para manejar si no existe.
        Optional<Producto> producto = service.porId(id);

        // Verificar si el producto existe antes de intentar agregarlo al carrito.
        if(producto.isPresent()) {
            // Si el producto existe, creamos un nuevo item para el carrito con cantidad 1.
            ItemCarro item = new ItemCarro(1, producto.get());

            // Obtenemos la sesión actual del usuario.
            HttpSession session = req.getSession();

            // Declaramos la variable para el detalle del carrito.
            DetalleCarro detalleCarro;

            // Verificamos si ya existe un carrito ("carro") en la sesión.
            if(session.getAttribute("carro") == null) {
                // Si no existe, creamos un nuevo objeto DetalleCarro (el carrito)
                detalleCarro = new DetalleCarro();
                // y lo guardamos en la sesión bajo el nombre "carro".
                session.setAttribute("carro", detalleCarro);
            }else  {
                // Si ya existe, lo recuperamos de la sesión y hacemos un 'cast' a DetalleCarro.
                detalleCarro = (DetalleCarro) session.getAttribute("carro");
            }

            // Añadimos el ItemCarro (el producto con cantidad 1) al DetalleCarro (el carrito).
            detalleCarro.addItemCarro(item);
        }

        // Una vez agregado (o no si el producto no existía), redirigimos al usuario a la página de ver carrito.
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}