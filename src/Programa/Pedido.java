package Programa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private Date fechaHoraApertura;
    private Date fechaHoraCierre;
    private List<Item> items;
    private float descuento;

    public Pedido(Date fechaHoraApertura, List<Item> items, float descuento, Date fechaHoraCierre) {
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento;
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public void AgregarItem(Item item) {
        items.add(item);
    }

    public void EliminarItem(Item item) {
        items.remove(item);
    }

    public float Subtotal() {
        float total = 0;
        for (Item item : items) {
            total += item.getPrecio() * item.getCantidad();
        }
        return total;
    }

    public float TotalPedido() {
        return Subtotal() - descuento;
    }
}
