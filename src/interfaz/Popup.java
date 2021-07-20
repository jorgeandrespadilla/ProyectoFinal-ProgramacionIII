package interfaz;

import infraestructura.ImgStorage;
import infraestructura.Singleton;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import utilities.AlertMessage;

/**
 *
 * @author Jorge Padilla
 * @param <T>
 */
public abstract class Popup<T extends Controller> implements Initializable {

    protected final Singleton singleton = Singleton.getInstance();
    protected final ImgStorage imgStorage = ImgStorage.getInstance();
    protected T parent; //Parent window
    private Stage window;

    public void setParent(T parent) {
        this.parent = parent;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }
    
    public Stage getWindow() {
        return this.window;
    }

    public void close() {
        parent.checkVisibility();
        window.close();
    }

    public void bringToFront() {
        window.toFront();
    }
    
    protected void showMessage(String title, String description) {
        AlertMessage.infoAlert(title, description, window);
    }
    
    protected boolean showConfirmation(String title, String description) {
        return AlertMessage.confirmationAlert(title, description, window);
    }
}
