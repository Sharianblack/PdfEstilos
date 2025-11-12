package models;

import java.util.Objects;

public class ItemCarro {
    //Atributo de tipo carro y atributo de tipo producto
    private int cantidad;
    private Producto producto;
    //va a recibir la cantidad y el producto
    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    //creamos un metodo para comparar su ya un producto esta en la lista del carrito
    //de compraa y no repetido

    //este metodo permite agregar al mismo objeto otra cantidad
    @Override
    public boolean equals(Object o) {
        //si el objeto es igual a null retorna verdadero
        if (this == o) return true;
        //caso contrario retorna falso
        if (o == null || getClass() != o.getClass()) return false;
        ItemCarro itemCarro = (ItemCarro) o;

        return Objects.equals(producto.getIdProducto(), itemCarro.producto.getIdProducto())
                && Objects.equals(cantidad, itemCarro.cantidad);
    }
    public double getSubtotal(){
        return cantidad * producto.getPrecio();
    }
}
