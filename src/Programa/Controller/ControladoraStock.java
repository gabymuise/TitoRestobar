package Programa.Controller;

import Programa.DAO.DAOStock;
import Programa.Model.Stock;
import Programa.Model.Producto;

import java.sql.SQLException;

public class ControladoraStock {
    private DAOStock daoStock;

    // Constructor que inicializa la instancia de DAOStock
    public ControladoraStock() {
        daoStock = new DAOStock();
    }

    // Método para obtener el stock por ID de producto
    public Stock obtenerStockPorProducto(int idProducto) throws SQLException {
        return daoStock.obtenerStockPorProducto(idProducto);
    }

    // Método para actualizar la cantidad de stock de un producto
    public void actualizarStock(Stock stock) throws SQLException {
        daoStock.actualizarStock(stock);
    }

    // Método para eliminar el stock de un producto
    public void eliminarStock(int idProducto) throws SQLException {
        daoStock.eliminarStock(idProducto);
    }
}
