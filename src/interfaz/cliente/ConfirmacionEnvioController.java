package interfaz.cliente;

import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jorge Padilla
 */
public class ConfirmacionEnvioController extends Controller {

    @FXML
    private Label lblMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        stackManager.goBack();
    }

    public void initData(String message) {
        lblMessage.setText(message);
    }
}
