package engine.input;

import engine.common.Observable;
import engine.common.Observer;
import engine.input.inputCombination.ActionType;
import engine.input.inputCombination.InputElement;
import lombok.Getter;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class MouseListener implements Observable, InputObservable, java.awt.event.MouseListener, MouseMotionListener {

    private static final int BUTTONS_NUMBER = 5;
    private boolean[] pressed;
    private final List<Observer> observers;
    private final List<InputObserver> inputObservers;
    @Getter
    private int x;
    @Getter
    private int y;

    MouseListener() {
        pressed = new boolean[BUTTONS_NUMBER];
        Arrays.fill(pressed, false);
        observers = new LinkedList<>();
        inputObservers = new LinkedList<>();
        x = 0;
        y = 0;
    }

    public Set<InputElement> getActivatedInputElements() {
        Set<InputElement> currentCombination = new HashSet<>();
        for (int i = 0; i < BUTTONS_NUMBER; i++) {
            InputEvent inputEvent = InputElement.getMouseInputEventByKeycode(i);
            currentCombination.add(new InputElement(pressed[i] ? ActionType.DOWN : ActionType.UP, inputEvent));
        }
        return currentCombination;
    }

    public boolean isActivated(InputElement inputElement) {
        int buttonCode = ((MouseEvent) inputElement.getInputEvent()).getButton();
        return switch (inputElement.getActionType()) {
            case UP -> !pressed[buttonCode];
            case DOWN -> pressed[buttonCode];
        };
    }

    public void reset() {
        pressed = new boolean[BUTTONS_NUMBER];
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
    public void mousePressed(MouseEvent e) {
        int buttonCode = e.getButton();
        pressed[buttonCode] = true;
        notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int buttonCode = e.getButton();
        pressed[buttonCode] = false;
        notifyObservers();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        notifyObservers();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
