package Programa.Controller;

import Programa.DAO.DAOProducto;
import Programa.Producto;
import java.sql.SQLException;

public class ControladoraProducto {
    public Producto CrearProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) throws SQLException {
        Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
        DAOProducto daoProducto = new DAOProducto(); 
        daoProducto.Guardar(producto);

        return producto;
    }
      
    public Producto EliminarProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) throws SQLException {
        Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
        DAOProducto daoProducto = new DAOProducto(); 
        daoProducto.EliminarProductoPorNombre(nombre);
        return producto;
    }
    public boolean EliminarProductoPorNombre(String nombre) {
    
        DAOProducto daoProducto = new DAOProducto();
        try {
            return daoProducto.EliminarProductoPorNombre(nombre);
        } catch (SQLException e) {
            return false; 
        }
    }
    public Producto VerProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) throws SQLException {
        Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
        DAOProducto daoProducto = new DAOProducto(); 
        daoProducto.Ver(producto);
        return producto;
    }
    public Producto ActualizarProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) throws SQLException{
       Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
        DAOProducto daoProducto = new DAOProducto(); 
        daoProducto.UpDate(producto);
        return producto; 
    }
}

