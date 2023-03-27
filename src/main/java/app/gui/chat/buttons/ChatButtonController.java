package app.gui.chat.buttons;

import common.CipherConfig;

import static app.gui.chat.backgrounds.ChatBackgroundController.getChatBackgroundController;

public class ChatButtonController {

    private static ChatButtonController chatButtonController;

    private ReceiversButton receiversButton;
    private ReceivedFilesOrTextsButton receivedFilesOrTextsButton;
    private AttachFileButton attachFileButton;
    private DetachFileButton detachFileButton;
    private SendFileButton sendFileButton;
    private CipherButton cipherButton;

    private ChatButtonController() {
        this.receiversButton = new ReceiversButton(getChatBackgroundController().getReceiversBackground());
        this.receivedFilesOrTextsButton = new ReceivedFilesOrTextsButton(getChatBackgroundController().getReceivedFilesBodyAllBackground());
        this.attachFileButton = new AttachFileButton(getChatBackgroundController().getFileToSendHeaderBackground());
        this.detachFileButton = new DetachFileButton(getChatBackgroundController().getFileToSendHeaderBackground());
        this.sendFileButton = new SendFileButton(getChatBackgroundController().getFileToSendBodyBackground());
        this.cipherButton = new CipherButton(getChatBackgroundController().getFileToSendBodyBackground());
    }

    public static ChatButtonController getChatButtonController() {
        if (chatButtonController == null) {
            chatButtonController = new ChatButtonController();
        }
        return chatButtonController;
    }

    public CipherConfig.CipherType getCipherType() {
        return this.cipherButton.getCipherType();
    }

    public Long getSelectedReceiverId() {
        return this.receiversButton.getSelectedReceiverId();
    }

    public void addReceiver(Long receiverId) {
        this.receiversButton.addReceiver(receiverId);
    }

    public void removeReceiver(Long receiverId) {
        this.receiversButton.removeReceiver(receiverId);
    }

    public void addReceivedFile(String fileName, int fileHashCode) {
        this.receivedFilesOrTextsButton.addReceivedFile(fileName, fileHashCode);
    }

    public void addReceivedText(String text) {
        this.receivedFilesOrTextsButton.addReceivedText(text);
    }

}
