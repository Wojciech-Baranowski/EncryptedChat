package app.login.texts;

import engine.display.Drawable;
import engine.display.text.Text;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class Login {

    private final Text login;
    private final Text userName;
    private final Text password;

    public Login(Drawable background) {
        this.login = getDisplay().getDrawableFactory().makeText(
                "Log in:",
                background.getX() + 15,
                background.getY() + 15,
                "HBE48",
                "black"
        );
        getScene().addObjectHigherThan(this.login, background);
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
    }

}
