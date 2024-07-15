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

    public Mesa CrearMesa(String nombre) {
        try {
            Mesa nuevaMesa = new Mesa(nombre);
            daoMesa.crearMesa(nuevaMesa);
            mesas.add(nuevaMesa);
            return nuevaMesa;
        } catch (SQLException e) {
            System.err.println("Error al crear la mesa: " + e.getMessage());
            return null;
        }
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

    public void EliminarMesa(String nombre) {
        try {
            daoMesa.eliminarMesa(nombre);
            mesas.removeIf(m -> m.getNombre().equals(nombre));
        } catch (SQLException e) {
            System.err.println("Error al eliminar la mesa: " + e.getMessage());
        }
    }

    public void MostrarTodasLasMesas() {
        System.out.println("Mesas disponibles:");
        for (Mesa mesa : mesas) {
            System.out.println("Nombre: " + mesa.getNombre());
        }
    }

    public void AgregarItemEnPedido(Pedido pedido, Item item) {
        pedido.AgregarItem(item);
    }

    public void EliminarItemEnPedido(Pedido pedido, Item item) {
        pedido.EliminarItem(item);
    }
}
