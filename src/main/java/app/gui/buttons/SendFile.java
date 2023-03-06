package app.gui.buttons;

import app.gui.commands.SendFileCommand;
import engine.button.SimpleButton;
import engine.common.Command;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.input.inputCombination.InputCombination;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.scene.SceneBean.getScene;

public class SendFile {

    private final SimpleButton sendFile;

    public SendFile(Drawable background) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 300,
                background.getY() + 156,
                150,
                50,
                2,
                "lightBlue",
                "yellow"
        );
        Drawable text = getDisplay().getDrawableFactory().makeText(
                "Send",
                drawable.getX() + 36,
                drawable.getY() + 11,
                "HBE32",
                "black"
        );
        drawable = new DrawableComposition(drawable, text);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        Command command = new SendFileCommand();
        this.sendFile = new SimpleButton(drawable, activationCombination, command);
        getScene().addOnHighest(this.sendFile);
    }

}
