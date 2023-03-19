package app.login.backgrounds;

import engine.display.Drawable;
import lombok.Getter;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class LoginBackground {

    @Getter
    private final Drawable loginBackground;

    public LoginBackground(Drawable background) {
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
