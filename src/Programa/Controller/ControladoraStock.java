package Programa.Controller;

import Programa.DAO.DAOStock;
import Programa.Model.Stock;
import java.sql.SQLException;

public class ControladoraStock {
    private DAOStock daoStock;

    public ControladoraStock() {
        daoStock = new DAOStock();
    }

    public void disminuirStock(int idProducto, int cantidad) {
        Stock stock = daoStock.obtenerStockPorProducto(idProducto);
        if (stock != null && stock.getCantidad() >= cantidad) {
            stock.setCantidad(stock.getCantidad() - cantidad);
            daoStock.actualizarStock(stock);
        } else {
            throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + idProducto);
        }
    }

    public void aumentarStock(int idProducto, int cantidad) {
        Stock stock = daoStock.obtenerStockPorProducto(idProducto);
        if (stock != null) {
            stock.setCantidad(stock.getCantidad() + cantidad);
            daoStock.actualizarStock(stock);
        } else {
            throw new IllegalArgumentException("Producto no encontrado en el stock con ID: " + idProducto);
        }
    }

    public void actualizarStock(Stock stock) throws SQLException {
        daoStock.actualizarStock(stock);
    }

    public void eliminarStock(int productoId) throws SQLException {
        daoStock.eliminarStock(productoId);
    }
}
