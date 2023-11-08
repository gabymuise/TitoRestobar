package Programa;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Date fechaHoraApertura;
    private Date fechaHoraCierre;
    public List<Item> items;
    private float descuento;

    public Pedido(Date fechaHoraApertura, List<Item> items) {
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = new ArrayList<>(items);
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
            total += item.getPrecio();
        }
        return total;
    }
    
    public float AplicarDescuentoPedido(float descuento) {
        float total = Subtotal();
        total -= descuento;
        return total;
    }

    public String TotalPedido() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
