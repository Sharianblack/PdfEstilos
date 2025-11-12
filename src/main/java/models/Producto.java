package models;

/**
 *Comentarios
 */

public class Producto {
    //Encapsulamos y declaramos las variables del objeto producto
    private long idProducto;
    private String nombre;
    private String tipo;
    private double precio;
    //Realizamos un constructor
    public Producto() {
    }
    //Este contructor recibe los parametros que inicializamos
    public Producto(long idProducto, String nombre, String tipo, double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }
    //Permite el acceso a esos atributos
    //Si no es void retorna un valor
    public long getIdProducto() {
        return idProducto;
    }
    //Modificar ingresar el metodo de un producto  y recibe un parametro y lo inicializa
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    //Manda a llamar el metodo getnombre
    public String getNombre() {
        return nombre;
    }
    //Inicializa esa variable
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
