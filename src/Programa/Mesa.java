package Programa;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int id;
    private String nombre;
    private List<Pedido> pedidos;

    public Mesa(String nombre) {
        this.nombre = nombre;
        this.pedidos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void AbrirPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Pedido VerPedido() {
        return pedidos.isEmpty() ? null : pedidos.get(pedidos.size() - 1);
    }

    public void CerrarPedido() {
        if (!pedidos.isEmpty()) {
            pedidos.remove(pedidos.size() - 1);
        }
    }
}
