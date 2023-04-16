package app.gui.login.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class KeyPasswordText {

    private final Text keyPassword;

    KeyPasswordText(Drawable background) {
        this.keyPassword = getDisplay().getDrawableFactory().makeText(
                "Key password:",
                background.getX() + 160,
                background.getY() + 32,
                "HBE32",
                "black"
        );
        getScene().addObjectHigherThan(this.keyPassword, background);
    }

}
