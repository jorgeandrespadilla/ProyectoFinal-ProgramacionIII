package interfaz.administrador;

import entidades.Pedido;
import estructurasdatos.List;
import interfaz.Controller;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionOrdenesController extends Controller implements Initializable {

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private TableColumn<Pedido, Integer> id;
    @FXML
    private TableColumn<Pedido, String> cliente;
    @FXML
    private TableColumn<Pedido, String> estado;
    @FXML
    private TableColumn<Pedido, String> fecha;
    @FXML
    private TableColumn<Pedido, String> valorTotal;
    @FXML
    private DatePicker fechaInicial;
    @FXML
    private DatePicker fechaFinal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFechas();

        tablaPedidos.setRowFactory(tv -> {
            TableRow<Pedido> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    mostrarDetallePedido(row.getItem());
                }
            });
            return row;
        });

        //Configuración Tabla
        id.setCellValueFactory(new PropertyValueFactory<>("numPedido"));
        estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        fecha.setCellValueFactory((CellDataFeatures<Pedido, String> data)
                -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().formatFecha())));
        valorTotal.setCellValueFactory((CellDataFeatures<Pedido, String> data)
                -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().total())));
        cliente.setCellValueFactory((CellDataFeatures<Pedido, String> data)
                -> new ReadOnlyStringWrapper(data.getValue().getCliente().getNombreCompleto()));
        List data = consultarPedidosPorFechas(singleton.getHistorialPedidos());
        ObservableList pedidosFiltrados = FXCollections.observableArrayList(data.toJavaLinkedList());
        tablaPedidos.setItems(pedidosFiltrados);
    }

    @FXML
    private void consultar(ActionEvent event) {
        List data = consultarPedidosPorFechas(singleton.getHistorialPedidos());
        ObservableList pedidosFiltrados = FXCollections.observableArrayList(data.toJavaLinkedList());
        tablaPedidos.setItems(pedidosFiltrados);
    }

    @FXML
    private void regresar(ActionEvent event) {
        stackManager.goBack();
    }

    private void setFechas() {
        fechaInicial.setValue(LocalDate.now());
        fechaFinal.setValue(LocalDate.now());
    }

    private List<Pedido> consultarPedidosPorFechas(List<Pedido> pedidos) {
        List<Pedido> pedidosFiltrados = new List<>();
        for (Pedido p : pedidos) {
            if (!p.getFecha().toLocalDate().isBefore(fechaInicial.getValue())
                    && !p.getFecha().toLocalDate().isAfter(fechaFinal.getValue())) {
                pedidosFiltrados.addEnd(p);
            }
        }
        return pedidosFiltrados;
    }

    private void mostrarDetallePedido(Pedido pedido) {
        DetalleOrdenController c = stackManager.loadScreen("administrador/DetalleOrden");
        c.cargarPedido(pedido);
    }
}
