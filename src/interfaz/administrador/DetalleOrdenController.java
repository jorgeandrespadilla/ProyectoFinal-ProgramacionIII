package interfaz.administrador;

import entidades.Pedido;
import estructurasdatos.List;
import entidades.ItemPedido;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Mono
 */
public class DetalleOrdenController extends Controller {

    @FXML
    private Label lblNumOrden;
    @FXML
    private Label lblNombrePlato;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblFecha;
    @FXML
    private ImageView imagenPlato;
    @FXML
    private TextArea txtAreaObservaciones;
    @FXML
    private TableView<ItemPedido> tablaPlatos;
    @FXML
    private TableColumn<ItemPedido, String> columnaPlato;
    @FXML
    private TableColumn<ItemPedido, Integer> columnaCantidad;
    @FXML
    private TableColumn<ItemPedido, String> columnaPrecio;
    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblIva;
    @FXML
    private Label lblTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaPlato.setCellValueFactory((TableColumn.CellDataFeatures<ItemPedido, String> data) -> 
                new ReadOnlyStringWrapper(data.getValue().getPlatillo().getNombre()));
        columnaPrecio.setCellValueFactory((TableColumn.CellDataFeatures<ItemPedido, String> data) -> 
                new ReadOnlyStringWrapper(data.getValue().getPlatillo().formatPrecio()));
        
        tablaPlatos.setRowFactory(tv -> {
            TableRow<ItemPedido> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    lblNombrePlato.setText(row.getItem().getPlatillo().getNombre());
                    imagenPlato.setImage(imgStorage.getImage(row.getItem().getPlatillo().getUrlImagen())); 
                }
            });
            return row;
        });
    }    
    
    public void cargarPedido(Pedido pedido) {
        lblNumOrden.setText(pedido.formatNumPedido());
        lblCliente.setText(pedido.getCliente().getNombreCompleto());
        lblFecha.setText(pedido.formatFecha());
        txtAreaObservaciones.setText(pedido.getObservacion());
        
        List data = pedido.getPlatillos();
        ObservableList platos = FXCollections.observableArrayList(data.toJavaLinkedList());
        tablaPlatos.setItems(platos);
        lblSubtotal.setText(String.format("$%.2f", pedido.subtotal()));
        lblIva.setText(String.format("$%.2f", pedido.IVA()));
        lblTotal.setText(String.format("$%.2f", pedido.total()));
    }

    @FXML
    private void regresar(ActionEvent event) {
        stackManager.goBack();
    }
    
}
