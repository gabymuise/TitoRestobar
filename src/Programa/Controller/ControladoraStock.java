package Programa.Controller;

import Programa.DAO.DAOStock;
import Programa.Model.Stock;
import java.sql.SQLException;

public class ControladoraStock {
    private DAOStock daoStock;

    // Constructor que inicializa la instancia de DAOStock
    public ControladoraStock() {
        daoStock = new DAOStock();
    }

    // Método para disminuir la cantidad de stock de un producto
    public void disminuirStock(int idProducto, int cantidad) throws SQLException {
        Stock stock = daoStock.obtenerStockPorProducto(idProducto);
        if (stock != null && stock.getCantidad() >= cantidad) {
            stock.setCantidad(stock.getCantidad() - cantidad);
            daoStock.actualizarStock(stock);
        } else {
            throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + idProducto);
        }
    }

    // Método para aumentar la cantidad de stock de un producto
    public void aumentarStock(int idProducto, int cantidad) throws SQLException {
        Stock stock = daoStock.obtenerStockPorProducto(idProducto);
        if (stock != null) {
            stock.setCantidad(stock.getCantidad() + cantidad);
            daoStock.actualizarStock(stock);
        } else {
            throw new IllegalArgumentException("Producto no encontrado en el stock con ID: " + idProducto);
        }
    }

    // Método para actualizar la información de stock de un producto
    public void actualizarStock(Stock stock) throws SQLException {
        daoStock.actualizarStock(stock);
    }

    // Método para eliminar el stock de un producto
    public void eliminarStock(int productoId) throws SQLException {
        daoStock.eliminarStock(productoId);
    }
}
