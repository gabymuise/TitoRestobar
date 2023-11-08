package Programa.Controller;

import Programa.Mesa;
import Programa.Pedido;

import java.util.List;

public class ControladoraPedido {
    
    public void mostrarPedidosMayoresA(Mesa mesa, float totalMinimo) {
        System.out.println("Pedidos en la mesa " + mesa.getId() + " con un total mayor a " + totalMinimo + ":");

        // Accede a la lista de pedidos de la mesa
        List<Pedido> pedidos = mesa.getPedidos();

        for (Pedido pedido : pedidos) {
            if (pedido.Subtotal() > totalMinimo) {
                System.out.println("ID del Pedido: " + pedido.getId());
                System.out.println("Total del Pedido: " + pedido.Subtotal());
            }
        }
    }
}


