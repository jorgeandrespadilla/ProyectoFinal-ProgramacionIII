package interfaz.cliente;

import entidades.Cliente;
import entidades.ItemPedido;
import entidades.Pedido;
import estructurasdatos.List;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author Alain
 */
public class ResumenPedidoController extends Controller {

    private Cliente clienteActual;
    private List<ItemPedido> platillosSeleccionados;

    @FXML
    private TextArea area;
    @FXML
    Button confirmar;
    @FXML
    private TextArea txtAreaObservaciones;
    @FXML
    private Label lblIVA;
    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblTotal;

    public void cargarPedido() {
        try {
            for (int cont = 0; cont < platillosSeleccionados.size(); cont++) {
                area.setText(area.getText() + "\n" + platillosSeleccionados.get(cont).getPlatillo() + "Cantidad: " + platillosSeleccionados.get(cont).getCantidad() + "\n\n");
            }
            double subtotal = singleton.calcularSubtotal(platillosSeleccionados);
            double IVA = subtotal * 0.12;
            lblSubtotal.setText(singleton.formatPrecio(subtotal));
            lblIVA.setText(singleton.formatPrecio(IVA));
            lblTotal.setText(singleton.formatPrecio(IVA + subtotal));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void volver(ActionEvent a) {
        stackManager.goBack();
    }

    @FXML
    public void confirmarPedido(ActionEvent a) throws Exception {
        Pedido pedido = new Pedido(clienteActual, platillosSeleccionados, txtAreaObservaciones.getText());//Cambiar por cliente
        ConfirmacionEnvioController controlador = stackManager.loadOverlap("cliente/ConfirmacionEnvio");
        if (singleton.agregarPedido(pedido)) {
            controlador.initData("Se envió exitosamente su pedido.", true);
        }
        else {
            controlador.initData("No se pudo enviar su pedido.", false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblSubtotal.setText("");
        lblIVA.setText("");
        lblTotal.setText("");
    }

    public void initData(Cliente clienteActual, List<ItemPedido> platillosSeleccionados) {
        this.clienteActual = clienteActual;
        this.platillosSeleccionados = platillosSeleccionados;
        cargarPedido();
    }
}
