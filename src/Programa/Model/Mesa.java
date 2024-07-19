package Programa.Model;

/**
 * Representa una mesa en el sistema.
 */
public class Mesa {
    private int id;
    private String nombre;

    /**
     * Crea una instancia de Mesa con el nombre especificado.
     * @param nombre El nombre de la mesa.
     */
    public Mesa(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID de la mesa.
     * @return El ID de la mesa.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la mesa.
     * @param id El nuevo ID de la mesa.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la mesa.
     * @return El nombre de la mesa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la mesa.
     * @param nombre El nuevo nombre de la mesa.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
