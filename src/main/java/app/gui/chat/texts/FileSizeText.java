package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class FileSizeText {

    private Text fileSize;

    public FileSizeText(Drawable background) {
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
