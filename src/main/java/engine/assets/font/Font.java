package engine.assets.font;

import engine.common.Rasterable;

public interface Font {

    Rasterable getSymbolRasterable(char symbol);

    static String getExtendedAlphabet() {
        return "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~♤♥♦♧♨♩";
    }

}
