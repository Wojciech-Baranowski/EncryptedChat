package app.gui.buttons;

import app.gui.commands.AttachFileToSendCommand;
import engine.button.SimpleButton;
import engine.common.Command;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.input.inputCombination.InputCombination;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.scene.SceneBean.getScene;

public class AddFile {

    private final SimpleButton addFile;

    public AddFile(Drawable background) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 410,
                background.getY(),
                50,
                50,
                2,
                "lightBlue",
                "yellow"
        );
        Drawable text = getDisplay().getDrawableFactory().makeText(
                "+",
                drawable.getX() + 11,
                drawable.getY(),
                "HBE48",
                "black"
        );
        drawable = new DrawableComposition(drawable, text);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        Command command = new AttachFileToSendCommand();
        this.addFile = new SimpleButton(drawable, activationCombination, command);
        getScene().addOnHighest(this.addFile);
    }

}
