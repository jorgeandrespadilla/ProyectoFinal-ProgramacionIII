package interfaz.administrador;

import entidades.Cocinero;
import interfaz.Popup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utilities.FilterTools;
import utilities.Hasher;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class DetalleEmpleadoController extends Popup implements Initializable {

    Cocinero cocinero;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtClave;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initInfo(Cocinero cocinero) {
        this.cocinero = cocinero;
        txtNombre.setText(cocinero.getNombre());
        txtApellido.setText(cocinero.getApellido());
        txtCorreo.setText(cocinero.getCorreo());
        agregarListeners();
    }

    @FXML
    private void guardarCambios(ActionEvent event) throws Exception {
        try {
            verificarCampos();
        } catch (Exception ex) {
            showMessage("Crear Empleado", ex.getMessage());
            return;
        }
        cocinero.setNombre(txtNombre.getText().trim());
        cocinero.setApellido(txtApellido.getText().trim());
        cocinero.setCorreo(txtCorreo.getText().trim());
        if (!txtClave.getText().isEmpty()) {
            cocinero.setClave(Hasher.convert(txtClave.getText()));
        }
        GestionEmpleadosController controlador = (GestionEmpleadosController) this.parent;
        controlador.refrescarTabla();
        showMessage("Crear Empleado", "Empleado actualizado con éxito");
        this.close();
    }

    private void verificarCampos() throws Exception {
        if (txtNombre.getText().isEmpty()
                || txtApellido.getText().isEmpty()
                || txtCorreo.getText().isEmpty()) {
            throw new Exception("Debe llenar todos los campos para continuar");
        }
        if (!txtClave.getText().isEmpty() && !FilterTools.isValidClave(txtClave.getText())) {
            throw new Exception("La clave debe ser de al menos 8 caracteres");
        }
        if (!FilterTools.isValidEmail(txtCorreo.getText().trim())) {
            throw new Exception("El correo ingresado no es válido");
        }
    }

    private void agregarListeners() {
        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            btnGuardar.setDisable(false);
        });
        txtNombre.textProperty().addListener(listener);
        txtApellido.textProperty().addListener(listener);
        txtCorreo.textProperty().addListener(listener);
        txtClave.textProperty().addListener(listener);
    }

    @FXML
    private void regresar(ActionEvent event) {
        this.close();
    }
}
