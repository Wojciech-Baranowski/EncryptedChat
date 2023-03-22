package app.gui.login.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class InfoText {

    private final Drawable background;
    private final Text registrationSuccess;
    private final Text userNameIsEmptyError;
    private final Text userNameDoesNotExistError;
    private final Text userNameAlreadyExistsError;
    private final Text incorrectPasswordError;
    private Text currentInfo;

    public InfoText(Drawable background) {
        this.background = background;
        this.registrationSuccess = getDisplay().getDrawableFactory().makeText(
                "Successful registration!",
                background.getX() + 10,
                background.getY() + 12,
                "HBE32",
                "green"
        );
        this.userNameIsEmptyError = getDisplay().getDrawableFactory().makeText(
                "Username is empty!",
                background.getX() + 10,
                background.getY() + 12,
                "HBE32",
                "red"
        );
        this.userNameDoesNotExistError = getDisplay().getDrawableFactory().makeText(
                "Username does not exist!",
                background.getX() + 10,
                background.getY() + 12,
                "HBE32",
                "red"
        );
        this.userNameAlreadyExistsError = getDisplay().getDrawableFactory().makeText(
                "Username already exists!",
                background.getX() + 10,
                background.getY() + 12,
                "HBE32",
                "red"
        );
        this.incorrectPasswordError = getDisplay().getDrawableFactory().makeText(
                "Incorrect password!",
                background.getX() + 10,
                background.getY() + 12,
                "HBE32",
                "red"
        );
        this.currentInfo = getDisplay().getDrawableFactory().makeText(
                "",
                background.getX() + 10,
                background.getY() + 12,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.currentInfo, background);
    }

    public void setRegistrationSuccess() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.registrationSuccess;
        getScene().addObjectHigherThan(this.currentInfo, this.background);
    }

    public void setUserNameIsEmptyError() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.userNameIsEmptyError;
        getScene().addObjectHigherThan(this.currentInfo, this.background);
    }

    public void setUserNameDoesNotExistError() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.userNameDoesNotExistError;
        getScene().addObjectHigherThan(this.currentInfo, this.background);
    }

    public void setUserNameAlreadyExistsError() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.userNameAlreadyExistsError;
        getScene().addObjectHigherThan(this.currentInfo, this.background);
    }

    public void setIncorrectPasswordError() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.incorrectPasswordError;
        getScene().addObjectHigherThan(this.currentInfo, this.background);
    }

}
