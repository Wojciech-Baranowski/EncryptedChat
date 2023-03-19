package app.login.buttons;

import app.login.commands.RegisterCommand;
import engine.button.SimpleButton;
import engine.common.Command;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.display.text.Text;
import engine.input.inputCombination.InputCombination;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.scene.SceneBean.getScene;

public class Register {

    private SimpleButton register;

    public Register(Drawable background) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 185,
                background.getY() + 385,
                150,
                50,
                2,
                "lightBlue",
                "lighterBlue"
        );
        Text text = getDisplay().getDrawableFactory().makeText(
                "Register",
                drawable.getX() + 15,
                drawable.getY() + 10,
                "HBE32",
                "black"
        );
        drawable = new DrawableComposition(drawable, text);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        Command command = new RegisterCommand();
        this.register = new SimpleButton(drawable, activationCombination, command);
        getScene().addObjectHigherThan(this.register, background);
    }

}
