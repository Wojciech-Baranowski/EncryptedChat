package app.engine.input;

import app.engine.input.inputCombination.InputElement;

public interface InputObserver {

    void update(InputElement inputElement);

}
