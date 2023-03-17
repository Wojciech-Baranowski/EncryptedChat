package engine.input;

import engine.common.Observable;
import engine.common.Observer;
import engine.input.inputCombination.InputElement;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import static engine.input.inputCombination.ActionType.DOWN;
import static engine.input.inputCombination.ActionType.UP;

public class KeyboardListener implements Observable, InputObservable, KeyListener {

    private static final int KEY_NUMBER = 256;
    private boolean[] pressed;
    private final List<Observer> observers;
    private final List<InputObserver> inputObservers;

    KeyboardListener() {
        pressed = new boolean[KEY_NUMBER];
        Arrays.fill(pressed, false);
        observers = new LinkedList<>();
        inputObservers = new LinkedList<>();
    }

    public Set<InputElement> getActivatedInputElements() {
        Set<InputElement> currentCombination = new HashSet<>();
        for (int i = 0; i < KEY_NUMBER; i++) {
            InputEvent inputEvent = InputElement.getKeyboardInputEventByKeycode(i);
            currentCombination.add(new InputElement(pressed[i] ? DOWN : UP, inputEvent));
        }
        return currentCombination;
    }

    public boolean isActivated(InputElement inputElement) {
        int keyCode = ((KeyEvent) inputElement.getInputEvent()).getKeyCode();
        return switch (inputElement.getActionType()) {
            case UP -> !pressed[keyCode];
            case DOWN -> pressed[keyCode];
        };
    }

    public void reset() {
        pressed = new boolean[KEY_NUMBER];
        Arrays.fill(pressed, false);
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

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!pressed[keyCode]) {
            pressed[keyCode] = true;
            notifyObservers();
            notifyObservers(new InputElement(DOWN, InputElement.getKeyboardInputEventByKeycode(keyCode)));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressed[keyCode] = false;
        notifyObservers();
        notifyObservers(new InputElement(UP, InputElement.getKeyboardInputEventByKeycode(keyCode)));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
