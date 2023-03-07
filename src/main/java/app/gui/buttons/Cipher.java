package app.gui.buttons;

import app.CipherType;
import engine.button.radioButton.RadioButton;
import engine.button.radioButton.RadioButtonBundle;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.input.inputCombination.InputCombination;

import java.util.List;

import static app.CipherType.CBC;
import static app.CipherType.EBC;
import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.scene.SceneBean.getScene;

public class Cipher {

    private RadioButtonBundle bundle;
    private RadioButton cbc;
    private RadioButton ebc;

    public Cipher(Drawable background) {
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
                cbcOffDrawable.getX() + 5,
                cbcOffDrawable.getY() + 5,
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
                ebcOffDrawable.getX() + 5,
                ebcOffDrawable.getY() + 5,
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
        return (this.bundle.getSelectedRadioButtonIndex() == 0) ? CBC : EBC;
    }

}
