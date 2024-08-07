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

    // Método para obtener todos los pedidos activos
    public List<Pedido> obtenerTodosLosPedidosActivos() throws SQLException {
        return daoPedido.obtenerTodosLosPedidosActivos();
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
    public void eliminarPedido(Pedido pedido) throws SQLException {
        daoPedido.eliminarPedido(pedido);
    }
    
    //Método para eliminar items
    public void eliminarItemsPorPedido(int pedidoId) throws SQLException {
        daoPedido.eliminarItemsPorPedido(pedidoId);
    }
    
    //Método para devolver un producto
    public void devolverProductosAlStock(int pedidoId) throws SQLException{
        daoPedido.devolverProductosAlStock(pedidoId);
    }

    // Método para obtener una mesa por su ID
    public Mesa obtenerMesaPorId(int idMesa) throws SQLException {
        return daoPedido.obtenerMesaPorId(idMesa);
    }
    public Pedido verPedidoActivoDeMesa(Mesa mesa) throws SQLException{
        return daoPedido.verPedidoActivoDeMesa(mesa);
    }
    //Método para cerrar un pedido
    public void cerrarPedido(Pedido pedido) throws SQLException{
        daoPedido.cerrarPedido(pedido);
    }
}