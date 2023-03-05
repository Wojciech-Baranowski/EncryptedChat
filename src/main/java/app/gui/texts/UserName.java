package app.gui.texts;

import engine.display.Drawable;
import engine.display.text.Text;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class UserName {

    private Text userName;

    public UserName(Drawable background) {
        this.userName = getDisplay().getDrawableFactory().makeText(
                "User 1",
                background.getX() + 6,
                background.getY() + 3,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.userName, background);
    }

    public void setText(String text) {
        this.userName.setText(text);
    }

}
