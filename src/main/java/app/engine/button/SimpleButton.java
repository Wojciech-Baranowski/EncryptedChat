package app.engine.button;

import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.input.inputCombination.InputCombination;

public class SimpleButton extends ComplexButton {

    public SimpleButton(Drawable drawable, InputCombination activationCombination, Command action) {
        super(drawable, new InputCombination[]{activationCombination}, new Command[]{action});
    }

    @Override
    public void update() {
        InputCombination activationCombination = this.actions.keySet()
                .stream()
                .findFirst()
                .orElse(null);
        if (activationCombination == null || activationCombination.isActive()) {
            this.actions.get(activationCombination).execute();
        }
    }

    @Override
    public Drawable getDrawable() {
        return this.drawable;
    }
}
