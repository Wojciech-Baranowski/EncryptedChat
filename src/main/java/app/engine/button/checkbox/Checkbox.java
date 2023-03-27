package app.engine.button.checkbox;

import app.engine.common.Interactive;
import app.engine.common.Visual;
import app.engine.display.Drawable;
import app.engine.input.inputCombination.InputCombination;
import lombok.Getter;
import lombok.Setter;

public class Checkbox implements Visual, Interactive {

    protected final Drawable offDrawable;
    protected final Drawable onDrawable;
    protected final InputCombination activationCombination;
    @Getter
    @Setter
    protected boolean selected;

    public Checkbox(Drawable offDrawable, Drawable onDrawable, InputCombination activationCombination) {
        this.offDrawable = offDrawable;
        this.onDrawable = onDrawable;
        this.activationCombination = activationCombination;
        this.selected = false;
    }

    @Override
    public void update() {
        if (this.activationCombination == null || this.activationCombination.isActive()) {
            this.selected = !this.selected;
        }
    }

    @Override
    public Drawable getDrawable() {
        return this.selected ? this.onDrawable : this.offDrawable;
    }
}
