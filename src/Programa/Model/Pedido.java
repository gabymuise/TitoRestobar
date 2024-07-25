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

    // Pedido Activo
    public Pedido(Mesa mesa, Timestamp fechaHoraApertura, List<Item> items, Descuento descuento) {
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento;
    }

    // Constructor vacío
    public Pedido() {
        this.mesa = null;
        this.fechaHoraApertura = null;
        this.fechaHoraCierre = null;
        this.items = new ArrayList<>();
        this.descuento = null;
    }

    // Constructor de Pedido
    public Pedido(Mesa mesa, Timestamp fechaHoraApertura, Timestamp fechaHoraCierre, List<Item> items, Descuento descuento) {
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento;
    }

    // Constructor de Base de Datos
    public Pedido(int id, Mesa mesa, Timestamp fechaHoraApertura, Timestamp fechaHoraCierre, List<Item> items, Descuento descuento) {
        this.id = id;
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento;
    }
    
    // Constructor de Base de Datos Actual
    public Pedido(int id, Timestamp fechaHoraApertura, Timestamp fechaHoraCierre, Descuento descuento) {
        this.id = id;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.descuento = descuento;
        this.items = new ArrayList<>();
    }
    
    // Constructor Momentáneo
    public Pedido(int id, Timestamp fechaHoraApertura, Timestamp fechaHoraCierre) {
        this.id = id;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.items = new ArrayList<>();
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
        this.items = items != null ? items : new ArrayList<>();
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }
    
    public double getTotal() {
        float total = 0;
        for (Item item : items) {
            if (item.getProducto() != null) {
                total += item.getProducto().getPrecio() * item.getCantidad();
            }
        }
        if (descuento != null) {
            return descuento.aplicarDescuento(total);
        } else {
            return total;
        }
    }
}
