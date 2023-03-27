package app.engine.input;

import app.engine.common.Observer;
import app.engine.display.Display;
import app.engine.display.DisplayBean;
import app.engine.input.inputCombination.ComplexInputCombination;
import app.engine.input.inputCombination.InputCombination;
import app.engine.input.inputCombination.InputCombinationFactory;
import app.engine.input.inputCombination.InputElement;

import java.util.Set;

public class InputBean implements Input {

    private static InputBean input;
    private final InputHandler inputHandler;
    private final InputCombinationFactory inputCombinationFactory;

    private InputBean() {
        this.inputHandler = new InputHandler();
        this.inputCombinationFactory = new InputCombinationFactory(inputHandler);
    }

    public static Input getInput() {
        if (input == null) {
            input = new InputBean();
        }
        return input;
    }

    @Override
    public void addInputListener(Observer observer) {
        this.inputHandler.attach(observer);
    }

    @Override
    public void addInputContentListener(InputObserver observer) {
        this.inputHandler.attach(observer);
    }

    @Override
    public void removeInputListener(Observer observer) {
        this.inputHandler.detach(observer);
    }

    @Override
    public void removeInputContentListener(InputObserver observer) {
        this.inputHandler.detach(observer);
    }

    @Override
    public int getMouseX() {
        return this.inputHandler.getX();
    }

    @Override
    public int getMouseY() {
        return this.inputHandler.getY();
    }

    @Override
    public InputCombination getCurrentInputCombination() {
        Set<InputElement> inputElements = this.inputHandler.getActivatedInputElements();
        return new ComplexInputCombination(this.inputHandler, inputElements);
    }

    @Override
    public InputCombinationFactory getInputCombinationFactory() {
        return this.inputCombinationFactory;
    }

    @Override
    public void initializeInputListener() {
        Display display = DisplayBean.getDisplay();
        display.addWindowListener(this.inputHandler.getInputEventListener());
    }

    @Override
    public void resetInputListener() {
        this.inputHandler.reset();
    }
}
