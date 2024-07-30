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
    private double subtotal;
    private double total;

    public Pedido(Mesa mesa, Timestamp fechaHoraApertura, List<Item> items, Descuento descuento) {
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento;
        calcularSubtotalYTotal();
    }

    public Pedido() {
        this.mesa = null;
        this.fechaHoraApertura = new Timestamp(System.currentTimeMillis());
        this.fechaHoraCierre = null;
        this.items = new ArrayList<>();
        this.descuento = null;
    }

    public Pedido(int id, Mesa mesa, Timestamp fechaHoraApertura, Timestamp fechaHoraCierre, List<Item> items, Descuento descuento) {
        this.id = id;
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento;
        calcularSubtotalYTotal();
    }
    
    public Pedido(int id, Mesa mesa, Timestamp fechaHoraCierre) {
        this.id = id;
        this.mesa = mesa;
        this.fechaHoraCierre = fechaHoraCierre;
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

    public void addItem(Item item) {
        items.add(item);
        calcularSubtotalYTotal();
    }
        
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items != null ? items : new ArrayList<>();
        calcularSubtotalYTotal();
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
        calcularSubtotalYTotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void calcularSubtotalYTotal() {
        subtotal = 0;
        for (Item item : items) {
            subtotal += item.getSubtotal(); // Asegúrate de que `getSubtotal` en `Item` esté calculando correctamente.
        }
        total = subtotal - (subtotal * (descuento != null ? descuento.getPorcentaje() / 100 : 0));
    }
}
