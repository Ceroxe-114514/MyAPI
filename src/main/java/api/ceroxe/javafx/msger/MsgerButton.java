package api.ceroxe.javafx.msger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MsgerButton {
    private final Button button;

    public MsgerButton(String text) {
        button = new Button(text);
        button.setFont(Font.font(15));
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        button.setOnAction(eventHandler);
    }

    protected Button getButton() {
        return button;
    }

    protected void setLayoutX(double value) {
        button.setLayoutX(value);
    }

    protected void setLayoutY(double value) {
        button.setLayoutY(value);
    }

}
