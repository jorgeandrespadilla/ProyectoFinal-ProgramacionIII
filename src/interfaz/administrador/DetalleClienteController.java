package interfaz.administrador;

import entidades.Cliente;
import entidades.Pedido;
import estructurasdatos.List;
import interfaz.Popup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utilities.FilterTools;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class DetalleClienteController extends Popup implements Initializable {

    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextArea txtDireccion;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableView<Pedido> tblPedidosCliente;
    @FXML
    private TableColumn<Pedido, String> colId;
    @FXML
    private TableColumn<Pedido, String> colFecha;
    @FXML
    private TableColumn<Pedido, Double> colValorTotal;
    private Cliente cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().formatNumPedido())));
        colFecha.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().formatFecha())));
        colValorTotal.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().total()));
        colValorTotal.setCellFactory(tc -> new TableCell<Pedido, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(singleton.formatPrecio(precio));
                }
            }
        });
    }

    private void agregarListeners() {
        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            btnGuardar.setDisable(false);
        });
        txtCedula.textProperty().addListener(listener);
        txtNombre.textProperty().addListener(listener);
        txtApellido.textProperty().addListener(listener);
        txtCorreo.textProperty().addListener(listener);
        txtDireccion.textProperty().addListener(listener);
    }

    public void initData(Cliente cliente) {
        //Datos Cliente
        this.cliente = cliente;
        txtCedula.setText(cliente.getCedula());
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtCorreo.setText(cliente.getCorreo());
        txtDireccion.setText(cliente.getDireccion());
        //Tabla Historial
        List<Pedido> data = singleton.obtenerHistorialCliente(cliente);
        ObservableList pedidosCliente = FXCollections.observableArrayList(data.toJavaLinkedList());
        tblPedidosCliente.setItems(pedidosCliente);
        agregarListeners();
    }

    @FXML
    private void regresar(ActionEvent event) {
        this.close();
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String cedula = txtCedula.getText().trim();
        String direccion = txtDireccion.getText().trim();
        try {
            verificarCampos();
        } catch (Exception ex) {
            showMessage("Guardar Cambios", ex.getMessage());
            return;
        }
        cliente.setCorreo(correo);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCedula(cedula);
        cliente.setDireccion(direccion);

        btnGuardar.setDisable(true);
        GestionClientesController c = (GestionClientesController) this.parent;
        c.refrescarTabla();
        showMessage("Guardar Cambios", "Cliente actualizado con éxito.");
        this.close();
    }

    private void verificarCampos() throws Exception {
        if (txtNombre.getText().trim().isEmpty()
                || txtApellido.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty()
                || txtCedula.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()) {
            throw new Exception("Debe llenar todos los campos para continuar");
        }

        String correo = txtCorreo.getText().trim();
        String cedula = txtCedula.getText().trim();
        if (!FilterTools.isValidEmail(correo)) {
            throw new Exception("El correo ingresado no es válido");
        }
        if (!FilterTools.isValidCedula(cedula)) {
            throw new Exception("La cédula ingresada no es válida");
        }
    }

}
