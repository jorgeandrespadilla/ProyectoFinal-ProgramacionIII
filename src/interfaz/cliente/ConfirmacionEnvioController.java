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

    private boolean goBackToTarget;

    @FXML
    private Label lblMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleCancel(ActionEvent event) throws Exception {
        if (goBackToTarget) {
            stackManager.goBackToTarget();
        }
        else {
            stackManager.goBack();
        }
    }

    public void initData(String message, boolean goBackToTarget) {
        lblMessage.setText(message);
        this.goBackToTarget = goBackToTarget;
    }
}
