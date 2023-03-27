package app.engine.display.rectangle;

import app.engine.assets.color.Color;

public class FramedRectangle extends Rectangle {

    private final int frameThickness;
    private final Color frameColor;

    FramedRectangle(int x, int y, int w, int h, int frameThickness, Color color, Color frameColor) {
        super(x, y, w, h, color);
        this.frameColor = frameColor;
        this.frameThickness = frameThickness;
        fillFrame();
        fillInside();
    }

    private void fillFrame() {
        for (int x = 0; x < this.w; x++) {
            for (int y = 0; y < Math.min(this.frameThickness, this.h); y++) {
                this.p[x + y * this.w] = this.frameColor.getValue();
            }
            for (int y = this.h - 1; y >= Math.max(this.h - this.frameThickness, 0); y--) {
                this.p[x + y * this.w] = this.frameColor.getValue();
            }
        }
        for (int y = 0; y < this.h; y++) {
            for (int x = 0; x < Math.min(this.frameThickness, this.w); x++) {
                this.p[x + y * this.w] = this.frameColor.getValue();
            }
            for (int x = this.w - 1; x >= Math.max(this.w - this.frameThickness, 0); x--) {
                this.p[x + y * this.w] = this.frameColor.getValue();
            }
        }
    }

    private void fillInside() {
        for (int x = this.frameThickness; x < this.w - this.frameThickness; x++) {
            for (int y = this.frameThickness; y < this.h - this.frameThickness; y++) {
                this.p[x + y * this.w] = this.color.getValue();
            }
        }
    }

}
