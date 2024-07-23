package Programa.Model;

/**
 * Representa un artículo en un pedido, que incluye un producto, cantidad y subtotal.
 */
public class Item {
    private Producto producto;
    private int cantidad;
    private double subTotal;

    /**
     * Crea una instancia de Item con el producto, cantidad y subtotal especificados.
     * @param producto El producto asociado con el ítem.
     * @param cantidad La cantidad del producto.
     * @param subTotal El subtotal calculado para el ítem.
     */
    public Item(Producto producto, int cantidad, double subTotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    /**
     * Crea una instancia de Item con el producto y cantidad especificados.
     * El subtotal se calcula automáticamente como producto del precio del producto y la cantidad.
     * @param producto El producto asociado con el ítem.
     * @param cantidad La cantidad del producto.
     */
    public Item(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        // Calcula el subtotal como el precio del producto por la cantidad
        this.subTotal = producto.getPrecio() * cantidad;
    }

    // Getters y Setters
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
        // Recalcula el subtotal cuando la cantidad cambia
        this.subTotal = producto.getPrecio() * cantidad;
    }

    
    public float getSubtotal(){
        return cantidad * producto.getPrecio();
    }
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
