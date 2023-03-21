package app.engine.button.radioButton;

import app.engine.common.Interactive;
import app.engine.common.Visual;
import app.engine.display.Drawable;
import app.engine.input.inputCombination.InputCombination;
import lombok.Getter;
import lombok.Setter;

public class RadioButton implements Visual, Interactive {

    private final Drawable offDrawable;
    private final Drawable onDrawable;
    protected final InputCombination activationCombination;
    @Setter
    protected RadioButtonBundle radioButtonBundle;
    @Getter
    @Setter
    private boolean selected;

    public RadioButton(Drawable offDrawable, Drawable onDrawable, InputCombination activationCombination) {
        this.offDrawable = offDrawable;
        this.onDrawable = onDrawable;
        this.activationCombination = activationCombination;
        this.selected = false;
    }


    @Override
    public void update() {
        if (activationCombination == null || activationCombination.isActive()) {
            radioButtonBundle.update(this);
        }
    }

    @Override
    public Drawable getDrawable() {
        return selected ? onDrawable : offDrawable;
    }
}
