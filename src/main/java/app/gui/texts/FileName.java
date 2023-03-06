package app.gui.texts;

import engine.display.Drawable;
import engine.display.text.Text;

import static engine.display.DisplayBean.getDisplay;
import static engine.scene.SceneBean.getScene;

public class FileName {

    private Text fileName;

    public FileName(Drawable background) {
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
