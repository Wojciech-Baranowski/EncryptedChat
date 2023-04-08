package app.gui.chat.buttons;

import app.commands.DetachFileToSendCommand;
import app.engine.button.SimpleButton;
import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.input.inputCombination.InputCombination;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

public class DetachFileButton {

    private final SimpleButton removeFile;

    DetachFileButton(Drawable background) {
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
