package app.engine.display;

import app.engine.assets.color.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
public class DrawableComposition implements Drawable {

    private int[] p;
    @Setter
    private int x;
    @Setter
    private int y;
    private int w;
    private int h;

    public DrawableComposition(Drawable bottom, Drawable top) {
        x = min(top.getX(), bottom.getX());
        y = min(top.getY(), bottom.getY());
        w = max(top.getX() + top.getW(), bottom.getX() + bottom.getW()) - x;
        h = max(top.getY() + top.getH(), bottom.getY() + bottom.getH()) - y;
        p = new int[w * h];
        Arrays.fill(p, Color.getTransparentColorValue());
        mergePixels(bottom);
        mergePixels(top);
    }

    public void update(DrawableComposition drawableComposition) {
        this.x = drawableComposition.getX();
        this.y = drawableComposition.getY();
        this.w = drawableComposition.getW();
        this.h = drawableComposition.getH();
        this.p = Arrays.copyOf(drawableComposition.getP(), drawableComposition.getP().length);
    }

    private void mergePixels(Drawable drawable) {
        int startX = drawable.getX() - x;
        int startY = drawable.getY() - y;
        int endX = startX + drawable.getW();
        int endY = startY + drawable.getH();
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int pixelValue = drawable.getP()[x - startX + (y - startY) * drawable.getW()];
                if (pixelValue != Color.getTransparentColorValue()) {
                    p[x + y * w] = (Color.isTransparent(pixelValue))
                            ? Color.blend(p[x + y * w], pixelValue)
                            : pixelValue;
                }
            }
        }
    }
}
