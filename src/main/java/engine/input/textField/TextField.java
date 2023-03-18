package engine.input.textField;

import engine.assets.font.Font;
import engine.common.Interactive;
import engine.common.Visual;
import engine.display.Drawable;
import engine.display.DrawableComposition;
import engine.display.text.Text;
import engine.input.InputObserver;
import engine.input.inputCombination.InputElement;

import java.awt.event.KeyEvent;

import static engine.display.DisplayBean.getDisplay;
import static engine.input.InputBean.getInput;
import static engine.input.inputCombination.ActionType.DOWN;

public class TextField implements Visual, Interactive, InputObserver {

    private static TextField currentlyActive;
    private Drawable background;
    private Text text;
    private DrawableComposition drawable;
    private String content;
    private String fontName;
    private boolean shift;

    public TextField(int x, int y, int w, int h, String backgroundColor, String font, String fontColor) {
        this.background = getDisplay().getDrawableFactory().makeRectangle(x, y, w, h, backgroundColor);
        this.text = getDisplay().getDrawableFactory().makeText("", x, y, font, fontColor);
        this.content = "";
        this.fontName = font;
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
        if (getInput().getInputCombinationFactory().makeEscapeCombination().isActive()) {
            if (currentlyActive != null) {
                getInput().removeInputKeyboardListener(currentlyActive);
            }
            currentlyActive = null;
        }
    }

    @Override
    public void update(InputElement inputElement) {
        updateContent(inputElement);
        if (inputElement.getActionType() == DOWN) {
            this.text.setText(this.content);
            if (this.text.getW() > this.background.getW()) {
                foldText();
            }
            if (this.text.getH() > this.background.getH()) {
                trimText();
            }
            this.drawable.update(new DrawableComposition(this.background, this.text));
        }
    }

    @Override
    public Drawable getDrawable() {
        return this.drawable;
    }

    public String getContent() {
        return this.content.replaceAll("\n", " ").replaceAll(" \\+", " ").trim();
    }

    private void updateContent(InputElement inputElement) {
        if (inputElement.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = (inputElement.getActionType().equals(DOWN));
        } else if (inputElement.getActionType().equals(DOWN)) {
            if (inputElement.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                delete(shift);
            } else if (Font.getSymbolBasedOnKeycode(inputElement.getKeyCode(), this.shift) != 0) {
                this.content = this.content + (char) Font.getSymbolBasedOnKeycode(inputElement.getKeyCode(), this.shift);
            }
        }
    }

    private void foldText() {
        this.content = new StringBuilder(this.content)
                .insert(this.content.length() - 1, '\n')
                .toString();
        this.text.setText(this.content);
    }

    private void trimText() {
        boolean prevToLastCharIsEnter = this.content.charAt(this.content.length() - 2) == '\n';
        this.content = this.content.substring(0, this.content.length() - (prevToLastCharIsEnter ? 2 : 1));
        this.text.setText(this.content);
    }

    private void delete(boolean shift) {
        if (shift) {
            this.content = this.content.trim();
            int indexOfLastSpaceOrEnter = Math.max(this.content.lastIndexOf('\n'), this.content.lastIndexOf(' '));
            this.content = this.content.substring(0, Math.max(indexOfLastSpaceOrEnter, 0)).trim();
        } else {
            this.content = this.content.substring(0, this.content.length() - 1);
        }
    }

}
