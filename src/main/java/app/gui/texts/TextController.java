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

    public void setUserName(int text) {
        this.userName.setUserName(text);
    }

    public void setFileName(String fileName) {
        this.fileName.setFileName(fileName);
    }

    public void resetFileName() {
        this.fileName.resetFileName();
    }

    public void setFileSize(String fileSize) {
        this.fileSize.setFileSize(fileSize);
    }

    public void resetFileSize() {
        this.fileSize.resetFileSize();
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension.setFileExtension(fileExtension);
    }

    public void resetFileExtension() {
        this.fileExtension.resetFileExtension();
    }

    public void setCurrentUploadInfoAsReadyToUpload() {
        this.uploadInfo.setCurrentUploadInfoAsReadyToUpload();
    }

    public void setCurrentUploadInfoAsProgress(double percentage) {
        this.uploadInfo.setCurrentUploadInfoAsProgress(percentage);
    }

    public void setCurrentUploadInfoAsSuccess() {
        this.uploadInfo.setCurrentUploadInfoAsSuccess();
    }

    public void setCurrentUploadInfoAsError() {
        this.uploadInfo.setCurrentUploadInfoAsError();
    }

    public void setCurrentUploadInfoAsBlank() {
        this.uploadInfo.setCurrentUploadInfoAsBlank();
    }

}
