package app.engine.input.inputCombination;

import app.engine.input.InputHandler;

import java.util.Set;

public class ComplexInputCombination implements InputCombination {

    private final InputHandler inputHandler;
    private final Set<InputElement> elements;

    public ComplexInputCombination(InputHandler inputHandler, Set<InputElement> elements) {
        this.inputHandler = inputHandler;
        this.elements = elements;
    }

    @Override
    public boolean isActive() {
        for (InputElement element : elements) {
            if (!inputHandler.isActivated(element)) {
                return false;
            }
        }
        return true;
    }
}
