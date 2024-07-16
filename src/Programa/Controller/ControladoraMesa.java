package Programa.Controller;

import Programa.DAO.DAOMesa;
import Programa.Mesa;
import Programa.Pedido;
import java.sql.SQLException;
import java.util.List;

public class ControladoraMesa {
    private DAOMesa daoMesa;
    private Pedido pedido;

    public ControladoraMesa() {
        daoMesa = new DAOMesa();
    }

    public List<Mesa> listarMesas() throws SQLException {
        return daoMesa.listarMesas();
    }

    public void crearMesa(Mesa mesa) throws SQLException {
        daoMesa.crearMesa(mesa);
    }

    public void eliminarMesa(String nombre) throws SQLException {
        daoMesa.eliminarMesa(nombre);
    }
    
     public Pedido verPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        return daoMesa.verPedidoActivoEnMesa(mesa);
    }
     
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        daoMesa.eliminarPedidoDeMesa(mesa, pedido);
    }

    public void cerrarConexion() {
        daoMesa.cerrarConexion();
    }
}
