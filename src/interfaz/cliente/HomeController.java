package interfaz.cliente;

import entidades.Cliente;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class HomeController extends Controller {

    private Cliente clienteActual;

    @FXML
    private Label nombreTxt;

    @FXML
    private void iniciarPedido(ActionEvent event) throws Exception {
        MenuController controlador = stackManager.loadScreen("cliente/Menu");
        controlador.initData(clienteActual);
    }

    @FXML
    private void historialPedidos(ActionEvent event) throws Exception {
        HistorialConsumoController controlador = stackManager.loadScreen("cliente/HistorialConsumo");
        controlador.initData(clienteActual);
    }

    @FXML
    private void administrarCuenta(ActionEvent event) throws Exception {
        GestionCuentaController controlador = stackManager.loadScreen("cliente/GestionCuenta");
        controlador.initData(clienteActual);
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws Exception {
        stackManager.clearStack();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    void initData(Cliente clienteActual) {
        this.clienteActual = clienteActual;
        nombreTxt.setText(this.clienteActual.getNombre() + " " + this.clienteActual.getApellido());
    }
}
