package app.engine.input.inputCombination;

import app.engine.input.InputHandler;

public class SimpleInputCombination implements InputCombination {

    private final InputHandler inputHandler;
    private final InputElement element;

    public SimpleInputCombination(InputHandler inputHandler, InputElement element) {
        this.inputHandler = inputHandler;
        this.element = element;
    }

    @Override
    public boolean isActive() {
        return inputHandler.isActivated(element);
    }
}
