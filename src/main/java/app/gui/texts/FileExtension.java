package app.gui.texts;

import engine.display.Drawable;
import engine.display.text.Text;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class FileExtension {

    private Text fileExtension;

    public FileExtension(Drawable background) {
        this.fileExtension = getDisplay().getDrawableFactory().makeText(
                "Extension:",
                background.getX() + 14,
                background.getY() + 48,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.fileExtension, background);
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension.setText(fileExtension);
    }

    public void resetFileExtension() {
        this.fileExtension.setText("Extension:");
    }

}
