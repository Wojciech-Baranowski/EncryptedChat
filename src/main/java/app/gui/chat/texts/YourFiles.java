package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class YourFiles {

    private final Text yourFiles;

    public YourFiles(Drawable background) {
        this.yourFiles = getDisplay().getDrawableFactory().makeText(
                "Your files: ",
                background.getX() + 14,
                background.getY() + 14,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.yourFiles, background);
    }

}
