package interfaz.administrador;

import entidades.Cocinero;
import interfaz.Popup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utilities.FilterTools;
import utilities.Hasher;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class AgregarEmpleadoController extends Popup {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtClave;
    @FXML
    private PasswordField txtConfirmacionClave;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void regresar(ActionEvent event) {
        this.close();
    }

    @FXML
    private void crearEmpleado(ActionEvent event) throws Exception {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String clave = txtClave.getText();

        try {
            verificarCampos();
        } catch(Exception ex) {
            showMessage("Crear Empleado", ex.getMessage());
            return;
        }
        
        singleton.getCocineros().addEnd(new Cocinero(nombre, apellido, correo, Hasher.convert(clave)));
        showMessage("Crear Empleado", "Empleado creado con éxito.");
        GestionEmpleadosController controlador=(GestionEmpleadosController)this.parent;
        controlador.configurarTabla();
        this.close();
    }
    
    private void verificarCampos() throws Exception {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String clave = txtClave.getText();
        String confirmacion = txtConfirmacionClave.getText();
        String camposVacios = "";
        if (nombre.isEmpty()) {
            camposVacios += "\nNombre";
        }
        if (apellido.isEmpty()) {
            camposVacios += "\nApellido";           
        }
        if (correo.isEmpty()) {
            camposVacios += "\nCorreo";
        }
        if (clave.isEmpty()) {
            camposVacios += "\nContraseña";
        }
        if (confirmacion.isEmpty()) {
            camposVacios += "\nConfirmar Contraseña";
        }
        if (!camposVacios.isEmpty()) {
            throw new Exception("Debe llenar los campos: " + camposVacios);
        }
        if (!FilterTools.isValidEmail(correo)) {
            throw new Exception("El correo ingresado no es válido");
        }
        if (singleton.buscarCocinero(correo) != null) {
            throw new Exception("Ya existe un empleado con el correo ingresado");
        }
        if (!FilterTools.isValidClave(clave)) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres");
        }
        if (!FilterTools.equals(clave, confirmacion)) {
            throw new Exception("La contraseña no coincide");
        }
    }

}
