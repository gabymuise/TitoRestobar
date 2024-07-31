package Programa.Model;


public class Stock {
    private int id;
    private int cantidad;
    private Producto producto;

    // Constructor vacío
    public Stock() {
        this.cantidad = 0;
        this.producto = new Producto();
    }

    // Constructor de Stock
    public Stock(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto != null ? producto : new Producto();
    }

    // Constructor de Base de Datos
    public Stock(int id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto != null ? producto : new Producto();
    }

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
        this.producto = producto != null ? producto : new Producto();
    }

    @Override
    public String toString() {
        return "Stock[producto=" + producto.getNombre() + ", cantidad=" + cantidad + "]";
    }
}
