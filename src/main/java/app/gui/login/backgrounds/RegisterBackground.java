package app.gui.login.backgrounds;

import app.engine.display.Drawable;
import lombok.Getter;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class RegisterBackground {

    @Getter
    private final Drawable registerBackground;

    public RegisterBackground(Drawable background) {
        this.registerBackground = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 415,
                background.getY() + 30,
                355,
                500,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.registerBackground, background);
    }

}
