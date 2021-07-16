package infraestructura;

import entidades.ItemPedido;
import entidades.*;
import estructurasdatos.List;
import utilities.FilterTools;

public class Singleton {

    private static Singleton instance;

    private final Menu menu;
    private Usuario administrador;
    private final List<Cliente> clientes;
    private final List<Cocinero> cocineros;
    private final List<Pedido> historialPedidos;

    private Singleton() {
        this.menu = new Menu();
        for (Platillo platillo : Constantes.crearPlatillos()) {
            this.menu.agregar(platillo);
        }
        this.clientes = Constantes.crearClientes();
        this.cocineros = Constantes.crearCocineros();
        this.historialPedidos = new List<>();
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public Menu getMenu() {
        return menu;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Cocinero> getCocineros() {
        return cocineros;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    // SERVICIOS
    public void agregarUsuario(Cliente cliente) {
        this.clientes.addEnd(cliente);
    }

    public void agregarPedido(Pedido pedido) throws Exception {
        Cocinero cocinero = null;
        for (Cocinero c : this.cocineros) {
            if (cocinero == null) {
                cocinero = c;
            } else if (c.calcularTiempoPedidos() < cocinero.calcularTiempoPedidos()) {
                cocinero = c;
            }
        }
        cocinero.agregarPedido(pedido);
    }

    public Cocinero buscarCocinero(String correo) {
        for (Cocinero cocinero : cocineros) {
            if (FilterTools.normalizeEmail(correo).compareTo(cocinero.getCorreo()) == 0) {
                return cocinero;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String correo) {
        for (Cliente cliente : clientes) {
            if (FilterTools.normalizeEmail(correo).compareTo(cliente.getCorreo()) == 0) {
                return cliente;
            }
        }
        return null;
    }

    public List<Pedido> obtenerHistorialCliente(Cliente cliente) {
        List<Pedido> pedidos = new List<>();
        for (Pedido pedido : historialPedidos) {
            if (cliente == pedido.getCliente()) {
                pedidos.addEnd(pedido);
            }
        }
        return pedidos;
    }
    
    public int obtenerColaCliente(Cliente cliente) {
        int total = 0;
        for (Cocinero cocinero : cocineros) {
            total += cocinero.getCantEnCola(cliente);
        }
        return total;
    }

    public void eliminarCuenta(Cliente clienteBuscado) {
        try {
            int cont = 0;
            for (Cliente cliente : clientes) {
                if (cliente == clienteBuscado) {
                    clientes.remove(cont);
                }
                cont++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarEmpleado(String correo) throws Exception {
        for (int i = 0; i < cocineros.size(); i++) {
            if (cocineros.get(i).getCorreo().equals(correo)) {
                cocineros.remove(i);
            }
        }
    }

    public double calcularSubtotal(List<ItemPedido> platillos) {
        double subTotal = 0;
        for (ItemPedido platillo : platillos) {
            subTotal += platillo.getCantidad() * platillo.getPlatillo().getPrecio();
        }
        return subTotal;
    }
    
    public String formatPrecio(double precio) {
        return String.format("$%.2f", precio);
    }

    public List<Platillo> ordenarMenuABCAscendente() throws Exception {
        List<Platillo> aux = new List<>();
        for (Platillo platillo : menu.getListaPlatillos()) {
            aux.addEnd(platillo);
        }
        for (int i = 0; i < aux.size(); i++) {
            for (int j = i + 1; j < aux.size(); j++) {
                if (aux.get(i).getNombre().compareTo(aux.get(j).getNombre()) > 0) {
                    Platillo temp = aux.get(i);
                    aux.set(i, aux.get(j));
                    aux.set(j, temp);
                }
            }
        }
        return aux;
    }

    public List<Platillo> ordenarMenuABCDescendente() throws Exception {
        List<Platillo> aux = new List<>();
        for (Platillo platillo : menu.getListaPlatillos()) {
            aux.addEnd(platillo);
        }
        for (int i = 0; i < aux.size(); i++) {
            for (int j = i + 1; j < aux.size(); j++) {
                if (aux.get(i).getNombre().compareTo(aux.get(j).getNombre()) < 0) {
                    Platillo temp = aux.get(i);
                    aux.set(i, aux.get(j));
                    aux.set(j, temp);
                }
            }
        }
        return aux;
    }
}
