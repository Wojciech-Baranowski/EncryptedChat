package app.gui.textFields;

import engine.display.Drawable;
import engine.input.textField.TextField;

import static engine.scene.SceneBean.getScene;

public class Message {

    TextField textField;

    public Message(Drawable background) {
        textField = new TextField(
                background.getX() + 10,
                background.getY() + 10,
                440,
                136,
                "lightBlue",
                "HBE24",
                "black"
        );
        getScene().addOnHighest(textField);
    }

}
