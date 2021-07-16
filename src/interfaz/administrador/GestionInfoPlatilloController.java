package interfaz.administrador;

import entidades.Platillo;
import interfaz.Popup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class GestionInfoPlatilloController extends Popup implements Initializable {

    private Platillo cambio;

    private ToggleGroup toggleDisponibilidad;
    private ToggleGroup togglePrioridad;

    @FXML
    private TextField txtNombrePlatillo;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Spinner<Double> spinnerPrecio;
    @FXML
    private ComboBox<?> tipoPlatillo;
    @FXML
    private Spinner<Integer> spinnerTiempoPrep;
    @FXML
    private RadioButton rbuttonDisponible;
    @FXML
    private RadioButton rbuttonNoDisponible;
    @FXML
    private ImageView imgPlatillo;
    @FXML
    private Label lblError;
    @FXML
    private Label txtTitulo;
    @FXML
    private RadioButton rbuttonPrioritario;
    @FXML
    private RadioButton rbuttonNoPrioritario;
    @FXML
    private Button saveBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblError.setText("");
    }

    public void initData() {
        saveBtn.setText("Agregar platillo");
        imgPlatillo.setImage(imgStorage.getMissingImg());
        cargarSpinners(0, 0);
        cargarRadioBtnDisponibilidad(true);
        cargarRadioBtnPrioridad(true);
        cargarCombo();
        txtTitulo.setText("Agregar Platillo");
    }

    public void initData(Platillo platillo) {
        saveBtn.setText("Guardar cambios");
        cambio = platillo;
        txtNombrePlatillo.setText(platillo.getNombre());
        txtDescripcion.setText(platillo.getDescripcion());
        imgPlatillo.setImage(imgStorage.getImage(platillo.getUrlImagen()));
        cargarSpinners(platillo.getPrecio(), platillo.getTiempoPreparacion());
        cargarRadioBtnDisponibilidad(platillo.isDisponible());
        cargarRadioBtnPrioridad(platillo.hasPrioridad());
        cargarCombo();
    }

    @FXML
    private void abrirImagen(ActionEvent a) {
        Image img = imgStorage.chooseImage(getWindow());
        if (img != null) {
            imgPlatillo.setImage(img);
        }
    }

    @FXML
    private void regresar(ActionEvent event) {
        imgStorage.clearTemp();
        this.close();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        try {
            if (cambio == null) {
                agregarPlatillo();
            } else {
                editarInfo();
            }
            GestionPlatillosController controlador = (GestionPlatillosController) this.parent;

            controlador.actualizar();
            this.close();
        } catch (Exception e) {
            lblError.setText(e.getMessage());
        }
    }

    private void editarInfo() throws Exception {
        verificarCampos();

        cambio.setNombre(txtNombrePlatillo.getText());
        cambio.setDescripcion(txtDescripcion.getText());

        cambio.setDisponible(rbuttonDisponible.isSelected());
        cambio.setPrioridad(rbuttonPrioritario.isSelected());
        cambio.setPrecio(spinnerPrecio.getValue());

        cambio.setTiempoPreparacion(spinnerTiempoPrep.getValue());

        if (imgStorage.hasTemp()) {
            imgStorage.deleteImage(cambio.getUrlImagen());
            cambio.setUrlImagen(imgStorage.saveImage());
        }

        if (tipoPlatillo.getSelectionModel().getSelectedItem().toString().compareTo("") != 0) {
            cambio.setTipo(tipoPlatillo.getSelectionModel().getSelectedItem().toString());
        }

    }

    private void agregarPlatillo() throws Exception {
        verificarCampos();
        Platillo platillo = new Platillo();

        platillo.setNombre(txtNombrePlatillo.getText());
        platillo.setTipo(tipoPlatillo.getSelectionModel().getSelectedItem().toString());
        platillo.setDescripcion(txtDescripcion.getText());
        platillo.setDisponible(rbuttonDisponible.isSelected());
        platillo.setPrioridad(rbuttonPrioritario.isSelected());
        platillo.setPrecio(spinnerPrecio.getValue());

        platillo.setTiempoPreparacion(spinnerTiempoPrep.getValue());

        if (imgStorage.hasTemp()) {
            platillo.setUrlImagen(imgStorage.saveImage());
        }

        singleton.getMenu().getListaPlatillos().addEnd(platillo);
    }

    private void verificarCampos() throws Exception {
        if (txtNombrePlatillo.getText().compareTo("") == 0 || txtDescripcion.getText().compareTo("") == 0) {
            throw new Exception("Los campos de texto se encuentran vacíos.");
        }
        if (spinnerPrecio.getValue() == null) {
            throw new Exception("El campo de precio se encuentra vacío.");
        }
        if (spinnerTiempoPrep.getValue() == null) {
            throw new Exception("El campo clave de tiempo de preparación se encuentra vacío.");
        }
        if (tipoPlatillo.getSelectionModel().getSelectedItem() == null) {
            throw new Exception("Seleccione el tipo de platillo.");
        }
        if (cambio == null && !imgStorage.hasTemp()) {
            throw new Exception("No se ha añadido una imagen para el platillo.");
        }
    }

    private void cargarRadioBtnDisponibilidad(boolean state) {
        toggleDisponibilidad = new ToggleGroup();
        rbuttonDisponible.setToggleGroup(toggleDisponibilidad);
        rbuttonNoDisponible.setToggleGroup(toggleDisponibilidad);
        if (state) {
            rbuttonDisponible.setSelected(true);
        } else {
            rbuttonNoDisponible.setSelected(true);
        }
    }

    private void cargarRadioBtnPrioridad(boolean state) {
        togglePrioridad = new ToggleGroup();
        rbuttonPrioritario.setToggleGroup(togglePrioridad);
        rbuttonNoPrioritario.setToggleGroup(togglePrioridad);
        if (state) {
            rbuttonPrioritario.setSelected(true);
        } else {
            rbuttonNoPrioritario.setSelected(true);
        }
    }

    private void cargarCombo() {
        ObservableList tipo = FXCollections.observableArrayList(Platillo.Tipo.values());
        tipoPlatillo.setItems(tipo);
        if (cambio != null) {
            tipoPlatillo.getSelectionModel().select(tipo.indexOf(cambio.getTipo()));
        }
    }

    private void cargarSpinners(double initialPrecio, int initialTiempo) {
        this.spinnerPrecio.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.50, 75.00, (double) initialPrecio));
        this.spinnerTiempoPrep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 75, (int) initialTiempo));
    }
}
