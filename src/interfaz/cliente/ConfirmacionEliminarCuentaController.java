package interfaz.cliente;

import entidades.Cliente;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author Jorge Padilla
 */
public class ConfirmacionEliminarCuentaController extends Controller {

    private Cliente clienteActual;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            //Eliminar cuenta
            singleton.eliminarCuenta(clienteActual);
            stackManager.clearStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        stackManager.goBack();
    }

    public void initData(Cliente cliente) {
        this.clienteActual = cliente;
    }
}
