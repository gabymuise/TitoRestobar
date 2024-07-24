package Programa.Controller;

import Programa.DAO.DAOMesa;
import Programa.DAO.DAOPedido;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import java.sql.SQLException;
import java.util.List;

public class ControladoraMesa {
    private DAOMesa daoMesa;

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

    // Método para eliminar una mesa por nombre
    public void eliminarMesa(String nombre) throws SQLException {
        daoMesa.eliminarMesa(nombre);
    }
    
    // Método para ver el pedido activo en una mesa específica
    public Pedido verPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        return daoMesa.verPedidoActivoEnMesa(mesa);
    }
     
    // Método para eliminar un pedido de una mesa
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        daoMesa.eliminarPedidoDeMesa(mesa, pedido);
    }
    
    //Metodo para modificar el nombre de una mesa
    public void modificarMesa(String nombreActual, String nuevoNombre) throws SQLException {
        daoMesa.modificarMesa(nombreActual, nuevoNombre);
    }
    
    // Método para obtener una mesa por su nombre
    public Mesa obtenerMesaPorNombre(String nombre) throws SQLException {
        return daoMesa.obtenerMesaPorNombre(nombre);
    }
    
    public Pedido obtenerPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        DAOPedido daoPedido = new DAOPedido();
        return daoPedido.obtenerPedidoActivoEnMesa(mesa);
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        daoMesa.cerrarConexion();
    }
}
