package interfaz.cliente;

import entidades.Pedido;
import interfaz.Controller;
import interfaz.StackManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ItemHistorialController extends Controller {

    private Pedido pedido;
    
    @FXML
    private Label lblFactura;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblPrecio;
    @FXML
    private Separator separator;
    
    @FXML
    public void handleLblFactura(MouseEvent e) {
       InfoConsumoController controlador = stackManager.loadScreen("cliente/InfoConsumo");
       controlador.initData(pedido, stackManager);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void inicializar(Pedido pedido, boolean lastItem, StackManager parent){
       this.pedido = pedido;
       this.stackManager=parent;
       this.lblFactura.setText("Orden N°" + pedido.formatNumPedido());
       this.lblFecha.setText(pedido.formatFecha());
       this.lblPrecio.setText(singleton.formatPrecio(pedido.total()));
       this.separator.setVisible(!lastItem);
    }  
}
