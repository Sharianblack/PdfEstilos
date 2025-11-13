package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Representa el Carrito de Compras (DetalleCarro).
 * Contiene una lista de ItemCarro (productos con su cantidad).
 */
public class DetalleCarro {
    // La lista que almacena los productos agregados al carrito.
    private List<ItemCarro> items;

    // Porcentaje de IVA (15%)
    private static final double porcentajeIva = 0.15;

    /**
     * Constructor. Inicializa la lista de items como un nuevo ArrayList.
     */
    public DetalleCarro() {
        this.items = new ArrayList<>();
    }

    /**
     * Implementamos un metodo para agregar un producto al carro.
     * Si el producto ya existe, incrementa la cantidad; si no, lo agrega.
     */
    public void addItemCarro(ItemCarro itemCarro) {
        if (items.contains(itemCarro)) {
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();

            if (optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }
        } else {
            this.items.add(itemCarro);
        }
    }

    /**
     * Metodo getter para obtener la lista de productos en el carrito.
     * @return La lista de ItemCarro.
     */
    public List<ItemCarro> getItem() {
        return items;
    }

    /**
     * Calcula el subtotal (sin IVA).
     * @return La suma de todos los subtotales (cantidad * precio) de los items.
     */
    public double getSubtotal() {
        return items.stream().mapToDouble(ItemCarro::getSubtotal).sum();
    }

    /**
     * Calcula el IVA sobre el subtotal.
     * @return El valor del IVA.
     */
    public double getIva() {
        return getSubtotal() * porcentajeIva;
    }

    /**
     * Calcula el total incluyendo el IVA.
     * @return El total con IVA.
     */
    public double getTotal() {
        return getSubtotal() + getIva();
    }
}
