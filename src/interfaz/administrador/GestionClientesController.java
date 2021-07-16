package interfaz.administrador;

import entidades.Cliente;
import infraestructura.Singleton;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class GestionClientesController extends Controller implements Initializable {

    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, String> colCedula;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colApellido;
    @FXML
    private TableColumn<Cliente, String> colCorreo;
    @FXML
    private TableColumn<Cliente, String> colDireccion;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private RadioButton radioBtnCedula;
    @FXML
    private RadioButton radioBtnNombre;
    @FXML
    private RadioButton radioBtnApellido;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //Listener para realizar doble click en fila de la tabla
        tblClientes.setRowFactory( tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    mostrarDetalleCliente(row.getItem());
                }
            });
            return row ;
        });
        
        configurarTabla();
        
        ObservableList clientes = FXCollections.observableArrayList(singleton.getClientes().toJavaLinkedList());
        FilteredList<Cliente> filteredClientes = new FilteredList(clientes, p -> true);
        tblClientes.setItems(filteredClientes);
        
        //Configuración Radio Buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        radioBtnCedula.setToggleGroup(toggleGroup);
        radioBtnNombre.setToggleGroup(toggleGroup);
        radioBtnApellido.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(radioBtnCedula);
        
        //Configuración barra busqueda
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                RadioButton seleccion = (RadioButton)toggleGroup.getSelectedToggle();
                switch (seleccion.getText())
                {
                    case "Cédula":
                        filteredClientes.setPredicate(p -> p.getCedula().toLowerCase().contains(txtBusqueda.getText().toLowerCase().trim()));
                        break;
                    case "Nombre":
                        filteredClientes.setPredicate(p -> p.getNombre().toLowerCase().contains(txtBusqueda.getText().toLowerCase().trim()));
                        break;
                    case "Apellido":
                        filteredClientes.setPredicate(p -> p.getApellido().toLowerCase().contains(txtBusqueda.getText().toLowerCase().trim()));
                        break;
                }
            }
        };
        btnBuscar.setOnAction(event);
    } 
    
    private void configurarTabla() {
        //Configuración columnas tabla
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
    }
    
    public void refrescarTabla() {
        tblClientes.refresh();
    }
    
    private void mostrarDetalleCliente(Cliente cliente) {
        DetalleClienteController controlador = this.loadPopup("administrador/DetalleCliente", "Detalle Cliente", Interaction.DISABLE);
        controlador.initData(cliente);
    }
    
    @FXML
    private void regresar(ActionEvent event) throws Exception{
        stackManager.goBack();
    }
}
