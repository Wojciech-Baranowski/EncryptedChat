package app.engine.button.checkbox;

import app.engine.common.Command;
import app.engine.display.Drawable;
import app.engine.input.inputCombination.InputCombination;
import lombok.Getter;

public class CommandCheckbox extends Checkbox {

    @Getter
    private final Command onCommand;
    @Getter 
    private final Command offCommand;

    public CommandCheckbox(Drawable offDrawable, Drawable onDrawable, InputCombination activationCombination, Command onCommand, Command offCommand) {
        super(offDrawable, onDrawable, activationCombination);
        this.onCommand = onCommand;
        this.offCommand = offCommand;
    }

    @Override
    public void update() {
        if (this.activationCombination == null || this.activationCombination.isActive()) {
            if (!selected) {
                this.selected = true;
                if (this.onCommand != null) {
                    this.onCommand.execute();
                }
            } else {
                this.selected = false;
                if (this.offCommand != null) {
                    this.offCommand.execute();
                }
            }
        }
    }
}
