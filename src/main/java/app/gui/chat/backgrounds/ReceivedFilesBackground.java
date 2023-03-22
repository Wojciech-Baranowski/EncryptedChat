package app.gui.chat.backgrounds;

import app.engine.display.Drawable;

import static app.Constants.MAX_FILES_PER_PAGE;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class ReceivedFilesBackground {

    private final Drawable receivedFilesHeader;
    private final Drawable receivedFilesBodyAll;
    private final Drawable[] receivedFilesBody;

    public ReceivedFilesBackground(Drawable background) {
        this.receivedFilesHeader = getDisplay().getDrawableFactory().makeRectangle(
                background.getX() + 20,
                background.getY() + 330,
                760,
                50,
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receivedFilesHeader, background);
        this.receivedFilesBodyAll = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 20,
                background.getY() + 380,
                760,
                30 * (MAX_FILES_PER_PAGE) + 2,
                2,
                "transparent",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receivedFilesBodyAll, background);
        this.receivedFilesBody = new Drawable[MAX_FILES_PER_PAGE];
        for (int i = 0; i < MAX_FILES_PER_PAGE; i++) {
            this.receivedFilesBody[i] = getDisplay().getDrawableFactory().makeRectangle(
                    getReceivedFilesBodyAllBackground().getX() + 2,
                    getReceivedFilesBodyAllBackground().getY() + 30 * i,
                    756,
                    30,
                    (i % 2 == 0) ? "gray" : "lightBlue"
            );
            getScene().addObjectHigherThan(this.receivedFilesBody[i], this.receivedFilesBodyAll);
        }
    }

    public Drawable getReceivedFilesBodyAllBackground() {
        return this.receivedFilesBodyAll.getDrawable();
    }

    public Drawable getReceivedFilesHeaderBackground() {
        return this.receivedFilesHeader.getDrawable();
    }

}
