package app.gui.background;

import engine.display.Drawable;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class FileToSend {

    private final Drawable fileToSendHeader;
    private final Drawable fileToSendBody;

    public FileToSend(Drawable background) {
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
