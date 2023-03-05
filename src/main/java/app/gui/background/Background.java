package app.gui.background;

import app.Constants;
import engine.display.Drawable;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class Background {

    private final Drawable background;
    private final Drawable name;
    private final Drawable receiversAll;
    private final Drawable[] receivers;
    private final Drawable fileToSendHeader;
    private final Drawable fileToSendBody;
    private final Drawable receivedFilesHeader;
    private final Drawable receivedFilesBodyAll;
    private final Drawable[] receivedFilesBody;

    public Background() {
        this.background = getDisplay().getDrawableFactory().makeFramedRectangle(
                0,
                0,
                800,
                640,
                2,
                "blue",
                "lighterBlue"
        );
        getScene().addOnHighest(this.background);
        this.name = getDisplay().getDrawableFactory().makeFramedRectangle(
                20,
                20,
                200,
                30,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.name, this.background);
        this.receiversAll = getDisplay().getDrawableFactory().makeFramedRectangle(
                20,
                70,
                200,
                30 * (Constants.MAX_CLIENTS - 1) + 4,
                2,
                "transparent",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receiversAll, this.background);
        this.receivers = new Drawable[Constants.MAX_CLIENTS - 1];
        for (int i = 0; i < Constants.MAX_CLIENTS - 1; i++) {
            this.receivers[i] = getDisplay().getDrawableFactory().makeRectangle(
                    22,
                    72 + 30 * i,
                    196,
                    30,
                    (i % 2 == 0) ? "gray" : "lightBlue"
            );
            getScene().addObjectHigherThan(this.receivers[i], this.receiversAll);
        }
        this.fileToSendHeader = getDisplay().getDrawableFactory().makeRectangle(
                320,
                20,
                460,
                50,
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.fileToSendHeader, this.background);
        this.fileToSendBody = getDisplay().getDrawableFactory().makeFramedRectangle(
                320,
                68,
                460,
                216,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.fileToSendBody, this.background);
        this.receivedFilesHeader = getDisplay().getDrawableFactory().makeRectangle(
                20,
                306,
                760,
                50,
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receivedFilesHeader, this.background);
        this.receivedFilesBodyAll = getDisplay().getDrawableFactory().makeFramedRectangle(
                20,
                354,
                760,
                30 * (Constants.MAX_FILES_PER_PAGE) + 4,
                2,
                "transparent",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receivedFilesBodyAll, this.background);
        this.receivedFilesBody = new Drawable[Constants.MAX_FILES_PER_PAGE];
        for (int i = 0; i < Constants.MAX_FILES_PER_PAGE; i++) {
            this.receivedFilesBody[i] = getDisplay().getDrawableFactory().makeRectangle(
                    22,
                    356 + 30 * i,
                    756,
                    30,
                    (i % 2 == 0) ? "gray" : "lightBlue"
            );
            getScene().addObjectHigherThan(this.receivedFilesBody[i], this.receivedFilesBodyAll);
        }
    }

}
