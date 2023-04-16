package app.gui.login.backgrounds;

import app.engine.display.Drawable;
import lombok.Getter;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class InfoBackground {

    @Getter
    private final Drawable infoBackground;

    InfoBackground(Drawable background) {
        this.infoBackground = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 30,
                background.getY() + 570,
                740,
                50,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.infoBackground, background);
    }

}
