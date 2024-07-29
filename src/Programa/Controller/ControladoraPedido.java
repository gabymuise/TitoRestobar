package Programa.Controller;

import Programa.DAO.DAOPedido;
import Programa.Model.Pedido;
import Programa.Model.Item;
import Programa.Model.Mesa;

import java.sql.SQLException;
import java.util.List;

public class ControladoraPedido {
    private final DAOPedido daoPedido;

    // Constructor que inicializa la instancia de DAOPedido
    public ControladoraPedido() {
        daoPedido = new DAOPedido();
    }

    // Método para crear un nuevo pedido
    public void crearPedido(Pedido pedido) throws SQLException {
        daoPedido.crearPedido(pedido);
    }

    // Método para crear un nuevo pedido con sus detalles
    public void crearPedidoConDetalles(Pedido pedido, List<Item> items) throws SQLException {
        crearPedido(pedido);

        for (Item item : items) {
            insertarDetallePedido(pedido, item);
        }
    }

    // Método para obtener todos los pedidos activos
    public List<Pedido> obtenerTodosLosPedidosActivos() throws SQLException {
        return daoPedido.obtenerTodosLosPedidosActivos();
    }

    // Método para insertar un detalle de pedido
    public void insertarDetallePedido(Pedido pedido, Item item) throws SQLException {
        daoPedido.insertarDetallePedido(pedido, item);
    }
    
    public void insertarMesaPedido(Mesa mesa, Pedido pedido) throws SQLException {
        daoPedido.insertarMesaPedido(mesa.getId(), pedido.getId());
    }

    // Método para obtener los items de un pedido por su ID
    public List<Item> obtenerItemsPorPedido(int pedidoId) throws SQLException {
        return daoPedido.obtenerItemsPorPedido(pedidoId);
    }

    // Método para insertar un item en un pedido
    public void insertarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.insertarItem(pedido, item);
    }

    // Método para eliminar un pedido por ID y sus detalles asociados
    public void eliminarPedido(int pedidoId) throws SQLException {
        daoPedido.eliminarDetallesPedido(pedidoId);
        daoPedido.eliminarPedido(pedidoId);
    }

    // Método para eliminar un pedido asociado a una mesa
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        daoPedido.eliminarMesaPedido(mesa.getId(), pedido.getId());
        eliminarPedido(pedido.getId());
    }
}
