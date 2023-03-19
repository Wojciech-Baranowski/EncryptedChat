package app.login.textFields;

import engine.display.Drawable;
import engine.input.textField.TextField;

import static engine.scene.SceneBean.getScene;

public class Login {

    private final TextField userName;
    private final TextField password;

    public Login(Drawable background) {
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
    }

    public String getUserName() {
        return this.userName.getContent();
    }

    public String getPassword() {
        return this.password.getContent();
    }

}
