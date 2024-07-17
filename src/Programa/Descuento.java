package Programa;

public class Descuento {
    private float porcentaje;

    public Descuento(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public float aplicarDescuento(float subtotal) {
        return subtotal - (subtotal *(porcentaje/100));
    }
}
