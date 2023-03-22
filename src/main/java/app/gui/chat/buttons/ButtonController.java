package app.gui.chat.buttons;

import static app.gui.chat.backgrounds.BackgroundController.getBackgroundController;

public class ButtonController {

    private static ButtonController buttonController;

    private ReceiversButton receiversButton;
    private ReceivedFilesOrTextsButton receivedFilesOrTextsButton;
    private AttachFileButton attachFileButton;
    private DetachFileButton detachFileButton;
    private SendFileButton sendFileButton;
    private CipherButton cipherButton;

    private ButtonController() {
        this.receiversButton = new ReceiversButton(getBackgroundController().getReceiversBackground());
        this.receivedFilesOrTextsButton = new ReceivedFilesOrTextsButton(getBackgroundController().getReceivedFilesBodyAllBackground());
        this.attachFileButton = new AttachFileButton(getBackgroundController().getFileToSendHeaderBackground());
        this.detachFileButton = new DetachFileButton(getBackgroundController().getFileToSendHeaderBackground());
        this.sendFileButton = new SendFileButton(getBackgroundController().getFileToSendBodyBackground());
        this.cipherButton = new CipherButton(getBackgroundController().getFileToSendBodyBackground());
    }

    public static ButtonController getButtonController() {
        if (buttonController == null) {
            buttonController = new ButtonController();
        }
        return buttonController;
    }

    public CipherType getCipherType() {
        return this.cipherButton.getCipherType();
    }

    public Integer getSelectedReceiverId() {
        return this.receiversButton.getSelectedReceiverId();
    }

    public void addReceiver(int receiverId) {
        this.receiversButton.addReceiver(receiverId);
    }

    public void removeReceiver(int receiverId) {
        this.receiversButton.removeReceiver(receiverId);
    }

    public void addReceivedFile(String fileName, int fileHashCode) {
        this.receivedFilesOrTextsButton.addReceivedFile(fileName, fileHashCode);
    }

    public void addReceivedText(String text) {
        this.receivedFilesOrTextsButton.addReceivedText(text);
    }

}
