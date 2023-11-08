package Programa;

public class Item {
    private Producto producto;
    public int cantidad;
    public float precioTotal;

    public Item(Producto producto, int cantidad, float precioTotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
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
    
    public void setPrecio(float precioTotal){
        this.precioTotal = precioTotal;
    }
    
    public float getPrecio(){
        return precioTotal;
    }
    
    public float CalcularTotal(){
        return producto.getPrecio()* cantidad;
    }
    
}
