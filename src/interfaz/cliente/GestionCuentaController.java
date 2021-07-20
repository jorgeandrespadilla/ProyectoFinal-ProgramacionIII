package interfaz.cliente;

import entidades.Cliente;
import estructurasdatos.List;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilities.FilterTools;
import utilities.Hasher;

/**
 *
 * @author Alain
 */
public class GestionCuentaController extends Controller {

    private Cliente clienteActual;

    @FXML
    private TextField nombreTxt;
    @FXML
    private TextField apellidoTxt;
    @FXML
    private TextField direccionTxt;
    @FXML
    private TextField correoTxt;
    @FXML
    private TextField cedulaTxt;
    @FXML
    private TextField claveTxt;
    @FXML
    private Label error;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void handleBtnCambiarUsuario(ActionEvent event) throws Exception {
        try {
            verificarCampos();
            Cliente clienteCambio = singleton.buscarCliente(clienteActual.getCorreo());
            clienteCambio.setCorreo(correoTxt.getText());
            clienteCambio.setDireccion(direccionTxt.getText());
            if (!claveTxt.getText().isEmpty()) {
                clienteCambio.setClave(Hasher.convert(claveTxt.getText()));
            }
            stackManager.goBack();
        } catch (Exception ex) {
            error.setText(ex.getMessage());
        }

    }

    public void verificarCampos() throws Exception {
        if (correoTxt.getText().isEmpty() || direccionTxt.getText().isEmpty()) {
            throw new Exception("Llene todos los campos para crear su cuenta.");
        }
        if (!FilterTools.isValidEmail(correoTxt.getText())) {
            throw new Exception("El correo ingresado no es válido.");
        }
        if (!claveTxt.getText().isEmpty() && !FilterTools.isValidClave(claveTxt.getText())) {
            throw new Exception("La clave debe ser de al menos 8 caracteres.");
        }
        Cliente clienteAux = singleton.buscarCliente(correoTxt.getText());
        if (clienteAux!= null && !FilterTools.equals(clienteAux.getCedula(), cedulaTxt.getText())) {
            throw new Exception("El correo ya esta registrado.");
        }
    }

    @FXML
    public void handleBtnVolver(ActionEvent event) {
        stackManager.goBack();
    }

    @FXML
    public void eliminarUsuario(ActionEvent event) throws Exception {
        List<Cliente> clientes = singleton.getClientes();
        int cont = 0;
        for (Cliente cliente : clientes) {
            if (clienteActual.getCorreo().trim().compareTo(cliente.getCorreo()) == 0) {
                clientes.remove(cont);
            }
            cont++;
        }
        stackManager.loadOverlap("cliente/ConfirmacionEliminarCuenta");
    }

    void initData(Cliente clienteActual) {
        error.setText("");
        this.clienteActual = clienteActual;
        nombreTxt.setText(this.clienteActual.getNombre() + " " + this.clienteActual.getApellido());
        correoTxt.setText(this.clienteActual.getCorreo());
        nombreTxt.setText(this.clienteActual.getNombre());
        apellidoTxt.setText(this.clienteActual.getApellido());
        cedulaTxt.setText(this.clienteActual.getCedula());
        direccionTxt.setText(this.clienteActual.getDireccion());
        claveTxt.setText("");
    }

}
