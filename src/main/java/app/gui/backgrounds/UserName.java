package app.gui.backgrounds;

import engine.display.Drawable;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class UserName {

    private final Drawable userName;

    public UserName(Drawable background) {
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
