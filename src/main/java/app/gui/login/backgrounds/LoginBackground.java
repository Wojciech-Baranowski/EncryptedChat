package app.gui.login.backgrounds;

import app.engine.display.Drawable;
import lombok.Getter;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class LoginBackground {

    @Getter
    private final Drawable loginBackground;

    LoginBackground(Drawable background) {
        this.loginBackground = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 30,
                background.getY() + 30,
                355,
                500,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.loginBackground, background);
    }

}
