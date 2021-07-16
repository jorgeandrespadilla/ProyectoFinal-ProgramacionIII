package interfaz;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilities.VisualTools;

/**
 * @author Jorge Padilla
 */
public class StackManager {

    private final StackPane root;
    private final Stage stage;
    private final String rootScreen;
    private Node targetScreen;

    public StackManager(String rootScreen, String windowTitle) {
        this.root = new StackPane();
        this.stage = new Stage();
        this.rootScreen = rootScreen;
        this.targetScreen = null;
        stage.setTitle("LA CASONA - " + windowTitle);
        setRootScreen();
        stage.setScene(new Scene(root));
        showStage();
    }

    public Stage getStage() {
        return this.stage;
    }

    private void loadStageIcon() {
        VisualTools.loadIcon(stage, "assets/icons/icon.png");
    }

    private void showStage() {
        loadStageIcon();
        stage.setResizable(false);
        stage.show();
        VisualTools.centerStage(stage);
    }

    /*Set root screen/layer*/
    private <T extends Controller> void setRootScreen() {
        try {
            root.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/" + this.rootScreen + ".fxml"));
            Parent screen = (Parent) loader.load();
            T controller = loader.getController();
            controller.setStackManager(this);
            root.getChildren().add(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Load and add a screen*/
    private <T extends Controller> T loadScreen(String path, boolean isTargetScreen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/" + path + ".fxml"));
            Parent screen = (Parent) loader.load();
            T controller = loader.getController();
            controller.setStackManager(this);

            /*Hide previous layer*/
            getLast().setVisible(false);

            root.getChildren().add(screen);
            if (isTargetScreen) {
                this.targetScreen = screen;
            }
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Controller> T loadScreen(String path) {
        return loadScreen(path, false);
    }

    //A target screen represents a screen where any upper screen can go back to if defined.
    public <T extends Controller> T loadScreenAsTarget(String path) {
        return loadScreen(path, true);
    }
    
    /*Load and add overlapping screen (doesn't hide previous screen)*/
    public <T extends Controller> T loadOverlap(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/" + path + ".fxml"));
            Parent screen = (Parent) loader.load();
            T controller = loader.getController();
            controller.setStackManager(this);

            root.getChildren().add(screen);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Go to previous screen/layer*/
    public void goBack() {
        root.getChildren().remove(getLast());
        getLast().setVisible(true); //Show previous layer
    }

    /*Return to root layer*/
    public void goBackToTarget() throws Exception {
        if (targetScreen == null) {
            throw new NullPointerException("A target screen must be specified.");
        }
        while (getLast() != targetScreen) {
            goBack();
        }
    }

    /*Return to root layer*/
    public void clearStack() throws Exception {
        if (root.getChildren().size() > 1) {
            goBack();
            clearStack();
        } else {
            setRootScreen();
        }
    }

    public Node getLast() {
        int last = root.getChildren().size() - 1;
        return root.getChildren().get(last);
    }

}
