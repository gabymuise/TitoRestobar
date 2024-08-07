package Programa.Controller;

import Programa.DAO.DAOMesa;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    // Método para decir que la mesa existe
    public boolean existeMesa(String nombre) throws SQLException {
        return daoMesa.obtenerMesaPorNombre(nombre) != null;
    }
    
     // Método para crear mesa
    public void crearMesa(Mesa mesa) throws SQLException {
        if (existeMesa(mesa.getNombre())) {
            throw new SQLException("La mesa ya existe.");
        }
        daoMesa.crearMesa(mesa);
    }

    // Método para eliminar una mesa por su ID
    public void eliminarMesa(int id) throws SQLException {
        if (daoMesa.tienePedidosActivos(id)) {
            throw new SQLException("No se puede eliminar la mesa porque tiene pedidos activos.");
        }
        daoMesa.eliminarMesa(id);
    }
    
    // Método para ver si tiene pedido activo
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
    
     // Método para devolver producto al stock, eliminar item y eliminar pedido
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        ControladoraPedido controladoraPedido = new ControladoraPedido();

        try {

            controladoraPedido.devolverProductosAlStock(pedido.getId());

            controladoraPedido.eliminarItemsPorPedido(pedido.getId());

            controladoraPedido.eliminarPedido(pedido);
        } catch (SQLException e) {
            Logger.getLogger(ControladoraMesa.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

     // Método para obtener pedido activo
    public Pedido obtenerPedidoActivo(int idMesa) throws SQLException{
       return daoMesa.obtenerPedidoActivo(idMesa);
    }
}
