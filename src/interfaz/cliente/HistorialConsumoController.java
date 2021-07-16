package interfaz.cliente;

import entidades.Cliente;
import entidades.Pedido;
import estructurasdatos.List;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class HistorialConsumoController extends Controller implements Initializable {

    private Cliente clienteActual;
    private List<ItemHistorialController> controladoresPlatos;

    @FXML
    private GridPane items;
    @FXML
    private Label lblPedidosCola;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        stackManager.goBack();
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        mostrarInfo();
    }

    private void cargarHistorial(List<Pedido> pedidos) throws Exception {
        controladoresPlatos = new List<>();
        int fila = 0;
        RowConstraints filMax;
        for (Pedido pedido : pedidos) {
            filMax = new RowConstraints(70);
            items.getRowConstraints().add(filMax);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemHistorial.fxml"));
            Parent root = (Parent) loader.load();
            ItemHistorialController controlador = loader.getController();
            boolean isLastItem = (fila == pedidos.size() - 1);
            controlador.inicializar(pedido, isLastItem, stackManager);
            items.add(root, 0, fila);
            controladoresPlatos.addEnd(controlador);
            fila++;
        }
    }

    private void mostrarColaPedidos() {
        int cantidad = singleton.obtenerColaCliente(clienteActual);
        lblPedidosCola.setText("Pedidos en la cola: " + cantidad);
    }

    private void mostrarInfo() {
        try {
            mostrarColaPedidos();
            //cargarMenu(Constantes.crearPedidos());
            cargarHistorial(singleton.obtenerHistorialCliente(clienteActual));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void initData(Cliente clienteActual) {
        this.clienteActual = clienteActual;
        mostrarInfo();
    }
}
