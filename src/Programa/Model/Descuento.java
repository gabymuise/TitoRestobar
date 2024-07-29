package Programa.Model;

public class Descuento {
    private float porcentaje;

    // Constructor vacío
    public Descuento() {
        this.porcentaje = 0.0f;
    }

    // Constructor de Descuento
    public Descuento(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double aplicarDescuento(double subtotal) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        if (subtotal < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser negativo.");
        }
        return subtotal - (subtotal * (porcentaje / 100));
}
}
