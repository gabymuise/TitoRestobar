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

    public ControladoraPedido() throws SQLException {
        daoPedido = new DAOPedido();
    }

    public void crearPedido(Pedido pedido) throws SQLException {
        daoPedido.crearPedido(pedido);
    }

    public void agregarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.agregarItem(pedido, item);
    }

    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.eliminarItem(pedido, item);
    }

    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        return daoPedido.listarPedidosDeMesa(mesa);
    }

    public float calcularTotal(Pedido pedido, Descuento descuento) throws SQLException {
        float total = 0;
        List<Item> items = pedido.getItems();
        for (Item item : items) {
            total += item.getSubTotal();
        }
        return descuento.aplicarDescuento(total);
    }

}