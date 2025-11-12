package services;

import models.Producto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductosServicesImplement implements ProductoService {
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Laptop", "Computacion", 256.23),
                new Producto(2L, "Mouse", "Computacion", 30),
                new Producto(3L, "teclado", "Computacion", 40),
                new Producto(4L, "Xbox","Gamer", 500));
    }

    @Override
    public Optional<Producto> porId(Long id) {
        /**
         *Stream en java es convertir una lista en una secuencia de elementos
         * sobre la cual se pueden aplicar operaciones funcionales como filter, map, collect
         * FILTER: aqui se filtra los elementos del stream
         *
         * p-> Representa cada producto de la lista
         *
         * p.getId().equals(idProducto) compara el id del producto con id que recibimos como parametro
         * Si el id coincide, el producto pasa el filtro, si no, se descarta
         *
         * FindAny(): intenta encontrar un elemtno cualquiera del stream que cumpla la condicion
         * si no la encuentra devuelve un Optional<Producto>
         * Si no la encuentra devuelve Optional.empty()
         */

        return listar().stream().filter(p -> p.getIdProducto().equals(id)).findAny();

    }
}

