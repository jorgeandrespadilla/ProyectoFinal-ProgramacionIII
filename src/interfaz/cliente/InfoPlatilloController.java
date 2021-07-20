package interfaz.cliente;

import entidades.Platillo;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Alain
 */
public class InfoPlatilloController extends Controller {

    @FXML
    private ImageView imagen;
    @FXML
    private TextArea area;
    @FXML
    private Text titulo;
    @FXML
    private Label lblPrecio;

    @FXML
    public void volver(ActionEvent a) {
        stackManager.goBack();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Platillo platillo) {
        this.imagen.setImage(imgStorage.getImage(platillo.getUrlImagen()));
        this.lblPrecio.setText(singleton.formatPrecio(platillo.getPrecio()));
        area.setText(platillo.getDescripcion());
        this.titulo.setText(platillo.getNombre());
    }

}
