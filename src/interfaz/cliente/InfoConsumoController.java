package interfaz.cliente;

import entidades.Cliente;
import entidades.ItemPedido;
import entidades.Pedido;
import estructurasdatos.List;
import interfaz.Controller;
import interfaz.StackManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author Alain
 */
public class InfoConsumoController extends Controller implements Initializable {

    private Pedido pedido;

    private Cliente clienteActual;
    private List<ItemPedido> platillosSeleccionados;

    @FXML
    private TextArea area;
    @FXML
    private TextArea txtAreaObservaciones;
    @FXML
    private Label lblIVA;
    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblFactura;
    @FXML
    private Label lblFecha;

    public void cargarPedido() {
        try {
            lblFactura.setText("Orden N°" + pedido.formatNumPedido());
            lblFecha.setText(pedido.formatFecha());
            for (int cont = 0; cont < pedido.getPlatillos().size(); cont++) {
                area.setText(area.getText() + "\n" + pedido.getPlatillos().get(cont).getPlatillo() + "Cantidad: " + pedido.getPlatillos().get(cont).getCantidad() + "\n\n");
            }
            lblSubtotal.setText(singleton.formatPrecio(pedido.subtotal()));
            lblIVA.setText(singleton.formatPrecio(pedido.IVA()));
            lblTotal.setText(singleton.formatPrecio(pedido.IVA() + pedido.subtotal()));
            txtAreaObservaciones.setText(pedido.getObservacion());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void volver(ActionEvent a) {
        stackManager.goBack();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(Pedido pedido, StackManager parent) {
        this.pedido = pedido;
        this.stackManager = parent;
        cargarPedido();
    }
}
