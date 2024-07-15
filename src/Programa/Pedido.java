package Programa;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private Date fechaHoraApertura;
    private List<Item> items;

    public Pedido(Date fechaHoraApertura, List<Item> items) {
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items;
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
}
