package Programa.Model;

/**
 * Representa una mesa en el sistema.
 */
public class Mesa {
    private int id;
    private String nombre;

   // Constructor vacío
    public Mesa() {
        this.nombre = null;
    }

    // Constructor de Mesa
    public Mesa(String nombre) {
        this.nombre = nombre;
    }

    // Constructor de Base de Datos
    public Mesa(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    @Override
    public String toString() {
        return nombre;
    }
}
