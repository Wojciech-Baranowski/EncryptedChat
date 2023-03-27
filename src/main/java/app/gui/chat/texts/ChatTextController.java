package app.gui.chat.texts;

import static app.gui.chat.backgrounds.ChatBackgroundController.getChatBackgroundController;

public class ChatTextController {

    private static ChatTextController chatTextController;

    private final UserNameText userNameText;
    private final FileNameText fileNameText;
    private final FileSizeText fileSizeText;
    private final FileExtensionText fileExtensionText;
    private final UploadInfoText uploadInfoText;
    private final YourFilesText yourFilesText;

    private ChatTextController() {
        this.userNameText = new UserNameText(getChatBackgroundController().getUserNameBackground());
        this.fileNameText = new FileNameText(getChatBackgroundController().getFileToSendHeaderBackground());
        this.fileSizeText = new FileSizeText(getChatBackgroundController().getFileToSendBodyBackground());
        this.fileExtensionText = new FileExtensionText(getChatBackgroundController().getFileToSendBodyBackground());
        this.uploadInfoText = new UploadInfoText(getChatBackgroundController().getFileToSendBodyBackground());
        this.yourFilesText = new YourFilesText(getChatBackgroundController().getReceivedFilesHeaderBackground());
    }

    public static ChatTextController getChatTextController() {
        if (chatTextController == null) {
            chatTextController = new ChatTextController();
        }
        return chatTextController;
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
