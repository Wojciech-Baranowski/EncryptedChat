package app.gui.buttons;

import app.gui.commands.DetachFileToSendCommand;
import engine.button.SimpleButton;
import engine.common.Command;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.input.inputCombination.InputCombination;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.scene.SceneBean.getScene;

public class DetachFile {

    private final SimpleButton removeFile;

    public DetachFile(Drawable background) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 360,
                background.getY(),
                50,
                50,
                2,
                "lightBlue",
                "lighterBlue"
        );
        Drawable text = getDisplay().getDrawableFactory().makeText(
                "-",
                drawable.getX() + 17,
                drawable.getY(),
                "HBE48",
                "black"
        );
        drawable = new DrawableComposition(drawable, text);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        Command command = new DetachFileToSendCommand();
        this.removeFile = new SimpleButton(drawable, activationCombination, command);
        getScene().addOnHighest(this.removeFile);
    }

}
