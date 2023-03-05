package app.gui.texts;

import engine.display.Drawable;
import engine.display.text.Text;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

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
