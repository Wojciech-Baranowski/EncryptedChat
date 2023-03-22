package app.gui.login.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class RegisterText {

    private final Text register;
    private final Text userName;
    private final Text password;
    private final Text repeatedPassword;

    public RegisterText(Drawable background) {
        this.register = getDisplay().getDrawableFactory().makeText(
                "Register:",
                background.getX() + 15,
                background.getY() + 15,
                "HBE48",
                "black"
        );
        getScene().addObjectHigherThan(this.register, background);
        this.userName = getDisplay().getDrawableFactory().makeText(
                "Username:",
                background.getX() + 22,
                background.getY() + 100,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.userName, background);
        this.password = getDisplay().getDrawableFactory().makeText(
                "Password:",
                background.getX() + 22,
                background.getY() + 190,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.password, background);
        this.repeatedPassword = getDisplay().getDrawableFactory().makeText(
                "Repeated password:",
                background.getX() + 22,
                background.getY() + 280,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.repeatedPassword, background);
    }

}
