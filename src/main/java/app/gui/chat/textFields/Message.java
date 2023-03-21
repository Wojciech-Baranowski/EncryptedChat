package app.gui.chat.textFields;

import app.engine.display.Drawable;
import app.engine.input.textField.TextField;
import lombok.Getter;

import static app.engine.scene.SceneBean.getScene;

public class Message {

    @Getter
    private TextField textField;

    public Message(Drawable background) {
        textField = new TextField(
                background.getX() + 10,
                background.getY() + 10,
                440,
                136,
                5,
                5,
                "lightBlue",
                "HBE24",
                "black",
                false
        );
        getScene().addOnHighest(textField);
    }

}
