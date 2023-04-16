package app.gui.login.backgrounds;

import app.engine.display.Drawable;
import lombok.Getter;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class KeyPasswordBackground {

    @Getter
    private final Drawable keyPasswordBackground;

    KeyPasswordBackground(Drawable background) {
        this.keyPasswordBackground = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 415,
                background.getY() + 21,
                355,
                54,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.keyPasswordBackground, background);
    }

}
