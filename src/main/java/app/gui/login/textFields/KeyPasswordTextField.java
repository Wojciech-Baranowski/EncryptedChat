package app.gui.login.textFields;

import app.engine.display.Drawable;
import app.engine.input.textField.TextField;

import static app.engine.scene.SceneBean.getScene;

public class KeyPasswordTextField {

    private final TextField keyPassword;

    KeyPasswordTextField(Drawable background) {
        this.keyPassword = new TextField(
                background.getX() + 417,
                background.getY() + 23,
                351,
                50,
                10,
                10,
                "gray",
                "HBE32",
                "black",
                true,
                12
        );
        getScene().addOnHighest(this.keyPassword);
    }

    public String getKeyPassword() {
        return this.keyPassword.getContent();
    }

}
