package Programa.Model;

/**
 * Representa un descuento aplicable a un subtotal.
 */
public class Descuento {
    private float porcentaje;

    /**
     * Crea una instancia de Descuento con el porcentaje especificado.
     * @param porcentaje El porcentaje de descuento (0-100).
     */
    public Descuento(float porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene el porcentaje de descuento.
     * @return El porcentaje de descuento.
     */
    public float getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece el porcentaje de descuento.
     * @param porcentaje El porcentaje de descuento (0-100).
     */
    public void setPorcentaje(float porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        this.porcentaje = porcentaje;
    }

    /**
     * Aplica el descuento al subtotal.
     * @param subtotal El monto original antes del descuento.
     * @return El monto después de aplicar el descuento.
     */
    public float aplicarDescuento(float subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser negativo.");
        }
        return subtotal - (subtotal * (porcentaje / 100));
    }
}
