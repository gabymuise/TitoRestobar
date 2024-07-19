package Programa.Model;

/**
 * Representa el stock de un producto en el sistema.
 */
public class Stock {
    private int id;
    private int cantidad;
    private Producto producto;

    /**
     * Crea una instancia de Stock con la cantidad y el producto especificado.
     * @param cantidad La cantidad del producto en stock.
     * @param producto El producto asociado al stock.
     */
    public Stock(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public Stock() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Stock[producto=" + producto.getNombre() + ", cantidad=" + cantidad + "]";
    }
}
