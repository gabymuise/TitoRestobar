package Programa.Controller;

import Programa.DAO.DAOPedido;
import Programa.Model.Pedido;
import Programa.Model.Item;
import Programa.Model.Producto;

import java.sql.SQLException;
import java.util.List;

public class ControladoraPedido {
    private DAOPedido daoPedido;

    // Constructor que inicializa la instancia de DAOPedido
    public ControladoraPedido() {
        daoPedido = new DAOPedido();
    }

    // Método para crear un nuevo pedido
    public boolean crearPedido(Pedido pedido) throws SQLException {
        return daoPedido.crearPedido(pedido);
    }

    public List<Pedido> obtenerTodosLosPedidosActivos() throws SQLException {
        return daoPedido.obtenerTodosLosPedidosActivos();
    }
    
    public void insertarDetallePedido(Pedido pedido, Item item) throws SQLException {
        daoPedido.insertarDetallePedido(pedido, item);
    }
    
    // Método para obtener un pedido por su ID
    public Pedido obtenerPedido(int pedidoId) throws SQLException {
        return daoPedido.obtenerPedido(pedidoId);
    }

    // Método para obtener los items de un pedido por su ID
    public List<Item> obtenerItemsPorPedido(int pedidoId) throws SQLException {
        return daoPedido.obtenerItemsPorPedido(pedidoId);
    }

    // Método para eliminar un pedido por ID
    public void eliminarPedido(int pedidoId) throws SQLException {
        daoPedido.eliminarPedido(pedidoId);
    }

    // Método para eliminar un pedido de una mesa
    public void eliminarPedidoDeMesa(int mesaId, int pedidoId) throws SQLException {
        daoPedido.eliminarPedidoDeMesa(mesaId, pedidoId);
    }
}
