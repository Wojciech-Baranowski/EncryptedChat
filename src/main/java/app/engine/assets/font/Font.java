package app.engine.assets.font;

import app.engine.common.Rasterable;

import java.awt.event.KeyEvent;

public interface Font {

    Rasterable getSymbolRasterable(char symbol);

    static String getExtendedAlphabet() {
        return "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~♤♥♦♧♨♩";
    }

    static int getSymbolBasedOnKeycode(int keycode, boolean shift) {
        if (keycode >= 'A' && keycode <= 'Z') {
            return keycode + 32 + (shift ? -32 : 0);
        }
        if (keycode == '=') {
            return keycode + (shift ? -18 : 0);
        }
        if (keycode == '7' || keycode == '9') {
            return keycode + (shift ? -17 : 0);
        }
        if (keycode == '1' || keycode == '3' || keycode == '4' || keycode == '5') {
            return keycode + (shift ? -16 : 0);
        }
        if (keycode == '8') {
            return keycode + (shift ? -14 : 0);
        }
        if (keycode == '0') {
            return keycode + (shift ? -7 : 0);
        }
        if (keycode == 222) {
            return keycode - 183 + (shift ? -5 : 0);
        }
        if (keycode == ';') {
            return keycode + (shift ? -1 : 0);
        }
        if (keycode == '2') {
            return keycode + (shift ? 14 : 0);
        }
        if (keycode == ',' || keycode == '.' || keycode == '/') {
            return keycode + (shift ? 16 : 0);
        }
        if (keycode == 192) {
            return keycode - 96 + (shift ? 30 : 0);
        }
        if (keycode == '[' || keycode == '\\' || keycode == ']') {
            return keycode + (shift ? 32 : 0);
        }
        if (keycode == '6') {
            return keycode + (shift ? 40 : 0);
        }
        if (keycode == '-') {
            return keycode + (shift ? 50 : 0);
        }
        if (keycode == KeyEvent.VK_ENTER || keycode == KeyEvent.VK_SPACE) {
            return keycode;
        }
        return 0;
    }

}
