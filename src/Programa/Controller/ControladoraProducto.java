package Programa.Controller;

import Programa.DAO.DAOProducto;
import Programa.Producto;
import java.sql.SQLException;

public class ControladoraProducto {
    private DAOProducto daoProducto;

    public ControladoraProducto() {
        this.daoProducto = new DAOProducto();
    }

    public Producto CrearProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        try {
            Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
            daoProducto.Guardar(producto);
            return producto;
        } catch (SQLException e) {
            System.err.println("Error al crear el producto: " + e.getMessage());
            return null;
        }
    }

    public boolean EliminarProductoPorNombre(String nombre) {
        try {
            return daoProducto.EliminarProductoPorNombre(nombre);
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    public Producto VerProducto(String nombre) {
        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            daoProducto.Ver(producto);
            return producto;
        } catch (SQLException e) {
            System.err.println("Error al ver el producto: " + e.getMessage());
            return null;
        }
    }

    public Producto ActualizarProducto(String nombre, String descripcion, float precio, float costo, boolean elaboracion) {
        try {
            Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
            daoProducto.Actualizar(producto);
            return producto;
        } 
        catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            return null;
        }
    }
}
