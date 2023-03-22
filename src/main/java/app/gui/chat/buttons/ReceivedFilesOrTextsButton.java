package app.gui.chat.buttons;

import app.commands.DownloadFileCommand;
import app.engine.button.SimpleButton;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.input.inputCombination.InputCombination;

import static app.Constants.MAX_CLIENTS;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

public class ReceivedFilesOrTextsButton {

    private final SimpleButton[] receivedFiles;
    private final Drawable background;
    private int numberOfReceivedFiles;

    public ReceivedFilesOrTextsButton(Drawable background) {
        this.receivedFiles = new SimpleButton[MAX_CLIENTS - 1];
        this.background = background;
        this.numberOfReceivedFiles = 0;
    }

    public void addReceivedFile(String filename, int fileHashCode) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                this.background.getX() + 2,
                this.background.getY() + (30 * this.numberOfReceivedFiles),
                756,
                30,
                2,
                (this.numberOfReceivedFiles % 2 == 0) ? "gray" : "lightBlue",
                "lighterBlue"
        );
        Drawable fileName = getDisplay().getDrawableFactory().makeText(
                filename,
                this.background.getX() + 7,
                this.background.getY() + 3 + (30 * this.numberOfReceivedFiles),
                "HBE24",
                "black"
        );
        drawable = new DrawableComposition(drawable, fileName);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        DownloadFileCommand command = new DownloadFileCommand(fileHashCode);
        SimpleButton receivedFile = new SimpleButton(drawable, activationCombination, command);
        getScene().addOnHighest(receivedFile);
        this.receivedFiles[this.numberOfReceivedFiles] = receivedFile;
        this.numberOfReceivedFiles++;
    }

    public void addReceivedText(String text) {
        Drawable drawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                this.background.getX() + 2,
                this.background.getY() + (30 * this.numberOfReceivedFiles),
                756,
                30,
                2,
                (this.numberOfReceivedFiles % 2 == 0) ? "gray" : "lightBlue",
                "lighterBlue"
        );
        Drawable fileName = getDisplay().getDrawableFactory().makeText(
                text,
                this.background.getX() + 7,
                this.background.getY() + 3 + (30 * this.numberOfReceivedFiles),
                "HBE24",
                "black"
        );
        drawable = new DrawableComposition(drawable, fileName);
        SimpleButton receivedFile = new SimpleButton(drawable, null, null);
        getScene().addOnHighest(receivedFile);
        this.receivedFiles[this.numberOfReceivedFiles] = receivedFile;
        this.numberOfReceivedFiles++;
    }

}