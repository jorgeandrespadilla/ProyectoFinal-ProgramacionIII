package interfaz.cliente;

import entidades.Cliente;
import interfaz.Controller;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilities.Hasher;

/**
 *
 * @author Alain
 */
public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField correoTxt;
    @FXML
    private TextField claveTxt;
    @FXML
    private Label mensajeError;

    @FXML
    private void handleBtniniciarSesion(ActionEvent event) throws Exception {
        login();
    }

    @FXML
    public void handleBtnCrearCuenta(ActionEvent event) throws Exception {
        crearCuenta();
    }

    private Cliente verificarCredenciales(String correo, String clave) throws Exception {
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
            Cliente usuario = singleton.buscarCliente(correo);
            if (usuario != null && hashClave.compareTo(usuario.getClave()) == 0) {
                return usuario;
            }
            throw new Exception("Las credenciales de acceso son incorrectas.");
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No fue posible procesar las credenciales.");
        }
    }

    private void login() {
        mensajeError.setText("");
        try {
            Cliente usuario = verificarCredenciales(correoTxt.getText(), claveTxt.getText());
            HomeController controlador = stackManager.loadScreenAsTarget("cliente/Home");
            controlador.initData(usuario);
        } catch (Exception e) {
            mensajeError.setText(e.getMessage());
        }
    }

    private void crearCuenta() throws Exception {
        stackManager.loadScreen("cliente/CreacionUsuario");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.mensajeError.setText("");
    }
}
