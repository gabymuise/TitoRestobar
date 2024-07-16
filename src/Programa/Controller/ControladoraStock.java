package Programa.Controller;

import Programa.DAO.DAOStock;
import Programa.Stock;
import java.sql.SQLException;

public class ControladoraStock {
    private DAOStock daoStock;

    public ControladoraStock() {
        daoStock = new DAOStock();
    }

    public void aumentarStock(int productoId, int cantidad) throws SQLException {
        daoStock.aumentarStock(productoId, cantidad);
    }

    public void disminuirStock(int productoId, int cantidad) throws SQLException {
        daoStock.disminuirStock(productoId, cantidad);
    }

    public void actualizarStock(int productoId, int nuevaCantidad) throws SQLException {
        daoStock.actualizarStock(productoId, nuevaCantidad);
    }

    public void eliminarStock(int productoId) throws SQLException {
        daoStock.eliminarStock(productoId);
    }

    public void cerrarConexion() {
        daoStock.cerrarConexion();
    }
}
