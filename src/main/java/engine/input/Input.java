package engine.input;

import engine.common.Observer;
import engine.input.inputCombination.InputCombination;
import engine.input.inputCombination.InputCombinationFactory;

public interface Input {

    void addMouseListener(Observer observer);

    void addKeyboardListener(Observer observer);

    void addInputMouseListener(InputObserver observer);

    void addInputKeyboardListener(InputObserver observer);

    void removeMouseListener(Observer observer);

    void removeKeyboardListener(Observer observer);

    void removeInputMouseListener(InputObserver observer);

    void removeInputKeyboardListener(InputObserver observer);

    int getMouseX();

    int getMouseY();

    InputCombination getCurrentInputCombination();

    InputCombinationFactory getInputCombinationFactory();

    void initializeListeners();

    void resetMouseListener();

    void resetKeyboardListener();

}
