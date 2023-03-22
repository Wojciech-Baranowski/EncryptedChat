package app.gui.chat.backgrounds;

import app.engine.display.Drawable;

public class BackgroundController {

    private static BackgroundController backgroundController;

    private final Background background;
    private final UserNameBackground userNameBackground;
    private final ReceiversBackground receiversBackground;
    private final FileToSendBackground fileToSendBackground;
    private final ReceivedFilesBackground receivedFilesBackground;

    private BackgroundController() {
        this.background = new Background();
        this.userNameBackground = new UserNameBackground(this.getBackground());
        this.receiversBackground = new ReceiversBackground(this.getBackground());
        this.fileToSendBackground = new FileToSendBackground(this.getBackground());
        this.receivedFilesBackground = new ReceivedFilesBackground(this.getBackground());
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
        return this.userNameBackground.getUserNameBackground();
    }

    public Drawable getReceiversBackground() {
        return this.receiversBackground.getReceiversAllBackground();
    }

    public Drawable getFileToSendHeaderBackground() {
        return this.fileToSendBackground.getFileToSendHeaderBackground();
    }

    public Drawable getFileToSendBodyBackground() {
        return this.fileToSendBackground.getFileToSendBodyBackground();
    }

    public Drawable getReceivedFilesHeaderBackground() {
        return this.receivedFilesBackground.getReceivedFilesHeaderBackground();
    }

    public Drawable getReceivedFilesBodyAllBackground() {
        return this.receivedFilesBackground.getReceivedFilesBodyAllBackground();
    }

}
