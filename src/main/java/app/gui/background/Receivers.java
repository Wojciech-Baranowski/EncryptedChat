package app.gui.background;

import app.Constants;
import engine.display.Drawable;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class Receivers {

    private final Drawable receiversAll;
    private final Drawable[] receivers;

    public Receivers(Drawable background) {
        this.receiversAll = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 20,
                background.getY() + 70,
                200,
                30 * (Constants.MAX_CLIENTS - 1) + 4,
                2,
                "transparent",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receiversAll, background);
        this.receivers = new Drawable[Constants.MAX_CLIENTS - 1];
        for (int i = 0; i < Constants.MAX_CLIENTS - 1; i++) {
            this.receivers[i] = getDisplay().getDrawableFactory().makeRectangle(
                    getReceiversAllBackground().getX() + 2,
                    getReceiversAllBackground().getY() + 2 + 30 * i,
                    196,
                    30,
                    (i % 2 == 0) ? "gray" : "lightBlue"
            );
            getScene().addObjectHigherThan(this.receivers[i], this.receiversAll);
        }
    }

    public Drawable getReceiversAllBackground() {
        return this.receiversAll.getDrawable();
    }

}
