package app.engine.display;

import app.engine.assets.color.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class HoverMark implements Drawable {

    private static HoverMark hoverMark;
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

    private HoverMark() {}

    public static HoverMark getHoverMark() {
        if (hoverMark == null) {
            hoverMark = new HoverMark();
        }
        return hoverMark;
    }

    public void fitHoverMarkToDrawable(Drawable drawable) {
        this.x = drawable.getX();
        this.y = drawable.getY();
        this.w = drawable.getW();
        this.h = drawable.getH();
        this.p = new int[this.w * this.h];
        Arrays.fill(this.p, Color.getHoverMarkColorValue());
    }

}
