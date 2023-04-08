package app.gui.chat.buttons;

import app.commands.DownloadFileCommand;
import app.engine.button.SimpleButton;
import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.input.inputCombination.InputCombination;
import common.transportObjects.FileData;
import common.transportObjects.UserData;

import static app.Constants.MAX_FILES_PER_PAGE;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

public class ReceivedFilesOrTextsButton {

    private final SimpleButton[] receivedFiles;
    private final Drawable background;
    private int numberOfReceivedFiles;

   ReceivedFilesOrTextsButton(Drawable background) {
       this.receivedFiles = new SimpleButton[MAX_FILES_PER_PAGE];
       this.background = background;
       this.numberOfReceivedFiles = 0;
   }

    public void addReceivedFile(UserData senderUserData, FileData fileData) {
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
                senderUserData.getUserName() + " (" + senderUserData.getId() + "): " + fileData.getTrimmedFileNameAndData(),
                this.background.getX() + 7,
                this.background.getY() + 3 + (30 * this.numberOfReceivedFiles),
                "HBE24",
                "black"
        );
        drawable = new DrawableComposition(drawable, fileName);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        DownloadFileCommand command = new DownloadFileCommand(fileData.hashCode());
        SimpleButton receivedFile = new SimpleButton(drawable, activationCombination, command);
        getScene().addOnHighest(receivedFile);
        this.receivedFiles[this.numberOfReceivedFiles] = receivedFile;
        this.numberOfReceivedFiles++;
    }

    public void addReceivedText(UserData senderUserData, String text) {
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
                senderUserData.getUserName() + " (" + senderUserData.getId() + "): " + text,
                this.background.getX() + 7,
                this.background.getY() + 3 + (30 * this.numberOfReceivedFiles),
                "HBE24",
                "black"
        );
        drawable = new DrawableComposition(drawable, fileName);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        SimpleButton receivedFile = new SimpleButton(drawable, activationCombination, Command.BLANK);
        getScene().addOnHighest(receivedFile);
        this.receivedFiles[this.numberOfReceivedFiles] = receivedFile;
        this.numberOfReceivedFiles++;
    }

}
