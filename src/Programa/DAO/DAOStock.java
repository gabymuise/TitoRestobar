package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Producto;
import Programa.Model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOStock {
    // Obtiene el stock de un producto específico
    public Stock obtenerStockPorProducto(int idProducto) {
        String sql = "SELECT * FROM stock WHERE id_producto = ?";
        Stock stock = null;
        
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stock = new Stock(); // Inicializar el objeto Stock
                    stock.setId(rs.getInt("id"));
                    stock.setCantidad(rs.getInt("cantidad"));
                    
                    Producto producto = new Producto(); // Inicializar el objeto Producto
                    producto.setId(rs.getInt("id_producto"));
                    stock.setProducto(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
        return stock;
    }

    // Actualiza la cantidad de stock para un producto
    public void actualizarStock(Stock stock) {
        String sql = "UPDATE stock SET cantidad = ? WHERE id_producto = ?";
        
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stock.getCantidad());
            ps.setInt(2, stock.getProducto().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }

    // Elimina el stock de un producto
    public void eliminarStock(int idProducto) {
        String sql = "DELETE FROM stock WHERE id_producto = ?";
        
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }
}
