package Programa.Controller;

import Programa.Item;
import Programa.Mesa;
import Programa.Pedido;
import Programa.DAO.DAOMesa;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladoraMesa {
    private List<Mesa> mesas;
    private DAOMesa daoMesa;

    public ControladoraMesa() {
        this.mesas = new ArrayList<>();
        this.daoMesa = new DAOMesa();
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    public Mesa CrearMesa(String nombre) throws SQLException {
        Mesa nuevaMesa = new Mesa(nombre);
        daoMesa.crearMesa(nuevaMesa);
        return nuevaMesa;
    }

    public Mesa BuscarMesaPorNombre(String nombre) {
        for (Mesa mesa : mesas) {
            if (mesa.getNombre().equals(nombre)) {
                return mesa;
            }
        }
        System.out.println("Mesa no encontrada.");
        return null;
    }

    public void EliminarMesa(String nombre) throws SQLException {
        daoMesa.eliminarMesa(nombre);
    }


    public void MostrarTodasLasMesas() {
        System.out.println("Mesas disponibles:");
        for (Mesa mesa : mesas) {
            System.out.println("Nombre: " + mesa.getNombre());
        }
    }

    /*public Pedido CrearPedidoEnMesa(Mesa mesa) {
        Pedido nuevoPedido = new Pedido();
        mesa.AbrirPedido(nuevoPedido);
        return nuevoPedido;
    }

    public void CerrarPedidoEnMesa(Mesa mesa) {
        Pedido pedido = mesa.VerPedido();
        if (pedido != null) {
            mesa.CerrarPedido();
        } else {
            System.out.println("No hay pedido en la mesa " + mesa.getNombre());
        }
    }*/

    public void AgregarItemEnPedido(Pedido pedido, Item item) {
        // Lógica para agregar un Item a un Pedido
        pedido.AgregarItem(item);
    }

    public void EliminarItemEnPedido(Pedido pedido, Item item) {
        // Lógica para eliminar un Item de un Pedido
        pedido.EliminarItem(item);
    }

    public void MostrarPedidoEnMesa(Mesa mesa) {
        Pedido pedido = mesa.VerPedido();
        if (pedido != null) {
            System.out.println("Pedido en mesa " + mesa.getNombre() + ":");
            System.out.println("Fecha y hora de apertura: " + pedido.getFechaHoraApertura());
            System.out.println("Total del pedido: " + pedido.TotalPedido());
            System.out.println("Items del pedido:");
            for (Item item : pedido.getItems()) {
                System.out.println("  - " + item.getProducto() + " - Cantidad: " + item.getCantidad());
            }
        } else {
            System.out.println("No hay pedido en la mesa " + mesa.getNombre());
        }
    }
}
