package Programa.DAO;

import Programa.Mesa;
import Programa.Pedido;
import Programa.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPedido {
    private final Connection conexion; 

    public DAOPedido(Connection conexion) {
        this.conexion = conexion;
    }

    public void crearPedido(Pedido pedido) throws SQLException {
        String consulta = "INSERT INTO pedidos (mesa_id, fecha_hora_apertura) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setTimestamp(2, new java.sql.Timestamp(pedido.getFechaHoraApertura().getTime()));
            ps.executeUpdate();
        }
    }

    public void agregarItem(Pedido pedido, Item item) throws SQLException {
        // Implementa la lógica para agregar un elemento de pedido a la base de datos
    }

    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        // Implementa la lógica para eliminar un elemento de pedido de la base de datos
    }

    /*public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        
        String consulta = "SELECT * FROM pedidos WHERE mesa_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int pedidoId = rs.getInt("id");
                    java.util.Date fechaHoraApertura = new java.util.Date(rs.getTimestamp("fecha_hora_apertura").getTime());
                    Pedido pedido = new Pedido(fechaHoraApertura, items);
                    // Agrega el pedido a la lista de pedidos
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }*/
}

