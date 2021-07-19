package interfaz;

import infraestructura.ImgStorage;
import infraestructura.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.AlertMessage;
import utilities.VisualTools;

/**
 *
 * @author Jorge Padilla
 */
public abstract class Controller implements Initializable {

    public enum Interaction {
        ALLOW, //Permite la interacción con la ventana padre
        DISABLE, //Deshabilita la interacción con la ventana padre
        HIDE,     //Esconde la ventana padre
    }

    protected final Singleton singleton = Singleton.getInstance();
    protected final ImgStorage imgStorage = ImgStorage.getInstance();
    protected StackManager stackManager;
    private boolean visible;

    public Controller() {
        this.visible = true;
    }

    public void setStackManager(StackManager stackManager) {
        this.stackManager = stackManager;
    }

    public void checkVisibility() {
        if (!visible) {
            this.visible = true;
            this.stackManager.getWindow().show();
        }
    }

    public <T extends Popup> T loadPopup(String path, String popupTitle, Interaction type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/" + path + ".fxml"));
            Parent screen = (Parent) loader.load();
            Scene scene = new Scene(screen);
            T controller = loader.getController();
            controller.setParent(this);

            Stage window = new Stage();
            window.setTitle(popupTitle);
            controller.setWindow(window);
            window.initOwner(this.stackManager.getLast().getScene().getWindow());
            window.setScene(scene);

            switch (type) {
                case ALLOW:
                    window.initModality(Modality.NONE);
                    break;
                case DISABLE:
                    window.initModality(Modality.WINDOW_MODAL);
                    break;
                case HIDE:
                    this.visible = false;
                    this.stackManager.getWindow().hide();
                    break;
            }

            showPopup(window);
            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showPopup(Stage window) {
        VisualTools.loadIcon(window, "assets/icons/icon.png");
        window.setResizable(false);
        window.show();
        window.setOnCloseRequest((WindowEvent we) -> {
            checkVisibility();
        });

        if (visible) {
            VisualTools.centerOnParent(stackManager.getWindow(), window);
        } else {
            VisualTools.centerStage(window);
        }
    }

    protected void showMessage(String title, String description) {
        AlertMessage.infoAlert(title, description, stackManager.getWindow());
    }

    protected boolean showConfirmation(String title, String description) {
        return AlertMessage.confirmationAlert(title, description, stackManager.getWindow());
    }
}
