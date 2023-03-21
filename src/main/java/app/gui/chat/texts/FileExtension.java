package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class FileExtension {

    private Text fileExtension;

    public FileExtension(Drawable background) {
        this.fileExtension = getDisplay().getDrawableFactory().makeText(
                "",
                background.getX() + 14,
                background.getY() + 48,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.fileExtension, background);
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension.setText("Extension: " + fileExtension);
    }

    public void resetFileExtension() {
        this.fileExtension.setText("");
    }

}
