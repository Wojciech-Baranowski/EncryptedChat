package app.engine.display.text;

import app.engine.assets.Assets;
import app.engine.assets.AssetsBean;
import app.engine.assets.color.Color;
import app.engine.assets.font.Font;

public class TextFactory {

    private final Assets assets;

    public TextFactory() {
        this.assets = AssetsBean.getAssets();
    }

    public Text makeText(String text, int x, int y, String fontName, String colorName) {
        Font font = assets.getFont(fontName);
        Color color = assets.getColor(colorName);
        return new Text(text, x, y, font, color);
    }

}
