package app.gui.buttons;

import static app.gui.background.BackgroundController.getBackgroundController;

public class ButtonController {

    private static ButtonController buttonController;

    private Receivers receivers;
    private ReceivedFiles receivedFiles;
    private AddFile addFile;
    private SendFile sendFile;

    private ButtonController() {
        this.receivers = new Receivers(getBackgroundController().getReceiversBackground());
        this.receivedFiles = new ReceivedFiles(getBackgroundController().getReceivedFilesBodyAllBackground());
        this.addFile = new AddFile(getBackgroundController().getFileToSendHeaderBackground());
        this.sendFile = new SendFile(getBackgroundController().getFileToSendBodyBackground());
        receivers.addReceiver(4);
        receivers.addReceiver(2);
        receivers.addReceiver(0);
        receivedFiles.addFile("JP");
        receivedFiles.addFile("2G");
        receivedFiles.addFile("MD");
    }

    public static ButtonController getButtonController() {
        if (buttonController == null) {
            buttonController = new ButtonController();
        }
        return buttonController;
    }

    public void addReceiver(int receiverId) {
        this.receivers.addReceiver(receiverId);
    }

    public void removeReceiver(int receiverId) {
        this.receivers.removeReceiver(receiverId);
    }

    public void addReceivedFile(String fileName) {
        this.receivedFiles.addFile(fileName);
    }

}
