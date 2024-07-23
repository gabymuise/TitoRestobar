package Programa.Model;

/**
 * Representa un producto en el sistema.
 */
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private float costo;
    private boolean elaboracion;

    /**
     * Crea una instancia de Producto con los detalles especificados.
     * @param nombre El nombre del producto.
     * @param descripcion La descripción del producto.
     * @param precio El precio del producto.
     * @param costo El costo del producto.
     * @param elaboracion Indica si el producto requiere elaboración.
     */
    public Producto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.costo = costo;
        this.elaboracion = elaboracion;
    }
    
    public Producto(int id, String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.costo = costo;
        this.elaboracion = elaboracion;
    }
    
    public Producto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Producto(int productoId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public boolean isElaboracion() {
        return elaboracion;
    }

    public void setElaboracion(boolean elaboracion) {
        this.elaboracion = elaboracion;
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion + " (Precio: " + precio + ")";
    }
}
