package Programa.Model;

/**
 * Representa un artículo en un pedido, que incluye un producto, cantidad y subtotal.
 */
public class Item {
    private int id;
    private Producto producto;
    private int cantidad;

    // Constructor vacío
    public Item() {
        this.producto = new Producto();
        this.cantidad = 0;
    }

    // Constructor de Item
    public Item(Producto producto, int cantidad) {
        this.producto = producto != null ? producto : new Producto();
        this.cantidad = cantidad;
    }

    // Constructor de Base de Datos
    public Item(int id, Producto producto, int cantidad) {
        this.id = id;
        this.producto = producto != null ? producto : new Producto();
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto != null ? producto : new Producto();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        if (producto != null) {
            return cantidad * producto.getPrecio();
        } else {
            return 0;
        }
    }
}
