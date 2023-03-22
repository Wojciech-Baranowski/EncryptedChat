package app.gui.chat.texts;

import static app.gui.chat.backgrounds.BackgroundController.getBackgroundController;

public class TextController {

    private static TextController textController;

    private final UserNameText userNameText;
    private final FileNameText fileNameText;
    private final FileSizeText fileSizeText;
    private final FileExtensionText fileExtensionText;
    private final UploadInfoText uploadInfoText;
    private final YourFilesText yourFilesText;

    private TextController() {
        this.userNameText = new UserNameText(getBackgroundController().getUserNameBackground());
        this.fileNameText = new FileNameText(getBackgroundController().getFileToSendHeaderBackground());
        this.fileSizeText = new FileSizeText(getBackgroundController().getFileToSendBodyBackground());
        this.fileExtensionText = new FileExtensionText(getBackgroundController().getFileToSendBodyBackground());
        this.uploadInfoText = new UploadInfoText(getBackgroundController().getFileToSendBodyBackground());
        this.yourFilesText = new YourFilesText(getBackgroundController().getReceivedFilesHeaderBackground());
    }

    public static TextController getTextController() {
        if (textController == null) {
            textController = new TextController();
        }
        return textController;
    }

    public void setUserName(String userName) {
        this.userNameText.setUserName(userName);
    }

    public void setFileName(String fileName) {
        this.fileNameText.setFileName(fileName);
    }

    public void resetFileName() {
        this.fileNameText.resetFileName();
    }

    public void setFileSize(String fileSize) {
        this.fileSizeText.setFileSize(fileSize);
    }

    public void resetFileSize() {
        this.fileSizeText.resetFileSize();
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtensionText.setFileExtension(fileExtension);
    }

    public void resetFileExtension() {
        this.fileExtensionText.resetFileExtension();
    }

    public void setCurrentUploadInfoAsReadyToUpload() {
        this.uploadInfoText.setCurrentUploadInfoAsReadyToUpload();
    }

    public void setCurrentUploadInfoAsProgress(double percentage) {
        this.uploadInfoText.setCurrentUploadInfoAsProgress(percentage);
    }

    public void setCurrentUploadInfoAsSuccess() {
        this.uploadInfoText.setCurrentUploadInfoAsSuccess();
    }

    public void setCurrentUploadInfoAsError() {
        this.uploadInfoText.setCurrentUploadInfoAsError();
    }

    public void setCurrentUploadInfoAsBlank() {
        this.uploadInfoText.setCurrentUploadInfoAsBlank();
    }

}
