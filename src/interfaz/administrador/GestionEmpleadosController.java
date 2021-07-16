package interfaz.administrador;

import entidades.Cocinero;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class GestionEmpleadosController extends Controller implements Initializable {

    @FXML
    private TableView<Cocinero> tblEmpleados;
    @FXML
    private TableColumn<Cocinero, String> colNombre;
    @FXML
    private TableColumn<Cocinero, String> colApellido;
    @FXML
    private TableColumn<Cocinero, String> colCorreo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();

        tblEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });
    }

    public void configurarTabla() {
        //Configuracion tabla empleados
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        popularTabla();
    }

    public void popularTabla() {
        ObservableList empleados = FXCollections.observableArrayList(singleton.getCocineros().toJavaLinkedList());
        tblEmpleados.setItems(empleados);
    }

    public void refrescarTabla() {
        tblEmpleados.refresh();
    }

    @FXML
    private void regresar(ActionEvent event) {
        stackManager.goBack();
    }

    @FXML
    private void editar(ActionEvent event) {
        Cocinero cocinero = tblEmpleados.getSelectionModel().getSelectedItem();
        DetalleEmpleadoController c = this.loadPopup("administrador/DetalleEmpleado", "Detalle Empleado", Interaction.DISABLE);
        c.initInfo(cocinero);
    }

    @FXML
    private void eliminar(ActionEvent event) throws Exception {
        Cocinero cocinero = tblEmpleados.getSelectionModel().getSelectedItem();

        boolean result = showConfirmation("Eliminar Empleado", "¿Está seguro que desea eliminar al empleado " + cocinero.getNombreCompleto() + "?");
        if (result) {
            singleton.eliminarEmpleado(cocinero.getCorreo());
            popularTabla();
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
        }
    }

    @FXML
    private void agregar(ActionEvent event) {
        this.loadPopup("administrador/AgregarEmpleado", "Agregar Nuevo Empleado", Controller.Interaction.DISABLE);
    }

}
