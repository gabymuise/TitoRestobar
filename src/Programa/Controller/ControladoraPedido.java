package Programa.Controller;

import Programa.Mesa;
import Programa.Pedido;
import java.util.List;

public class ControladoraPedido {

    // Implementa la lógica necesaria en los métodos comentados cuando sea necesario.
    // Por ahora, solo descomentamos el método mostrarPedidosMayoresA y manejamos posibles excepciones.

    public void mostrarPedidosMayoresA(Mesa mesa, float totalMinimo) {
        try {
            System.out.println("Pedidos en la mesa " + mesa.getId() + " con un total mayor a " + totalMinimo + ":");

            // Accede a la lista de pedidos de la mesa
            List<Pedido> pedidos = mesa.getPedidos();

            for (Pedido pedido : pedidos) {
                if (pedido.Subtotal() > totalMinimo) {
                    System.out.println("ID del Pedido: " + pedido.getId());
                    System.out.println("Total del Pedido: " + pedido.Subtotal());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar los pedidos: " + e.getMessage());
        }
    }
}
