package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class YourFilesText {

    private final Text yourFiles;

    public YourFilesText(Drawable background) {
        this.yourFiles = getDisplay().getDrawableFactory().makeText(
                "Received files and messages: ",
                background.getX() + 14,
                background.getY() + 14,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.yourFiles, background);
    }

}
