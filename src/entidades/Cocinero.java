package entidades;

import estructurasdatos.List;

/**
 *
 * @author Miguel Brito
 */
public class Cocinero extends Usuario {

    private List<Pedido> pedidos;

    public Cocinero(String nombre, String apellido, String correo, String clave) {
        super(nombre, apellido, correo, clave);
        this.pedidos = new List<>();
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    public int calcularTiempoPedidos() throws Exception {
        int total = 0;
        for (Pedido p : this.pedidos) {
            total += p.calcularTiempoPreparacion();
        }
        return total;
    }

    public void agregarPedido(Pedido pedido) {
        pedido.setEstado(Pedido.Estado.PENDIENTE);
        this.pedidos.addEnd(pedido);
    }

    public void procesarPedido() throws Exception {
        if (this.pedidos.isEmpty()) {
            throw new Exception("Pedidos vacios");
        }
        this.pedidos.start().setEstado(Pedido.Estado.EN_PROGRESO);
    }

    public Pedido completarPedido() throws Exception {
        if (this.pedidos.isEmpty()) {
            throw new Exception("Pedidos vacios");
        }
        Pedido pedido = pedidos.removeStart();
        pedido.setEstado(Pedido.Estado.FINALIZADO);
        return pedido;
    }

    public Pedido getPedidoActual() throws Exception {
        if (pedidos.isEmpty()) {
            throw new Exception("No existen pedidos en la cola.");
        }
        return this.pedidos.start();
    }

    //No se cuenta a la orden que está siendo atendida
    public int getCantPendientes() {
        int cont = 0;
        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == Pedido.Estado.PENDIENTE) {
                cont++;
            }
        }
        return cont;
    }

    public int getCantEnCola(Cliente cliente) {
        int cont = 0;
        for (Pedido pedido : pedidos) {
            if (cliente == pedido.getCliente()) {
                Pedido.Estado estado = pedido.getEstado();
                if (estado == Pedido.Estado.PENDIENTE || estado == Pedido.Estado.EN_PROGRESO) {
                    cont++;
                }
            }
        }
        return cont;
    }
}
