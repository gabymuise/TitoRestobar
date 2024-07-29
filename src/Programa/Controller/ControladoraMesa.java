package Programa.Controller;

import Programa.DAO.DAOMesa;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Item;
import Resources.PedidoNoActivoException;

import java.sql.SQLException;
import java.util.List;

public class ControladoraMesa {
    private final DAOMesa daoMesa;

    // Constructor que inicializa la instancia de DAOMesa
    public ControladoraMesa() {
        daoMesa = new DAOMesa();
    }

    // Método para listar todas las mesas
    public List<Mesa> listarMesas() throws SQLException {
        return daoMesa.listarMesas();
    }

    // Método para crear una nueva mesa
    public void crearMesa(Mesa mesa) throws SQLException {
        daoMesa.crearMesa(mesa);
    }

    // Método para eliminar una mesa por su ID
    public void eliminarMesa(int id) throws SQLException {
        // Asegurarse de que no hay pedidos activos en la mesa antes de eliminarla
        if (daoMesa.hasActiveOrders(id)) {
            throw new SQLException("No se puede eliminar la mesa porque tiene pedidos activos.");
        }
        daoMesa.eliminarMesa(id);
    }
    
    public boolean tienePedidosActivos(Mesa mesa) throws SQLException {
        return daoMesa.hasActiveOrders(mesa.getId());
    }
    

    // Método para modificar el nombre de una mesa
    public void modificarMesa(int id, String nuevoNombre) throws SQLException {
        daoMesa.modificarMesa(id, nuevoNombre);
    }

    // Método para obtener una mesa por su nombre
    public Mesa obtenerMesaPorNombre(String nombre) throws SQLException {
        return daoMesa.obtenerMesaPorNombre(nombre);
    }

    // Método para obtener una mesa por su ID
    public Mesa obtenerMesaPorId(int id) throws SQLException {
        return daoMesa.obtenerMesaPorId(id);
    }

    // Método para obtener el pedido activo en una mesa
    public Pedido obtenerPedidoActivoEnMesa(Mesa mesa) throws SQLException, PedidoNoActivoException {
        Pedido pedido = daoMesa.verPedidoActivoEnMesa(mesa);
        if (pedido == null) {
            throw new PedidoNoActivoException("No hay un pedido activo en esta mesa.");
        }
        return pedido;
    }

    // Método para eliminar un pedido de una mesa
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        // Primero eliminamos el pedido de la mesa
        daoMesa.eliminarPedidoDeMesa(mesa, pedido);
        // Luego eliminamos el pedido en sí mismo
        new ControladoraPedido().eliminarPedido(pedido.getId());
    }
}
