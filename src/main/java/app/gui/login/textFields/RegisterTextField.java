package app.gui.login.textFields;

import app.engine.display.Drawable;
import app.engine.input.textField.TextField;

import static app.engine.scene.SceneBean.getScene;

public class RegisterTextField {

    private final TextField userName;
    private final TextField password;
    private final TextField repeatedPassword;

    RegisterTextField(Drawable background) {
        this.userName = new TextField(
                background.getX() + 20,
                background.getY() + 130,
                315,
                50,
                10,
                10,
                "lightBlue",
                "HBE32",
                "black",
                false,
                12
        );
        getScene().addObjectHigherThan(this.userName, background);
        this.password = new TextField(
                background.getX() + 20,
                background.getY() + 220,
                315,
                50,
                10,
                10,
                "lightBlue",
                "HBE32",
                "black",
                true,
                22
        );
        getScene().addObjectHigherThan(this.password, background);
        this.repeatedPassword = new TextField(
                background.getX() + 20,
                background.getY() + 310,
                315,
                50,
                10,
                10,
                "lightBlue",
                "HBE32",
                "black",
                true,
                22
        );
        getScene().addObjectHigherThan(this.repeatedPassword, background);
    }

    public String getUserName() {
        return this.userName.getContent();
    }

    public String getPassword() {
        return this.password.getContent();
    }

    public String getRepeatedPassword() {
        return this.repeatedPassword.getContent();
    }

}
