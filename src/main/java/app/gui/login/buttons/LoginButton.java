package app.gui.login.buttons;

import app.commands.LoginCommand;
import app.engine.button.SimpleButton;
import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.display.text.Text;
import app.engine.input.inputCombination.InputCombination;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

public class LoginButton {

    private SimpleButton login;

    public LoginButton(Drawable background) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 185,
                background.getY() + 295,
                150,
                50,
                2,
                "lightBlue",
                "lighterBlue"
        );
        Text text = getDisplay().getDrawableFactory().makeText(
                "Login",
                drawable.getX() + 36,
                drawable.getY() + 10,
                "HBE32",
                "black"
        );
        drawable = new DrawableComposition(drawable, text);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        Command command = new LoginCommand();
        this.login = new SimpleButton(drawable, activationCombination, command);
        getScene().addObjectHigherThan(this.login, background);
    }

}
