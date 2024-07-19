package Programa.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa un pedido en el sistema.
 */
public class Pedido {
    private int id;
    private Mesa mesa;
    private Date fechaHoraApertura;
    private Date fechaHoraCierre;
    private List<Item> items;
    private Descuento descuento;

    /**
     * Crea una instancia de Pedido con los detalles especificados.
     * @param mesa La mesa asociada al pedido.
     * @param fechaHoraApertura La fecha y hora de apertura del pedido.
     * @param items La lista de items en el pedido.
     * @param descuento El descuento aplicado al pedido.
     */
    public Pedido(Mesa mesa, Date fechaHoraApertura, List<Item> items, Descuento descuento) {
        this.mesa = mesa;
        this.fechaHoraApertura = fechaHoraApertura;
        this.items = items != null ? items : new ArrayList<>();
        this.descuento = descuento != null ? descuento : new Descuento(0); // Asigna un descuento por defecto si es nulo
    }

    /**
     * Constructor vacío para uso en la base de datos u otros fines.
     */
    public Pedido() {
        this.items = new ArrayList<>();
        this.descuento = new Descuento(0); // Asigna un descuento por defecto si es nulo
    }

    // Getters y Setters
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

    /**
     * Añade un item a la lista de items del pedido.
     * @param item El item a añadir.
     */
    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    /**
     * Calcula el total del pedido aplicando el descuento.
     * @return El total del pedido después de aplicar el descuento.
     */
    public float getTotal() {
        float total = 0;
        for (Item item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return descuento.aplicarDescuento(total);
    }
}
