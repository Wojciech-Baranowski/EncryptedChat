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
    private final DrawableComposition drawable;
    private final Drawable background;
    private final int textOffsetX;
    private final int textOffsetY;
    private final Text text;
    private final int limit;
    private final boolean masked;
    private String content;
    private boolean shift;

    public TextField(int x, int y, int w, int h, int textOffsetX, int textOffsetY,
                     String backgroundColor, String font, String fontColor, boolean masked) {
        this.background = getDisplay().getDrawableFactory().makeRectangle(x, y, w, h, backgroundColor);
        this.text = getDisplay().getDrawableFactory().makeText("", x + textOffsetX, y + textOffsetY, font, fontColor);
        this.drawable = new DrawableComposition(this.background, this.text);
        this.textOffsetX = textOffsetX;
        this.textOffsetY = textOffsetY;
        this.content = "";
        this.shift = false;
        this.masked = masked;
        this.limit = Integer.MAX_VALUE;
    }

    public TextField(int x, int y, int w, int h, int textOffsetX, int textOffsetY,
                     String backgroundColor, String font, String fontColor, boolean masked, int limit) {
        this.background = getDisplay().getDrawableFactory().makeRectangle(x, y, w, h, backgroundColor);
        this.text = getDisplay().getDrawableFactory().makeText("", x + textOffsetX, y + textOffsetY, font, fontColor);
        this.drawable = new DrawableComposition(this.background, this.text);
        this.textOffsetX = textOffsetX;
        this.textOffsetY = textOffsetY;
        this.content = "";
        this.shift = false;
        this.masked = masked;
        this.limit = limit;
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
            this.text.setText(this.masked ? "*".repeat(this.content.length()) : this.content);
            if (this.text.getW() + this.textOffsetX > this.background.getW()) {
                foldText();
            }
            if (this.text.getH() + this.textOffsetY > this.background.getH()) {
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
        return this.content.replaceAll("\\s+", " ").trim();
    }

    private void updateContent(InputElement inputElement) {
        if (inputElement.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = (inputElement.getActionType().equals(DOWN));
        } else if (inputElement.getActionType().equals(DOWN)) {
            if (inputElement.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                delete(shift);
            } else if (this.getContent().length() < limit && Font.getSymbolBasedOnKeycode(inputElement.getKeyCode(), this.shift) != 0) {
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
            int indexOfLastWhiteSpace = Math.max(this.content.lastIndexOf("\n"), this.content.lastIndexOf(' '));
            this.content = this.content.substring(0, Math.max(indexOfLastWhiteSpace, 0)).trim();
        } else if (this.content.length() > 0) {
            this.content = this.content.substring(0, this.content.length() - 1);
        }
    }

}
