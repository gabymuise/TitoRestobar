package Programa.Model;

public class Item {
    private Producto producto;
    public int cantidad;

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
    
    
    public float getSubTotal(){
        return producto.getPrecio()* cantidad;
    }
    
}
