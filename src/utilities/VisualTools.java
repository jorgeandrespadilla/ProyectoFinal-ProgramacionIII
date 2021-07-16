package utilities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Padilla
 */
public class VisualTools {

    private VisualTools() {
        throw new UnsupportedOperationException();
    }
    
    /*
    This method allows to center a stage according to user screen.
    It must be called after showing the stage on screen.
     */
    public static void centerStage(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /*
    This method allows to center a stage according to its parent window.
    It must be called after showing the stage on screen.
     */
    public static void centerOnParent(Stage parentStage, Stage childStage) {
        childStage.setX(parentStage.getX() + parentStage.getWidth() / 2 - childStage.getWidth() / 2);
        childStage.setY(parentStage.getY() + parentStage.getHeight() / 2 - childStage.getHeight() / 2);
    }

    public static void loadIcon(Stage stage, String iconPath) {
        Image image = new Image(iconPath);
        stage.getIcons().add(image);
    }
    
    private static void showProperties(Stage stage) {
        System.out.println(
            "X: " + stage.getX() 
            + "\nY: " + stage.getY() 
            + "\nWidth: " + stage.getWidth()
            + "\nHeight: " + stage.getHeight()
            + "\nCenter-X: " + (stage.getX() + stage.getWidth() / 2)
            + "\nCenter-Y: " + (stage.getY() + stage.getHeight() / 2)
        );
    }
}
