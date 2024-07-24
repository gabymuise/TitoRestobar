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

    //Constructor vacio
    public Producto() {
        this.nombre = null;
        this.descripcion = null;
        this.precio = 0;
        this.costo = 0;
        this.elaboracion = false;
    }
        
    //Constructor de Producto
    public Producto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.costo = costo;
        this.elaboracion = elaboracion;
    }
    
    //Constructor de Base de Datos
    public Producto(int id, String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.costo = costo;
        this.elaboracion = elaboracion;
    }
    
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
