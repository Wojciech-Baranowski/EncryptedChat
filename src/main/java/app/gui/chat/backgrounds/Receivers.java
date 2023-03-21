package app.gui.chat.backgrounds;

import app.engine.display.Drawable;

import static app.Constants.MAX_CLIENTS;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class Receivers {

    private final Drawable receiversAll;
    private final Drawable[] receivers;

    public Receivers(Drawable background) {
        this.receiversAll = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 20,
                background.getY() + 70,
                200,
                30 * (MAX_CLIENTS - 1) + 4,
                2,
                "transparent",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.receiversAll, background);
        this.receivers = new Drawable[MAX_CLIENTS - 1];
        for (int i = 0; i < MAX_CLIENTS - 1; i++) {
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
