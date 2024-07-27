package Programa.Controller;

import Programa.DAO.DAOPedido;
import Programa.Model.Pedido;
import Programa.Model.Item;
import Programa.Model.Mesa;

import java.sql.SQLException;
import java.util.List;

public class ControladoraPedido {
    private DAOPedido daoPedido;

    // Constructor que inicializa la instancia de DAOPedido
    public ControladoraPedido() {
        daoPedido = new DAOPedido();
    }

    // Método para crear un nuevo pedido
    public void crearPedido(Pedido pedido) throws SQLException {
        daoPedido.crearPedido(pedido);
    }

    public void crearPedidoConDetalles(Pedido pedido, List<Item> items) throws SQLException {
        // Crear el pedido
        crearPedido(pedido);

        // Insertar los detalles del pedido
        for (Item item : items) {
            insertarDetallePedido(pedido, item);
        }
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
    
    public void insertarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.insertarItem(pedido, item);
    }
    
    public void insertarMesaPedido(Mesa mesa, Pedido pedido) throws SQLException {
        daoPedido.insertarMesaPedido(mesa, pedido);
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
