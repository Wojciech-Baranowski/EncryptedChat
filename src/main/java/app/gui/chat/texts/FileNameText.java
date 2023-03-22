package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class FileNameText {

    private Text fileName;

    public FileNameText(Drawable background) {
        this.fileName = getDisplay().getDrawableFactory().makeText(
                "No file selected.",
                background.getX() + 14,
                background.getY() + 14,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.fileName, background);
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public void resetFileName() {
        this.fileName.setText("No file selected.");
    }

}
