package interfaz.cliente;

import entidades.Cliente;
import entidades.Platillo;
import estructurasdatos.List;
import entidades.ItemPedido;
import interfaz.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import utilities.FilterTools;

public class MenuController extends Controller {

    private enum Ordenamiento {
        INDEFINIDO,
        ASCENDENTE,
        DESCENDENTE
    }

    private Cliente clienteActual;
    private List<Platillo> platillosMenu;
    private List<ItemPedido> seleccion = new List<>();
    private List<ItemMenuController> controladoresPlatos = new List<>();
    private Ordenamiento opcionOrdenamiento = Ordenamiento.INDEFINIDO;

    @FXML
    private TextField nombre;
    @FXML
    private GridPane padre;
    @FXML
    private ImageView btnSortIcon;
    @FXML
    private Tooltip tooltipSort;
    @FXML
    private Label lblMensajeBusqueda;

    @FXML
    public void ordenarAlfabeticamente(ActionEvent event) throws Exception {
        nombre.setText("");
        switch (opcionOrdenamiento) {
            case INDEFINIDO:
                opcionOrdenamiento = Ordenamiento.ASCENDENTE;
                break;
            case ASCENDENTE:
                opcionOrdenamiento = Ordenamiento.DESCENDENTE;
                break;
            case DESCENDENTE:
                opcionOrdenamiento = Ordenamiento.INDEFINIDO;
                break;
        }
        actualizarOrden();
    }

    @FXML
    public void handleBusqueda(KeyEvent event) throws Exception {
        reestablecerOrdenamiento();
        if (nombre.getText().isEmpty()) {
            cargarMenu(platillosMenu);
        } else {
            List<Platillo> aux = new List<>();
            for (int i = 0; i < platillosMenu.size(); i++) {
                Platillo actual = platillosMenu.get(i);
                if (actual.isDisponible() && FilterTools.startsWith(actual.getNombre(), nombre.getText())) {
                    aux.addEnd(actual);
                }
            }
            cargarMenu(aux);
        }
    }

    @FXML
    public void finalizarSeleccion(ActionEvent a) {
        List<ItemPedido> platillosSeleccionados = new List<>();
        for (ItemPedido orden : seleccion) {
            if (orden.getCantidad() > 0) {
                platillosSeleccionados.addStart(orden);
            }
        }
        if (platillosSeleccionados.size() > 0) {
            ResumenPedidoController controlador = stackManager.loadScreen("cliente/ResumenPedido");
            controlador.initData(clienteActual, platillosSeleccionados);
        }
    }

    @FXML
    public void volver(ActionEvent event) {
        stackManager.goBack();
    }

    private void cargarMenu(List<Platillo> platillos) throws Exception {
        padre.getChildren().clear();
        padre.getRowConstraints().clear();
        lblMensajeBusqueda.setVisible(platillos.isEmpty()); //Mostrar mensaje si no se encontraron platillos
        int j=0;
        int fila = 0;
        RowConstraints filMax;
        for (int i = 0; i < platillos.size(); i++) {
            
            if(platillos.get(i).isDisponible()){
                if (j % 2 == 0) { //Generar nuevas filas
                    filMax = new RowConstraints(200);
                    padre.getRowConstraints().add(filMax);
                }
                Platillo p = platillos.get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemMenu.fxml"));
                Parent root = (Parent) loader.load();
                ItemMenuController controlador = loader.getController();
                ItemPedido aux = buscarItemMenu(p);
                if (aux != null) {
                    controlador.inicializar(aux, stackManager);
                } else {
                    ItemPedido item = new ItemPedido(p, 0);
                    seleccion.addEnd(item);
                    controlador.inicializar(item, stackManager);
                }

                padre.add(root, j % 2, fila);
                controladoresPlatos.addEnd(controlador);
                if (j % 2 == 1) {
                    fila++;
                }
                j++;
            }
        }
    }

    private ItemPedido buscarItemMenu(Platillo platillo) {
        for (ItemPedido itemMenu : seleccion) {
            if (platillo == itemMenu.getPlatillo()) {
                return itemMenu;
            }
        }
        return null;
    }

    private void actualizarOrden() throws Exception {
        String iconName = "";
        String tooltipMsg = "";
        switch (opcionOrdenamiento) {
            case INDEFINIDO: //Quitar filtro
                iconName = "sort.png";
                tooltipMsg = "Ordenar alfabéticamente";
                cargarMenu(platillosMenu);
                break;
            case ASCENDENTE:
                iconName = "sort-ascending.png";
                tooltipMsg = "Orden ascendente";
                cargarMenu(singleton.ordenarMenuABCAscendente());
                break;
            case DESCENDENTE:
                iconName = "sort-descending.png";
                tooltipMsg = "Orden descendente";
                cargarMenu(singleton.ordenarMenuABCDescendente());
                break;
        }
        Image image = new Image("assets/icons/" + iconName);
        btnSortIcon.setImage(image);
        tooltipSort.setText(tooltipMsg);
    }

    private void reestablecerOrdenamiento() {
        try {
            if (opcionOrdenamiento != Ordenamiento.INDEFINIDO) {
                opcionOrdenamiento = Ordenamiento.INDEFINIDO;
                actualizarOrden();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void initData(Cliente clienteActual) {
        this.clienteActual = clienteActual;
        this.platillosMenu = singleton.getMenu().getListaPlatillos();
        seleccion = new List<>();
        try {
            actualizarOrden();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
