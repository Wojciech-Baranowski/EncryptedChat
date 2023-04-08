package app.gui.chat.textFields;

import app.engine.display.Drawable;
import app.engine.input.textField.TextField;
import lombok.Getter;

import static app.engine.scene.SceneBean.getScene;

public class MessageTextField {

    @Getter
    private TextField textField;

    MessageTextField(Drawable background) {
        this.textField = new TextField(
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
        getScene().addOnHighest(this.textField);
    }

    public String getTextFieldContent() {
        return this.textField.getContent();
    }

    public void clear() {
        this.textField.clear();
    }

}
