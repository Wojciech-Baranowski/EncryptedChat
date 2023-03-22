package app.gui.chat.texts;

import app.engine.display.Drawable;
import app.engine.display.text.Text;

import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.scene.SceneBean.getScene;

public class UploadInfoText {

    private final Drawable background;
    private final Text readyToUpload;
    private final Text success;
    private final Text error;
    private final Text progress;
    private Text currentInfo;

    public UploadInfoText(Drawable background) {
        this.background = background;
        this.readyToUpload = getDisplay().getDrawableFactory().makeText(
                "Ready to upload!",
                background.getX() + 14,
                background.getY() + 82,
                "HBE24",
                "green"
        );
        this.success = getDisplay().getDrawableFactory().makeText(
                "File uploaded successfully!",
                background.getX() + 14,
                background.getY() + 82,
                "HBE24",
                "green"
        );
        this.error = getDisplay().getDrawableFactory().makeText(
                "Error occurred while uploading!",
                background.getX() + 14,
                background.getY() + 82,
                "HBE24",
                "red"
        );
        this.progress = getDisplay().getDrawableFactory().makeText(
                "Progress: 0%",
                background.getX() + 14,
                background.getY() + 82,
                "HBE24",
                "darkYellow"
        );
        this.currentInfo = getDisplay().getDrawableFactory().makeText(
                "",
                background.getX() + 14,
                background.getY() + 82,
                "HBE24",
                "black"
        );
        getScene().addObjectHigherThan(this.currentInfo, background);
    }

    public void setCurrentUploadInfoAsReadyToUpload() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.readyToUpload;
        getScene().addObjectHigherThan(this.currentInfo, background);
    }

    public void setCurrentUploadInfoAsProgress(double percentage) {
        this.progress.setText("Progress: " + (((double) ((int) (percentage * 100))) / 100.0) + "%");
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.progress;
        getScene().addObjectHigherThan(this.currentInfo, background);
    }

    public void setCurrentUploadInfoAsSuccess() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.success;
        getScene().addObjectHigherThan(this.currentInfo, background);
    }

    public void setCurrentUploadInfoAsError() {
        getScene().removeObject(this.currentInfo);
        this.currentInfo = this.error;
        getScene().addObjectHigherThan(this.currentInfo, background);
    }

    public void setCurrentUploadInfoAsBlank() {
        getScene().removeObject(this.currentInfo);
    }

}
