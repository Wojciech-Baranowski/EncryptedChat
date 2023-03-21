package app.gui.chat.buttons;

import app.CipherType;

import static app.gui.chat.backgrounds.BackgroundController.getBackgroundController;

public class ButtonController {

    private static ButtonController buttonController;

    private Receivers receivers;
    private ReceivedFiles receivedFiles;
    private AttachFile attachFile;
    private DetachFile detachFile;
    private SendFile sendFile;
    private Cipher cipher;

    private ButtonController() {
        this.receivers = new Receivers(getBackgroundController().getReceiversBackground());
        this.receivedFiles = new ReceivedFiles(getBackgroundController().getReceivedFilesBodyAllBackground());
        this.attachFile = new AttachFile(getBackgroundController().getFileToSendHeaderBackground());
        this.detachFile = new DetachFile(getBackgroundController().getFileToSendHeaderBackground());
        this.sendFile = new SendFile(getBackgroundController().getFileToSendBodyBackground());
        this.cipher = new Cipher(getBackgroundController().getFileToSendBodyBackground());
    }

    public static ButtonController getButtonController() {
        if (buttonController == null) {
            buttonController = new ButtonController();
        }
        return buttonController;
    }

    public CipherType getCipherType() {
        return this.cipher.getCipherType();
    }

    public Integer getSelectedReceiverId() {
        return this.receivers.getSelectedReceiverId();
    }

    public void addReceiver(int receiverId) {
        this.receivers.addReceiver(receiverId);
    }

    public void removeReceiver(int receiverId) {
        this.receivers.removeReceiver(receiverId);
    }

    public void addReceivedFile(String fileName, int fileHashCode) {
        this.receivedFiles.addReceivedFile(fileName, fileHashCode);
    }

}
