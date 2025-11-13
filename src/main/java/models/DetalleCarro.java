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
    public void addItemCarro(ItemCarro itemCarro){
        // Verificamos si el item (producto) ya está en la lista (usa ItemCarro.equals).
        if (items.contains(itemCarro)){
            // Si ya existe, lo buscamos usando streams y Optional para manejo seguro.
            Optional<ItemCarro> optionalItemCarro=items.stream()
                    // Filtramos para encontrar el item que es igual al que queremos agregar.
                    .filter(i -> i.equals(itemCarro))
                    .findAny(); // Encuentra cualquier coincidencia (solo debería haber una).

            // Si lo encontramos...
            if (optionalItemCarro.isPresent()){
                ItemCarro i = optionalItemCarro.get();
                // ...incrementamos la cantidad del item existente en 1.
                i.setCantidad(i.getCantidad()+1);
            }
        } else {
            // Si el item (producto) es nuevo, simplemente lo añadimos a la lista.
            this.items.add(itemCarro);
        }
    }

    /**
     * Metodo getter para obtener la lista de productos en el carrito.
     * @return La lista de ItemCarro.
     */
    public List <ItemCarro> getItem(){
        return items;
    }

    /**
     * Calcula el costo total de todos los items en el carrito.
     * @return La suma de todos los subtotales (cantidad * precio) de los items.
     */
    public double getTotal(){
        // Usamos streams para mapear cada ItemCarro a su subtotal y luego sumarlos todos.
        return items.stream().mapToDouble(ItemCarro::getSubtotal).sum();
    }
}