package app.gui.texts;

import engine.display.Drawable;
import engine.display.text.Text;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class FileSize {

    private Text fileSize;

    public FileSize(Drawable background) {
        this.fileSize = getDisplay().getDrawableFactory().makeText(
                "",
                background.getX() + 14,
                background.getY() + 14,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.fileSize, background);
    }

    public void setFileSize(String text) {
        this.fileSize.setText("Size: " + text + "B");
    }

    public void resetFileSize() {
        this.fileSize.setText("");
    }

}
