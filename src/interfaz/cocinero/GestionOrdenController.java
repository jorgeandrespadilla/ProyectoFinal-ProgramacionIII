package interfaz.cocinero;

import entidades.Cocinero;
import entidades.Pedido;
import entidades.ItemPedido;
import interfaz.Popup;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Jorge Padilla
 */
public class GestionOrdenController extends Popup {

    private Cocinero cocinero;
    private Pedido pedidoActual;
    private Timer timerPendiente;

    @FXML
    private TableView<ItemPedido> tablaPlatos;
    @FXML
    private TableColumn<ItemPedido, String> columnaPlato;
    @FXML
    private TableColumn<ItemPedido, Integer> columnaCantidad;
    @FXML
    private TableColumn<ItemPedido, Boolean> columnaPrioridad;
    @FXML
    private Label nombreCocinero;
    @FXML
    private Label numOrdenLbl;
    @FXML
    private Label nombrePlatoLbl;
    @FXML
    private ImageView imagenPlato;
    @FXML
    private TextArea txtAreaObservaciones;
    @FXML
    private Label lblOrdenesEnCola;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Cocinero cocinero) {
        this.pedidoActual = null;
        this.cocinero = cocinero;
        nombreCocinero.setText(cocinero.getNombreCompleto()); //Establece el nombre de usuario

        setupTable();
        setOrdenesPendientes();
        //Pedido pedido = Constantes.crearOrden();
        setupTimerPendientes();

        getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                salir();
            }
        });
    }
    
    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        salir();
    }

    @FXML
    private void finalizarOrden(ActionEvent event) throws Exception {
        if (pedidoActual != null) {
            Pedido pedidoFinalizado = cocinero.completarPedido();
            singleton.getHistorialPedidos().addEnd(pedidoFinalizado);
            pedidoActual = null;
            if (cocinero.getCantPendientes() != 0) {
                pedidoActual = cocinero.getPedidoActual();
                cocinero.procesarPedido();
            }
            setInfoPedido();
        }
    }

    private void setupTable() {
       //Configuración Tabla
        columnaPlato.setCellValueFactory(new Callback<CellDataFeatures<ItemPedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ItemPedido, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getPlatillo().getNombre());
            }
        }); //Establece la columna del nombre de plato
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad")); //Establece la columna de cantidad
        
        columnaPrioridad.setCellValueFactory(new Callback<CellDataFeatures<ItemPedido, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<ItemPedido, Boolean> data) {
                return new ReadOnlyBooleanWrapper(data.getValue().getPlatillo().hasPrioridad());
            }
        });
        
        columnaPrioridad.setCellFactory(tc -> new TableCell<ItemPedido, Boolean>() {
            @Override
            protected void updateItem(Boolean prioridad, boolean empty) {
                super.updateItem(prioridad, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(prioridad ? "Sí" : "");
                }
            }
        });

        //Agregar interacción con la tabla: selección de un registro a la vez
        // http://tutorials.jenkov.com/javafx/tableview.html
        TableViewSelectionModel<ItemPedido> selectionModel = tablaPlatos.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ItemPedido> selectedItems = selectionModel.getSelectedItems();
        selectedItems.addListener(new ListChangeListener<ItemPedido>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends ItemPedido> change) {
                if (!change.getList().isEmpty()) {
                    ItemPedido item = change.getList().get(0);
                    nombrePlatoLbl.setText(item.getPlatillo().getNombre());
                    imagenPlato.setImage(imgStorage.getImage(item.getPlatillo().getUrlImagen()));
                }
            }
        });

        tablaPlatos.setPlaceholder(new Label("No hay órdenes pendientes"));
    }

    private void setOrdenesPendientes() {
        lblOrdenesEnCola.setText("Ordenes en la cola: " + cocinero.getCantPendientes());
    }

    //Establece la información de la tabla de acuerdo al valor de pedidoActual: null o un objeto de tipo Pedido
    private void setInfoPedido() {
        nombrePlatoLbl.setText("Seleccione un plato");
        imagenPlato.setImage(null);

        if (pedidoActual == null) { //Mostrar interfaz sin datos del pedido
            numOrdenLbl.setText("ORDEN N°-----");
            txtAreaObservaciones.setText("");
            tablaPlatos.setItems(null);
        } else { //Muestra datos del pedido
            numOrdenLbl.setText("ORDEN N°" + pedidoActual.formatNumPedido());
            txtAreaObservaciones.setText(pedidoActual.getObservacion());
            //Agregar platos con su respectiva cantidad a la tabla
            ObservableList platosObservable = FXCollections.observableArrayList(pedidoActual.getPlatillos().toJavaLinkedList());
            tablaPlatos.setItems(platosObservable);
            /*Ordenar inicialmente la tabla de platos de lo más prioritario a lo no prioritario*/
            columnaPrioridad.setSortType(TableColumn.SortType.DESCENDING);
            tablaPlatos.getSortOrder().add(columnaPrioridad);
            tablaPlatos.sort();
        }
    }

    //Permite crear un temporizador para refrescar el contador de ordenes pendientes
    public void setupTimerPendientes() {
        timerPendiente = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (cocinero != null) {
                            try {
                                if (pedidoActual == null && cocinero.getCantPendientes() != 0) {
                                    pedidoActual = cocinero.getPedidoActual();
                                    cocinero.procesarPedido();
                                    setInfoPedido();
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            setOrdenesPendientes();
                        }

                    }
                });
            }
        };
        timerPendiente.schedule(task, 0, 1000);
    }

    public void salir() {
        try {
            ((LoginController) parent).reloadScreen();
            timerPendiente.cancel();
            timerPendiente.purge();
            if (pedidoActual != null) {
                pedidoActual.setEstado(Pedido.Estado.PENDIENTE);
            }
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
