package app.gui.chat.backgrounds;

import app.engine.display.Drawable;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class FileToSendBackground {

    private final Drawable fileToSendHeader;
    private final Drawable fileToSendBody;

    public FileToSendBackground(Drawable background) {
        this.fileToSendHeader = getDisplay().getDrawableFactory().makeRectangle(
                background.getX() + 320,
                background.getY() + 20,
                460,
                50,
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.fileToSendHeader, background);
        this.fileToSendBody = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 320,
                background.getY() + 68,
                460,
                216,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.fileToSendBody, background);
    }

    public Drawable getFileToSendHeaderBackground() {
        return this.fileToSendHeader.getDrawable();
    }

    public Drawable getFileToSendBodyBackground() {
        return this.fileToSendBody.getDrawable();
    }

}
