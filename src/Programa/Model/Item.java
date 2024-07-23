package Programa.Model;

/**
 * Representa un artículo en un pedido, que incluye un producto, cantidad y subtotal.
 */
public class Item {
    private Producto producto;
    private int cantidad;

    public Item(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
    public float getSubtotal(){
        return cantidad * producto.getPrecio();
    }
}
