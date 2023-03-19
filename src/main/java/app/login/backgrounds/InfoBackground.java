package app.login.backgrounds;

import engine.display.Drawable;
import lombok.Getter;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class InfoBackground {

    @Getter
    private final Drawable infoBackground;

    public InfoBackground(Drawable background) {
        this.infoBackground = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 30,
                background.getY() + 560,
                740,
                50,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.infoBackground, background);
    }

}
