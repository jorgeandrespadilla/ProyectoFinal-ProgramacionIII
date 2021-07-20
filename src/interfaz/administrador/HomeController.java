package interfaz.administrador;

import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class HomeController extends Controller {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void mostrarOrdenes(MouseEvent event) throws Exception {
        stackManager.loadScreen("administrador/GestionOrdenes");
    }

    @FXML
    private void mostrarClientes(MouseEvent event) throws Exception {
        stackManager.loadScreen("administrador/GestionClientes");
    }

    @FXML
    private void gestionarPlatillos(ActionEvent event) throws Exception {
        stackManager.loadScreen("administrador/GestionPlatillos");
    }

    @FXML
    private void mostrarEmpleados(ActionEvent event) {
        stackManager.loadScreen("administrador/GestionEmpleados");
    }

}
