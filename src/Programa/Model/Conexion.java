package Programa.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";
    private static final String BASE_DE_DATOS = "titorestobar";

    public static Connection Conectar() throws SQLException {
        String cadenaConexion = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BASE_DE_DATOS;
        return DriverManager.getConnection(cadenaConexion, USUARIO, PASSWORD);
    }
}
