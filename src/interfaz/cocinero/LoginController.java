package interfaz.cocinero;

import entidades.Cocinero;
import interfaz.Controller;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilities.Hasher;

/**
 * FXML Controller class
 *
 * @author Jorge Padilla
 */
public class LoginController extends Controller {

    private Cocinero cocinero;

    @FXML
    private Label errorMsg;
    @FXML
    private TextField campoCorreo;
    @FXML
    private PasswordField campoClave;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMsg.setText("");
    }
    
    @FXML
    private void handleBtnLogin(ActionEvent event) {
        login(event.getSource());
    }

    @FXML
    private void handleFieldKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login(keyEvent.getSource());
        }
    }

    private void login(Object source) {
        errorMsg.setText("");
        try {
            this.cocinero = verificarCredenciales(campoCorreo.getText(), campoClave.getText());
            GestionOrdenController controlador = loadPopup("cocinero/GestionOrden", "La Casona - Portal Cocineros", Interaction.HIDE);
            controlador.initData(cocinero);
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    private Cocinero verificarCredenciales(String correo, String clave) throws Exception {
        if (correo.isEmpty() && clave.isEmpty()) {
            throw new Exception("Los campos se encuentran vacíos.");
        }
        if (correo.isEmpty()) {
            throw new Exception("El campo correo electrónico se encuentra vacío.");
        }
        if (clave.isEmpty()) {
            throw new Exception("El campo clave se encuentra vacío.");
        }
        try {
            String hashClave = Hasher.convert(clave);
            Cocinero usuario = singleton.buscarCocinero(correo);
            if (usuario != null && hashClave.compareTo(usuario.getClave()) == 0) {
                return usuario;
            }
            throw new Exception("Las credenciales de acceso son incorrectas.");
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No fue posible procesar las credenciales.");
        }
    }

    public void reloadScreen() throws Exception {
        stackManager.clearStack();
    }
}
