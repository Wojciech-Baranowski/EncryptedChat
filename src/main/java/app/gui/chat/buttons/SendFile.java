package app.gui.chat.buttons;

import app.engine.button.SimpleButton;
import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.input.inputCombination.InputCombination;
import app.gui.chat.commands.SendFileCommand;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

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
                "lighterBlue"
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
