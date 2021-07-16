package interfaz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import utilities.Cedula;

/**
 * FXML Controller class
 *
 * @author Jorge Padilla
 */
public class InspectorController implements Initializable {

    private Stage currentStage;
    private Stage admin;
    private Stage cliente;
    private Stage cocinero;

    @FXML
    private void handleAdminClick(ActionEvent event) {
        revealStage(admin);
    }

    @FXML
    private void handleClienteClick(ActionEvent event) {
        revealStage(cliente);
    }

    @FXML
    private void handleCocineroClick(ActionEvent event) {
        revealStage(cocinero);
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        admin.close();
        cliente.close();
        cocinero.close();
        currentStage.close();
    }

    private void revealStage(Stage stage) {
        stage.show();
        stage.toFront();
    }

    @FXML
    private void generarCedula(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(Cedula.generarCedula());
        clipboard.setContent(content);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Stage currentStage, Stage admin, Stage cliente, Stage cocinero) {
        this.currentStage = currentStage;
        this.admin = admin;
        this.cliente = cliente;
        this.cocinero = cocinero;
    }

}
