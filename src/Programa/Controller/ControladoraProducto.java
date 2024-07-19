package Programa.Controller;

import Programa.DAO.DAOProducto;
import Programa.Model.Producto;
import Programa.Model.Stock;
import java.sql.SQLException;
import java.util.List;

public class ControladoraProducto {
    private DAOProducto daoProducto;

    // Constructor que inicializa la instancia de DAOProducto
    public ControladoraProducto() {
        daoProducto = new DAOProducto();
    }

    // Método para obtener una lista de productos
    public List<Producto> listadoDeProductos() throws SQLException {
        return daoProducto.listadoDeProductos();
    }

    // Método para guardar un nuevo producto en la base de datos
    public void guardar(Producto producto) throws SQLException {
        daoProducto.guardar(producto);
    }

    // Método para actualizar un producto existente en la base de datos
    public void actualizar(Producto producto) throws SQLException {
        daoProducto.actualizar(producto);
    }

    // Método para eliminar un producto por su nombre
    public boolean eliminarProductoPorNombre(String nombre) throws SQLException {
        return daoProducto.eliminarProductoPorNombre(nombre);
    }

    // Método para obtener detalles de un producto específico
    public void ver(int productoId) throws SQLException {
        daoProducto.ver(productoId);
    }

    // Método para guardar la información de stock de un producto
    public void guardarStock(Stock stock) throws SQLException {
        daoProducto.guardarStock(stock);
    }
}
