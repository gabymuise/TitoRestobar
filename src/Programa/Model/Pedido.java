package Programa.Model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private Mesa mesa;
    private Date fechaHoraApertura;
    private Date fechaHoraCierre;
    private List<Item> items;
    private Descuento descuento;

    public Pedido(Mesa mesa, Date fechaHoraApertura, List<Item> items, Descuento descuento) {
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items;
        this.descuento = descuento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Date getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(Date fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public Date getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Date fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    // Método para calcular el total del pedido
    public float getTotal() {
        float total = 0;
        for (Item item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total - descuento.aplicarDescuento(total);
    }
}
