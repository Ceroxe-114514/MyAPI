package api.ceroxe.javafx.msger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorMsgerController {

    @FXML
    private Label DownTextLabel;
    @FXML
    private Label UpTextLabel;

    public Label getDownTextLabel() {
        return DownTextLabel;
    }

    public Label getUpTextLabel() {
        return UpTextLabel;
    }

}
