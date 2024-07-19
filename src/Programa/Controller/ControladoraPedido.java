package Programa.Controller;

import Programa.DAO.DAOPedido;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Descuento;
import java.sql.SQLException;
import java.util.List;

public class ControladoraPedido {
    private DAOPedido daoPedido;

    // Constructor que inicializa la instancia de DAOPedido
    public ControladoraPedido() throws SQLException {
        daoPedido = new DAOPedido();
    }

    // Método para crear un nuevo pedido
    public void crearPedido(Pedido pedido) throws SQLException {
        daoPedido.crearPedido(pedido);
    }

    // Método para agregar un item a un pedido existente
    public void agregarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.agregarItem(pedido, item);
    }

    // Método para eliminar un item de un pedido existente
    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.eliminarItem(pedido, item);
    }

    // Método para listar todos los pedidos de una mesa específica
    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        return daoPedido.listarPedidosDeMesa(mesa);
    }

    // Método para calcular el total de un pedido, aplicando un descuento si es necesario
    public float calcularTotal(Pedido pedido, Descuento descuento) throws SQLException {
        float total = 0;
        List<Item> items = pedido.getItems();
        for (Item item : items) {
            total += item.getSubTotal();
        }
        return descuento.aplicarDescuento(total);
    }
}
