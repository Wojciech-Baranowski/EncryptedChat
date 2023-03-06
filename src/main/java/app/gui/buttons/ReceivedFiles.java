package app.gui.buttons;

import app.Constants;
import app.gui.commands.DownloadFileCommand;
import engine.button.SimpleButton;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.input.inputCombination.InputCombination;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.scene.SceneBean.getScene;

public class ReceivedFiles {

    private final SimpleButton[] receivedFiles;
    private final Drawable background;
    private int numberOfReceivedFiles;

    public ReceivedFiles(Drawable background) {
        this.receivedFiles = new SimpleButton[Constants.MAX_CLIENTS - 1];
        this.background = background;
        this.numberOfReceivedFiles = 0;
    }

    public void addFile(String filename) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                this.background.getX() + 2,
                this.background.getY() + (30 * this.numberOfReceivedFiles),
                756,
                30,
                2,
                (this.numberOfReceivedFiles % 2 == 0) ? "gray" : "lightBlue",
                "lighterBlue"
        );
        Drawable userName = getDisplay().getDrawableFactory().makeText(
                filename,
                this.background.getX() + 7,
                this.background.getY() + 5 + (30 * this.numberOfReceivedFiles),
                "HBE24",
                "black"
        );
        drawable = new DrawableComposition(drawable, userName);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        DownloadFileCommand command = new DownloadFileCommand();
        SimpleButton receivedFile = new SimpleButton(drawable, activationCombination, command);
        getScene().addOnHighest(receivedFile);
        this.receivedFiles[this.numberOfReceivedFiles] = receivedFile;
        this.numberOfReceivedFiles++;
    }

}
