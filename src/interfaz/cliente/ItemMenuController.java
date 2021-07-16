package interfaz.cliente;

import entidades.ItemPedido;
import interfaz.Controller;
import interfaz.StackManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Alain
 */
public class ItemMenuController extends Controller implements Initializable{
    private ItemPedido relacionado;
    @FXML
    private Text titulo;
    @FXML
    private Text precio;
    @FXML
    protected Text cantidad;
    @FXML
    private ImageView imagen;
    
    public ItemPedido getRelacionado() {
        return relacionado;
    } 
    
    public void inicializar(ItemPedido platillo, StackManager parent){
       this.relacionado = platillo;
       this.stackManager = parent;
       this.titulo.setText(platillo.getPlatillo().getNombre());
       this.precio.setText(singleton.formatPrecio(platillo.getPlatillo().getPrecio()));
       this.imagen.setImage(imgStorage.getImage(platillo.getPlatillo().getUrlImagen()));
       this.cantidad.setText("" + this.relacionado.getCantidad()); 
    }
    @FXML
    public void agregarCantidad(ActionEvent event){
        this.relacionado.agregarUnidad();
        this.cantidad.setText("" + this.relacionado.getCantidad());
    }
    
    @FXML
    public void quitarCantidad(ActionEvent event){
        this.relacionado.quitarUnidad();
        this.cantidad.setText("" + this.relacionado.getCantidad());
    }
    
    @FXML
    public void mostrarInfo(ActionEvent a) {
        InfoPlatilloController controlador = stackManager.loadScreen("cliente/InfoPlatillo");
        controlador.initData(relacionado.getPlatillo());
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoPlatillo.fxml"));
//            Parent root = (Parent) loader.load();
//            InfoPlatilloController controlador = loader.getController();
//            controlador.cargarInfo(relacionado.getPlatillo());
//            Scene home_page_scene = new Scene(root);
//            Stage app_stage = (Stage)((Node) a.getSource()).getScene().getWindow();
//            app_stage.setScene(home_page_scene); 
//            app_stage.show();
//        }catch(IOException e){
//            System.out.println(e.getMessage());
//        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cantidad.setText(String.valueOf(0));
    }
    
}
