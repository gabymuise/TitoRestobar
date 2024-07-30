package Programa.Controller;

import Programa.DAO.DAOMesa;
import Programa.Model.Mesa;
import Programa.Model.Pedido;

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

    public boolean existeMesa(String nombre) throws SQLException {
        return daoMesa.obtenerMesaPorNombre(nombre) != null;
    }

    public void crearMesa(Mesa mesa) throws SQLException {
        if (existeMesa(mesa.getNombre())) {
            throw new SQLException("La mesa ya existe.");
        }
        daoMesa.crearMesa(mesa);
    }

    // Método para eliminar una mesa por su ID
    public void eliminarMesa(int id) throws SQLException {
        // Asegurarse de que no hay pedidos activos en la mesa antes de eliminarla
        if (daoMesa.tienePedidosActivos(id)) {
            throw new SQLException("No se puede eliminar la mesa porque tiene pedidos activos.");
        }
        daoMesa.eliminarMesa(id);
    }
    
    public boolean tienePedidosActivos(Mesa mesa) throws SQLException {
        return daoMesa.tienePedidosActivos(mesa.getId());
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
    
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        // Primero, elimina todos los items asociados al pedido
        new ControladoraPedido().eliminarItemsPorPedido(pedido.getId());

        // Luego, elimina el pedido
        new ControladoraPedido().eliminarPedido(pedido);
    }

    
    public Pedido obtenerPedidoActivo(int idMesa) throws SQLException{
       return daoMesa.obtenerPedidoActivo(idMesa);
    }
}
