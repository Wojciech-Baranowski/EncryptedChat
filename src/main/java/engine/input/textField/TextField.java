package engine.input.textField;

import engine.common.Interactive;
import engine.common.Visual;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.display.text.Text;
import engine.input.InputObserver;
import engine.input.inputCombination.InputElement;
import lombok.Getter;

import java.awt.event.KeyEvent;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.input.inputCombination.ActionType.DOWN;

public class TextField implements Visual, Interactive, InputObserver {

    private static TextField currentlyActive;
    private Drawable background;
    private Text text;
    private DrawableComposition drawable;
    @Getter
    private String content;
    private boolean shift;

    public TextField(int x, int y, int w, int h, String backgroundColor, String font, String fontColor) {
        this.background = getDisplay().getDrawableFactory().makeRectangle(x, y, w, h, backgroundColor);
        this.text = getDisplay().getDrawableFactory().makeText("", x, y, font, fontColor);
        this.content = "";
        this.drawable = new DrawableComposition(this.background, this.text);
        this.shift = false;
    }

    @Override
    public void update() {
        if (getInput().getInputCombinationFactory().makeLmbCombination().isActive()) {
            if (currentlyActive != null) {
                getInput().removeInputKeyboardListener(currentlyActive);
            }
            currentlyActive = this;
            getInput().addInputKeyboardListener(this);
        }
        if (getInput().getInputCombinationFactory().makeSimpleInputCombination(DOWN,
                InputElement.getKeyboardInputEventByKeycode(KeyEvent.VK_ESCAPE)).isActive()) {
            if (currentlyActive != null) {
                getInput().removeInputKeyboardListener(currentlyActive);
            }
            currentlyActive = null;
        }
    }

    @Override
    public void update(InputElement inputElement) {
        if (inputElement.getActionType() == DOWN) {
            updateContent(inputElement);
            this.text.setText(this.content);
            this.drawable.update(new DrawableComposition(this.background, this.text));
        }
    }

    @Override
    public Drawable getDrawable() {
        return this.drawable;
    }

    private void updateContent(InputElement inputElement) {
        this.content = this.content + (char) inputElement.getKeyCode();
    }

}
