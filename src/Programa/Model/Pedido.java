package Programa.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Mesa mesa;
    private Timestamp fechaHoraApertura;
    private Timestamp fechaHoraCierre;
    private List<Item> items;
    private Descuento descuento;

    public Pedido(Mesa mesa, Timestamp fechaHoraApertura, List<Item> items, Descuento descuento) {
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento != null ? descuento : new Descuento(0);
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

    public Timestamp getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(Timestamp fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public Timestamp getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Timestamp fechaHoraCierre) {
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

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    public float getTotal() {
        float total = 0;
        for (Item item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return descuento.aplicarDescuento(total);
    }
}
