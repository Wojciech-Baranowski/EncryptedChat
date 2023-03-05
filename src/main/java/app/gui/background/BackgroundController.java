package app.gui.background;

import engine.display.Drawable;

public class BackgroundController {

    private static BackgroundController backgroundController;

    private final Background background;
    private final UserName userName;
    private final Receivers receivers;
    private final FileToSend fileToSend;
    private final ReceivedFiles receivedFiles;

    private BackgroundController() {
        this.background = new Background();
        this.userName = new UserName(this.getBackground());
        this.receivers = new Receivers(this.getBackground());
        this.fileToSend = new FileToSend(this.getBackground());
        this.receivedFiles = new ReceivedFiles(this.getBackground());
    }

    public static BackgroundController getBackgroundController() {
        if (backgroundController == null) {
            backgroundController = new BackgroundController();
        }
        return backgroundController;
    }

    public Drawable getBackground() {
        return this.background.getBackground();
    }

    public Drawable getUserNameBackground() {
        return this.userName.getUserNameBackground();
    }

    public Drawable getFileToSendHeaderBackground() {
        return this.fileToSend.getFileToSendHeaderBackground();
    }

    public Drawable getFileToSendBodyBackground() {
        return this.fileToSend.getFileToSendBodyBackground();
    }

    public Drawable getReceivedFilesHeaderBackground() {
        return this.receivedFiles.getReceivedFilesHeaderBackground();
    }

}
