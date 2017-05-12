package net.realmproject.dcm.stock.examples.breakout.engine;

import java.awt.Color;


public enum SpriteColour {

    RED {

        public Color toAwtColor() {
            return Colours.red;
        }

    },
    ORANGE {

        public Color toAwtColor() {
            return Colours.orange;
        }

    },
    YELLOW {

        public Color toAwtColor() {
            return Colours.yellow;
        }

    },
    GREEN {

        public Color toAwtColor() {
            return Colours.green;
        }

    },
    BLUE {

        public Color toAwtColor() {
            return Colours.blue;
        }

    },
    GRAY {

        public Color toAwtColor() {
            return Color.gray;
        }

    }

    ;

    public Color toAwtColor() {
        return Color.black;
    }
}

class Colours {

    static Color red = new Color(0xF4, 0x43, 0x36);
    static Color orange = new Color(0xFF, 0x98, 0x00);
    static Color yellow = new Color(0xFF, 0xEB, 0x3B);
    static Color green = new Color(0x4C, 0xAF, 0x50);
    static Color blue = new Color(0x21, 0x96, 0xF3);

}
