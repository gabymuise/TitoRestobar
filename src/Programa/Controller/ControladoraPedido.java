package Programa.Controller;

import Programa.DAO.DAOPedido;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Descuento;
import java.sql.SQLException;
import java.util.List;

public class ControladoraPedido {
    private DAOPedido daoPedido;

    // Constructor que inicializa la instancia de DAOPedido
    public ControladoraPedido() throws SQLException {
        daoPedido = new DAOPedido();
    }

    // Método para crear un nuevo pedido
    public boolean crearPedido(Pedido pedido, int mesaId, List<Item> items) throws SQLException {
        boolean pedidoCreado = daoPedido.crearPedido(pedido);
        boolean mesaAsignada = daoPedido.asignarMesaAPedido(pedido.getId(), mesaId);

        if (pedidoCreado && mesaAsignada) {
            for (Item item : items) {
                boolean itemAgregado = daoPedido.agregarItemAPedido(pedido.getId(), item.getProducto().getId(), item.getCantidad());
                if (!itemAgregado) {
                    return false; // Si algún item no se agrega, retornar falso
                }
            }
            return true; // Si todos los items se agregan correctamente, retornar verdadero
        }
        return false; // Si el pedido o la mesa no se pueden asignar, retornar falso
    }

    // Método para agregar un item a un pedido existente
    public boolean agregarItemAPedido(int pedidoId, int productoId, int cantidad) throws SQLException {
        return daoPedido.agregarItemAPedido(pedidoId, productoId, cantidad);
    }

    // Método para eliminar un item de un pedido existente
    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        daoPedido.eliminarItem(pedido, item);
    }

    // Método para listar todos los pedidos de una mesa específica
    public Pedido verificarPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        return daoPedido.obtenerPedidoActivoEnMesa(mesa);
    }
    
    /*//Metodo para obtener el pedido activo de una mesa especifica
    public void obtenerPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        daoPedido.obtenerPedidoActivoEnMesa(mesa);
    }*/
    

    // Método para calcular el total de un pedido, aplicando un descuento si es necesario
    public float getTotal(Pedido pedido, Descuento descuento) throws SQLException {
        float total = 0;
        List<Item> items = pedido.getItems();
        for (Item item : items) {
            total += item.getSubtotal();
        }
        return descuento.aplicarDescuento(total);
    }
}
