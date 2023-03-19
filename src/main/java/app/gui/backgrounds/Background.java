package app.gui.backgrounds;

import engine.display.Drawable;
import lombok.Getter;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class Background {

    @Getter
    private final Drawable background;

    public Background() {
        this.background = getDisplay().getDrawableFactory().makeFramedRectangle(
                0,
                0,
                800,
                640,
                2,
                "blue",
                "white"
        );
        getScene().addOnHighest(this.background);
    }

}
