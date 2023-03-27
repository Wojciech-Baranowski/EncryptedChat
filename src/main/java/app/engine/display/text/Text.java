package app.engine.display.text;

import app.engine.assets.color.Color;
import app.engine.assets.font.Font;
import app.engine.common.Rasterable;
import app.engine.display.Drawable;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static app.engine.assets.color.Color.getTransparentColorValue;
import static java.lang.Math.max;

public class Text implements Drawable {

    private final Font font;
    private final Color color;
    private String text;
    @Getter
    private int[] p;
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;
    @Getter
    private int w;
    @Getter
    private int h;


    Text(String text, int x, int y, Font font, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.color = color;
        this.w = recalculateWidth();
        this.h = recalculateHeight();
        this.p = recalculatePixels();
    }

    public void setText(String text) {
        this.text = text;
        this.w = recalculateWidth();
        this.h = recalculateHeight();
        this.p = recalculatePixels();
    }

    private int recalculateWidth() {
        int maxW = 0;
        int currentW = 0;
        int symbolsHeight = this.font.getSymbolRasterable('0').getH();
        for (char symbol : this.text.toCharArray()) {
            if (symbol == '\n') {
                maxW = max(maxW, currentW);
                currentW = 0;
                continue;
            }
            if (symbol == ' ') {
                currentW += symbolsHeight / 2;
                continue;
            }
            currentW += this.font.getSymbolRasterable(symbol).getW();
        }
        return max(maxW, currentW);
    }

    private int recalculateHeight() {
        int numberOfLines = 1;
        int symbolHeight = this.font.getSymbolRasterable('0').getH();
        for (char symbol : this.text.toCharArray()) {
            if (symbol == '\n') {
                numberOfLines++;
            }
        }
        return symbolHeight * numberOfLines;
    }

    private int[] recalculatePixels() {
        int[] pixels = new int[this.w * this.h];
        Arrays.fill(pixels, getTransparentColorValue());
        int currentX = 0;
        int currentY = 0;
        int symbolsHeight = this.font.getSymbolRasterable('0').getH();
        for (char symbol : this.text.toCharArray()) {
            if (symbol == ' ') {
                currentX += symbolsHeight / 2;
                continue;
            }
            if (symbol == '\n') {
                currentX = 0;
                currentY += symbolsHeight;
                continue;
            }
            Rasterable symbolRasterable = this.font.getSymbolRasterable(symbol);
            mergeSymbolIntoPixelArray(pixels, symbolRasterable, currentX, currentY);
            currentX += symbolRasterable.getW();
        }
        return pixels;
    }

    private void mergeSymbolIntoPixelArray(int[] pixels, Rasterable symbol, int currentX, int currentY) {

        int[] symbolPixels = symbol.getP();
        for (int x = 0; x < symbol.getW(); x++) {
            for (int y = 0; y < symbol.getH(); y++) {
                int pixel = symbolPixels[x + y * symbol.getW()];
                pixels[currentX + x + (currentY + y) * this.w] = (pixel != getTransparentColorValue() ? this.color.getValue() : pixel);
            }
        }
    }

}
