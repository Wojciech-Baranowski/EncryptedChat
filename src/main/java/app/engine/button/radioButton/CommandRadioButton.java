package app.engine.button.radioButton;

import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.input.inputCombination.InputCombination;

public class CommandRadioButton extends RadioButton {

    private final Command command;

    public CommandRadioButton(Drawable offDrawable, Drawable onDrawable, InputCombination activationCombination,
                              Command command) {
        super(offDrawable, onDrawable, activationCombination);
        this.command = command;
    }

    @Override
    public void update() {
        if (activationCombination == null || activationCombination.isActive()) {
            if (radioButtonBundle.getSelectedRadioButton() != this) {
                command.execute();
            }
            radioButtonBundle.update(this);
        }
    }

}
