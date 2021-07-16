package entidades;

import estructurasdatos.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jorge Padilla
 */
public class Pedido {

    public enum Estado {
        PENDIENTE, EN_PROGRESO, FINALIZADO
    }

    private static int acumuladorNum = 1;

    private int numPedido;
    private Cliente cliente;
    private Estado estado;
    private LocalDateTime fecha;
    private List<ItemPedido> platillos;
    private String observacion;

    public Pedido(Cliente cliente, List<ItemPedido> platillos, String observacion) {
        this.numPedido = acumuladorNum;
        acumuladorNum++;
        this.cliente = cliente;
        this.estado = Estado.PENDIENTE;
        this.fecha = LocalDateTime.now();
        this.platillos = platillos;
        this.observacion = observacion;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getPlatillos() {
        return platillos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void agregarPlatillo(Platillo platillo, int cantidad) {
        this.platillos.addEnd(new ItemPedido(platillo, cantidad));
    }

    public void eliminarPlatillo(ItemPedido platillo) throws Exception {
        int aux = this.platillos.indexOf(platillo);
        this.platillos.remove(aux);
    }

    public int calcularTiempoPreparacion() throws Exception {
        int tiempo = 0;
        for (int i = 0; i < this.platillos.size(); i++) {
            Platillo platillo = this.platillos.get(i).getPlatillo();
            tiempo += platillo.getTiempoPreparacion();
        }
        return tiempo;
    }

    public double subtotal() {
        double subTotal = 0;
        for (ItemPedido platillo : platillos) {
            subTotal += platillo.getCantidad() * platillo.getPlatillo().getPrecio();
        }
        return subTotal;
    }

    public double IVA() {
        return subtotal() * 0.12;
    }

    public double total() {
        return subtotal() + IVA();
    }

    public String formatNumPedido() {
        return String.format("%05d", this.numPedido);
    }

    public String formatFecha() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fecha.format(format);
    }
}
