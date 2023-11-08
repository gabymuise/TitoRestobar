package Programa;

public class Producto {
   private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private float costo;
    private boolean elaboracion;

    public Producto(String nombre, String descripcion, float precio, float costo, java.lang.Boolean elaboracion) {
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

    
}
