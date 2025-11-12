package services;

import jakarta.servlet.http.HttpServlet;
import models.Producto;

import java.util.Arrays;
import java.util.List;

public class ProductosServicesImplement implements ProductoService {

    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Laptop", "Computacion", 256.23),
                new Producto(2L, "Mouse", "Computacion", 30),
                new Producto(3L, "teclado", "Computacion", 40),
                new Producto(4L, "Xbox","Gamer", 500)
        );
    }
}
