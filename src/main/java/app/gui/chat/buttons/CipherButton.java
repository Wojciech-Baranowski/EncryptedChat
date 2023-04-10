package app.gui.chat.buttons;

import app.encryption.aesCipher.CipherType;
import app.engine.button.radioButton.RadioButton;
import app.engine.button.radioButton.RadioButtonBundle;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.input.inputCombination.InputCombination;

import java.util.List;

import static app.encryption.aesCipher.CipherType.CBC;
import static app.encryption.aesCipher.CipherType.ECB;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

public class CipherButton {

    private RadioButtonBundle bundle;
    private RadioButton cbc;
    private RadioButton ebc;

    CipherButton(Drawable background) {
        Drawable cbcOffDrawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 10,
                background.getY() + 156,
                100,
                50,
                2,
                "lightBlue",
                "lighterBlue"
        );
        Drawable cbcOnDrawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 10,
                background.getY() + 156,
                100,
                50,
                2,
                "lightBlue",
                "yellow"
        );
        Drawable cbcText = getDisplay().getDrawableFactory().makeText(
                "CBC",
                cbcOffDrawable.getX() + 17,
                cbcOffDrawable.getY() + 10,
                "HBE32",
                "black"
        );
        Drawable ebcOffDrawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 120,
                background.getY() + 156,
                100,
                50,
                2,
                "lightBlue",
                "lighterBlue"
        );
        Drawable ebcOnDrawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                background.getX() + 120,
                background.getY() + 156,
                100,
                50,
                2,
                "lightBlue",
                "yellow"
        );
        Drawable ebcText = getDisplay().getDrawableFactory().makeText(
                "EBC",
                ebcOffDrawable.getX() + 19,
                ebcOffDrawable.getY() + 10,
                "HBE32",
                "black"
        );
        cbcOffDrawable = new DrawableComposition(cbcOffDrawable, cbcText);
        cbcOnDrawable = new DrawableComposition(cbcOnDrawable, cbcText);
        ebcOffDrawable = new DrawableComposition(ebcOffDrawable, ebcText);
        ebcOnDrawable = new DrawableComposition(ebcOnDrawable, ebcText);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        this.cbc = new RadioButton(cbcOffDrawable, cbcOnDrawable, activationCombination);
        this.ebc = new RadioButton(ebcOffDrawable, ebcOnDrawable, activationCombination);
        getScene().addOnHighest(this.cbc);
        getScene().addOnHighest(this.ebc);
        this.bundle = new RadioButtonBundle(List.of(this.cbc, this.ebc), false);
        this.bundle.update(this.cbc);
    }

    public CipherType getCipherType() {
        return (this.bundle.getSelectedRadioButtonIndex() == 0) ? CBC : ECB;
    }

}
