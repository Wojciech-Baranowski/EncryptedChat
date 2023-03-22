package app.gui.chat.backgrounds;

import app.engine.display.Drawable;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class UserNameBackground {

    private final Drawable userName;

    public UserNameBackground(Drawable background) {
        this.userName = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 20,
                background.getY() + 20,
                200,
                30,
                2,
                "gray",
                "lighterBlue"
        );
        getScene().addObjectHigherThan(this.userName, background);
    }

    public Drawable getUserNameBackground() {
        return this.userName.getDrawable();
    }

}
