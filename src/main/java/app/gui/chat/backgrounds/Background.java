package app.gui.chat.backgrounds;

import app.engine.display.Drawable;
import lombok.Getter;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

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
