package Programa;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private Date fechaHoraApertura;
    private List<Item> items;
    private Descuento descuento;

    public Pedido(Date fechaHoraApertura, List<Item> items, Descuento descuento) {
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

    public Date getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(Date fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
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
    
    public float getTotal() {
        float subTotal = 0.0f;
        for (Item item : this.items) {
            subTotal += item.getSubTotal();
        }
        return this.descuento.aplicarDescuento(subTotal);
    }
}
