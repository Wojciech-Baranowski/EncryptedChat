package app.engine.input;

import app.engine.common.Observable;
import app.engine.common.Observer;
import app.engine.input.inputCombination.ActionType;
import app.engine.input.inputCombination.InputElement;
import app.engine.listener.ParallelThread;
import lombok.Getter;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;

public class InputHandler implements Observable, InputObservable {

    private static final int BUTTONS_NUMBER = 5;
    private static final int KEYS_NUMBER = 1 << 16;

    @Getter
    private final InputEventListener inputEventListener;
    private final List<Observer> observers;
    private final List<InputObserver> inputObservers;
    private boolean[] buttonsPressed;
    private boolean[] keysPressed;
    @Getter
    private int x;
    @Getter
    private int y;

    public InputHandler() {
        this.inputEventListener = new InputEventListener();
        buttonsPressed = new boolean[BUTTONS_NUMBER];
        keysPressed = new boolean[KEYS_NUMBER];
        Arrays.fill(buttonsPressed, false);
        Arrays.fill(keysPressed, false);
        observers = new LinkedList<>();
        inputObservers = new LinkedList<>();
        x = 0;
        y = 0;
        new ParallelThread<>(this.inputEventListener::listen, this::handleInputEvent);
    }

    public Set<InputElement> getActivatedInputElements() {
        Set<InputElement> currentCombination = new HashSet<>();
        for (int i = 0; i < BUTTONS_NUMBER; i++) {
            InputEvent inputEvent = InputElement.getMouseInputEventByKeycode(i);
            currentCombination.add(new InputElement(buttonsPressed[i] ? ActionType.DOWN : ActionType.UP, inputEvent));
        }
        for (int i = 0; i < KEYS_NUMBER; i++) {
            InputEvent inputEvent = InputElement.getKeyboardInputEventByKeycode(i);
            currentCombination.add(new InputElement(keysPressed[i] ? ActionType.DOWN : ActionType.UP, inputEvent));
        }
        return currentCombination;
    }

    public boolean isActivated(InputElement inputElement) {
        boolean activated = false;
        if (inputElement.getInputEvent() instanceof MouseEvent) {
            int buttonCode = ((MouseEvent) inputElement.getInputEvent()).getButton();
            activated = switch (inputElement.getActionType()) {
                case UP -> !buttonsPressed[buttonCode];
                case DOWN -> buttonsPressed[buttonCode];
            };
        }
        if (inputElement.getInputEvent() instanceof KeyEvent) {
            int keyCode = ((KeyEvent) inputElement.getInputEvent()).getKeyCode();
            activated = switch (inputElement.getActionType()) {
                case UP -> !keysPressed[keyCode];
                case DOWN -> keysPressed[keyCode];
            };
        }
        return activated;
    }

    public void reset() {
        buttonsPressed = new boolean[BUTTONS_NUMBER];
        keysPressed = new boolean[KEYS_NUMBER];
        Arrays.fill(buttonsPressed, false);
        Arrays.fill(keysPressed, false);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void attach(InputObserver observer) {
        inputObservers.add(observer);
    }

    @Override
    public void detach(InputObserver observer) {
        inputObservers.remove(observer);
    }

    @Override
    public void notifyObservers(InputElement inputElement) {
        for (InputObserver observer : inputObservers) {
            observer.update(inputElement);
        }
    }

    private void handleInputEvent(InputEvent inputEvent) {
        switch (inputEvent.getID()) {
            case MouseEvent.MOUSE_MOVED -> handleMouseMovedInput((MouseEvent) inputEvent);
            case KeyEvent.KEY_PRESSED -> handleKeyPressedInput((KeyEvent) inputEvent);
            case KeyEvent.KEY_RELEASED -> handleKeyReleasedInput((KeyEvent) inputEvent);
            case MouseEvent.MOUSE_PRESSED -> handleButtonPressedInput((MouseEvent) inputEvent);
            case MouseEvent.MOUSE_RELEASED -> handleButtonReleasedInput((MouseEvent) inputEvent);
        }
    }

    private void handleKeyPressedInput(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (!keysPressed[keyCode]) {
            keysPressed[keyCode] = true;
            notifyObservers();
            notifyObservers(new InputElement(ActionType.DOWN, InputElement.getKeyboardInputEventByKeycode(keyCode)));
        }
    }

    private void handleKeyReleasedInput(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        keysPressed[keyCode] = false;
        notifyObservers();
        notifyObservers(new InputElement(ActionType.UP, InputElement.getKeyboardInputEventByKeycode(keyCode)));
    }

    private void handleButtonPressedInput(MouseEvent mouseEvent) {
        int buttonCode = mouseEvent.getButton();
        buttonsPressed[buttonCode] = true;
        notifyObservers();
    }

    private void handleButtonReleasedInput(MouseEvent mouseEvent) {
        int buttonCode = mouseEvent.getButton();
        buttonsPressed[buttonCode] = false;
        notifyObservers();
    }

    private void handleMouseMovedInput(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        notifyObservers();
    }

}
