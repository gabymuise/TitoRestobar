package Programa.Controller;

import Programa.DAO.DAOProducto;
import Programa.Producto;
import java.sql.SQLException;
import java.util.List;

public class ControladoraProducto {
    private DAOProducto daoProducto;

    public ControladoraProducto() {
        daoProducto = new DAOProducto();
    }

    public List<Producto> listadoDeProductos() throws SQLException {
        return daoProducto.listadoDeProductos();
    }

    public void Guardar(Producto producto) throws SQLException {
        daoProducto.Guardar(producto);
    }

    public void Actualizar(Producto producto) throws SQLException {
        daoProducto.UpDate(producto);
    }

    public boolean EliminarProductoPorNombre(String nombre) throws SQLException {
        return daoProducto.EliminarProductoPorNombre(nombre);
    }

    public void Ver(Producto producto) throws SQLException {
        daoProducto.Ver(producto);
    }

    public Producto CrearProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
