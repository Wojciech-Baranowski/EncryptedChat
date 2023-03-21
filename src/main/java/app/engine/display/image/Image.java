package app.engine.display.image;

import app.engine.display.Drawable;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

public class Image implements Drawable {

    @Getter
    private final int[] p;
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;
    @Getter
    private final int w;
    @Getter
    private final int h;

    Image(BufferedImage image, int x, int y, int w, int h) {
        p = image.getRGB(0, 0, w, h, null, 0, w);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
