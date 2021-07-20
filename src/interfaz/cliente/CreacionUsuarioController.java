package interfaz.cliente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import entidades.Cliente;
import interfaz.Controller;
import utilities.FilterTools;
import utilities.Hasher;

/**
 *
 * @author Alain
 */
public class CreacionUsuarioController extends Controller implements Initializable {

    @FXML
    private TextField nombreTxt;
    @FXML
    private TextField apellidoTxt;
    @FXML
    private TextField claveTxt;
    @FXML
    private TextField direccionTxt;
    @FXML
    private TextField correoTxt;
    @FXML
    private TextField cedulaTxt;
    @FXML
    private Label error;

    @FXML
    public void handleBtnCrearUsuario(ActionEvent event) throws Exception {
        try {
            verificarCampos();
            Cliente nuevo = new Cliente(nombreTxt.getText(), apellidoTxt.getText(), correoTxt.getText(), Hasher.convert(claveTxt.getText()), cedulaTxt.getText(), direccionTxt.getText());
            singleton.agregarUsuario(nuevo);
            stackManager.clearStack();
        } catch (Exception ex) {
            error.setText(ex.getMessage());
        }

    }

    public void verificarCampos() throws Exception {
        if (nombreTxt.getText().isEmpty()
                || apellidoTxt.getText().isEmpty()
                || correoTxt.getText().isEmpty()
                || claveTxt.getText().isEmpty()
                || cedulaTxt.getText().isEmpty()
                || direccionTxt.getText().isEmpty()) {
            throw new Exception("Llene todos los campos para crear su cuenta.");
        }
        if (!FilterTools.isValidEmail(correoTxt.getText())) {
            throw new Exception("El correo ingresado no es válido.");
        }
        if (!FilterTools.isValidClave(claveTxt.getText())) {
            throw new Exception("La clave debe ser de al menos 8 caracteres.");
        }
        if (!FilterTools.isValidCedula(cedulaTxt.getText())) {
            throw new Exception("La cédula ingresada no es válida.");
        }

        for (int i = 0; i < singleton.getClientes().size(); i++) {
            if (FilterTools.normalizeEmail(correoTxt.getText()).compareTo(singleton.getClientes().get(i).getCorreo()) == 0
                    || cedulaTxt.getText().compareTo(singleton.getClientes().get(i).getCedula()) == 0) {
                throw new Exception("El correo o cédula ya están registrados.");
            }
        }
    }

    @FXML
    public void handleBtnVolver(ActionEvent event) {
        stackManager.goBack();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        error.setText("");
    }
}
