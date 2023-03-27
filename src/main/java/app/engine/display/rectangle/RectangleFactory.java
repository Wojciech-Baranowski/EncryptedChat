package app.engine.display.rectangle;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.assets.color.Color;

public class RectangleFactory {

    private final Assets assets;

    public RectangleFactory() {
        this.assets = AssetsBean.getAssets();
    }

    public Rectangle makeRectangle(int x, int y, int w, int h, String colorName) {
        Color color = this.assets.getColor(colorName);
        return new Rectangle(x, y, w, h, color);
    }

    public Rectangle makeFramedRectangle(int x, int y, int w, int h, int frameThickness, String colorName, String frameColorName) {
        Color color = this.assets.getColor(colorName);
        Color frameColor = this.assets.getColor(frameColorName);
        return new FramedRectangle(x, y, w, h, frameThickness, color, frameColor);

    }

}
