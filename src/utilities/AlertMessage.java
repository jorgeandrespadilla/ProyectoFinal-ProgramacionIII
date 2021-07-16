package utilities;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Padilla
 */
public class AlertMessage {

    private AlertMessage() {
        throw new UnsupportedOperationException();
    }

    public static boolean confirmationAlert(String title, String description, Stage owner) {
        Alert alert = setupAlert(title, description, owner, Alert.AlertType.CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancelar");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void infoAlert(String title, String description, Stage owner) {
        Alert alert = setupAlert(title, description, owner, Alert.AlertType.INFORMATION);
        alert.showAndWait();
    }

    private static Alert setupAlert(String title, String description, Stage owner, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.initOwner(owner);
        alert.initModality(Modality.WINDOW_MODAL);

        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Aceptar");
        return alert;
    }
}
