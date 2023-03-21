package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class UserName {

    private Text userName;

    public UserName(Drawable background) {
        this.userName = getDisplay().getDrawableFactory().makeText(
                "User:",
                background.getX() + 6,
                background.getY() + 3,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.userName, background);
    }

    public void setUserName(String userName) {
        this.userName.setText("User: " + userName);
    }

}
