package app.engine.input.inputCombination;

import app.engine.input.InputHandler;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.min;

public class InputCombinationFactory {

    private final InputHandler inputHandler;

    public InputCombinationFactory(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public InputCombination makeComplexInputCombination(ActionType[] actionTypes, InputEvent[] inputEvents) {
        Set<InputElement> inputElements = new HashSet<>();
        for (int i = 0; i < min(actionTypes.length, inputEvents.length); i++) {
            InputElement inputElement = new InputElement(actionTypes[i], inputEvents[i]);
            inputElements.add(inputElement);
        }
        return new ComplexInputCombination(inputHandler, inputElements);
    }

    public InputCombination makeSimpleInputCombination(ActionType actionType, InputEvent inputEvent) {
        InputElement inputElement = new InputElement(actionType, inputEvent);
        return new SimpleInputCombination(inputHandler, inputElement);
    }

    public InputCombination makeLmbCombination() {
        InputEvent inputEvent = InputElement.getMouseInputEventByKeycode(MouseEvent.BUTTON1);
        InputElement inputElement = new InputElement(ActionType.DOWN, inputEvent);
        return new SimpleInputCombination(inputHandler, inputElement);
    }

    public InputCombination makeEscapeCombination() {
        InputEvent inputEvent = InputElement.getKeyboardInputEventByKeycode(KeyEvent.VK_ESCAPE);
        InputElement inputElement = new InputElement(ActionType.DOWN, inputEvent);
        return new SimpleInputCombination(inputHandler, inputElement);
    }

    public InputCombination makeNullCombination() {
        return new SimpleInputCombination(inputHandler, null);
    }

}
