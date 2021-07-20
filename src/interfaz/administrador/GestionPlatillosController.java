package interfaz.administrador;

import entidades.Platillo;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class GestionPlatillosController extends Controller {

    private Platillo platilloActual;

    @FXML
    private Label nombrePlatoLbl;
    @FXML
    private ImageView imagenPlato;
    @FXML
    private TextArea txtAreaIngredientes;
    @FXML
    private TableView<Platillo> tablaPlatos;
    @FXML
    private TableColumn<Platillo, String> columnaPlato;
    @FXML
    private TableColumn<Platillo, Double> columnaPrecio;
    @FXML
    private Button editarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private Label txtDisponibilidad;
    @FXML
    private Label txtTipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        actualizar();
        editarBtn.setDisable(true);
        eliminarBtn.setDisable(true);
    }

    private void setupTable() {
        //Configuración Tabla
        columnaPlato.setCellValueFactory(new PropertyValueFactory<>("nombre")); //Establece la columna del nombre de plato
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        columnaPrecio.setCellFactory(tc -> new TableCell<Platillo, Double>() {
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

        //Agregar interacción con la tabla: selección de un registro a la vez
        // http://tutorials.jenkov.com/javafx/tableview.html
        TableView.TableViewSelectionModel<Platillo> selectionModel = tablaPlatos.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Platillo> selectedItems = selectionModel.getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Platillo>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Platillo> change) {
                if (!change.getList().isEmpty()) {
                    Platillo item = change.getList().get(0);
                    platilloActual = item;
                    nombrePlatoLbl.setText(platilloActual.getNombre());
                    txtTipo.setText("Tipo: " + platilloActual.getTipo().toString());
                    imagenPlato.setImage(imgStorage.getImage(platilloActual.getUrlImagen()));
                    txtAreaIngredientes.setText(platilloActual.getDescripcion());
                    txtDisponibilidad.setText("Disponibilidad: " + platilloActual.formatDisponibilidad());
                }
                editarBtn.setDisable(false);
                eliminarBtn.setDisable(false);
            }
        });
        tablaPlatos.setPlaceholder(new Label("No hay platillos agregados"));
    }

    private void setInfoMenu() {
        nombrePlatoLbl.setText("Seleccione un plato");
        imagenPlato.setImage(null);
        txtDisponibilidad.setText("Disponibilidad: ");
        txtAreaIngredientes.setText("");
        txtTipo.setText("Tipo: ");
        ObservableList platosObservable = FXCollections.observableArrayList(singleton.getMenu().getListaPlatillos().toJavaLinkedList());
        tablaPlatos.setItems(platosObservable);

    }

    @FXML
    private void editarPlato(ActionEvent event) throws Exception {
        GestionInfoPlatilloController controlador = this.loadPopup("administrador/GestionInfoPlatillo", "Cambiar informacion", Interaction.DISABLE);
        controlador.initData(platilloActual);
    }

    @FXML
    private void regresar(ActionEvent event) throws Exception {
        stackManager.goBack();
    }

    @FXML
    private void eliminarPlatillo(ActionEvent event) throws Exception {
        boolean result = showConfirmation("Eliminar Platillo", "¿Está seguro que desea eliminar al platillo " + platilloActual.getNombre() + "?");
        if (result) {
            singleton.getMenu().quitar(platilloActual.getNombre());
            actualizar();
            editarBtn.setDisable(true);
            eliminarBtn.setDisable(true);
        }
    }

    public void eliminarPlatillo() throws Exception {
        singleton.getMenu().quitar(platilloActual.getNombre());
        actualizar();
    }

    public void actualizar() {
        setInfoMenu();
        tablaPlatos.refresh();
    }

    @FXML
    private void agregarPlatillo(ActionEvent a) {
        GestionInfoPlatilloController controlador = this.loadPopup("administrador/GestionInfoPlatillo", "Agregar platillo", Interaction.DISABLE);
        controlador.initData();
    }
}
