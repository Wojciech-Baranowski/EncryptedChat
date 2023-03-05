package app.gui.texts;

import static app.gui.background.BackgroundController.getBackgroundController;

public class TextController {

    private static TextController textController;

    private final UserName userName;
    private final FileName fileName;
    private final FileSize fileSize;
    private final FileExtension fileExtension;
    private final UploadInfo uploadInfo;
    private final YourFiles yourFiles;

    private TextController() {
        this.userName = new UserName(getBackgroundController().getUserNameBackground());
        this.fileName = new FileName(getBackgroundController().getFileToSendHeaderBackground());
        this.fileSize = new FileSize(getBackgroundController().getFileToSendBodyBackground());
        this.fileExtension = new FileExtension(getBackgroundController().getFileToSendBodyBackground());
        this.uploadInfo = new UploadInfo(getBackgroundController().getFileToSendBodyBackground());
        this.yourFiles = new YourFiles(getBackgroundController().getReceivedFilesHeaderBackground());
    }

    public static TextController getTextController() {
        if (textController == null) {
            textController = new TextController();
        }
        return textController;
    }

    public void setUserName(String text) {
        this.userName.setText(text);
    }

}
