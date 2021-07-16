package interfaz;

import infraestructura.ImgStorage;
import infraestructura.Singleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.VisualTools;

/**
 *
 * @author Jorge Padilla
 */
public class Principal extends Application {

    private static StackManager admin;
    private static StackManager cliente;
    private static StackManager cocinero;

    @Override
    public void start(Stage stage) throws Exception {
        Singleton.getInstance();
        ImgStorage.getInstance();

        admin = new StackManager("administrador/Home", "Portal Administrador");
        cliente = new StackManager("cliente/Login", "Portal Clientes");
        cocinero = new StackManager("cocinero/Login", "Portal Cocineros");

        setupInspector();
    }

    private void setupInspector() throws Exception {
        Stage stage = new Stage();
        VisualTools.loadIcon(stage, "assets/icons/manager-icon.png");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inspector.fxml"));
        Parent screen = (Parent) loader.load();
        InspectorController controller = loader.getController();
        controller.initData(stage, admin.getStage(), cliente.getStage(), cocinero.getStage());
        stage.setScene(new Scene(screen));
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
