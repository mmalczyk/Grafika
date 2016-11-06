package SpecialColor;

import java.awt.*;

/**
 * Created by Magda on 15.10.2016.
 */
public class SafeColor {

    final static int lowerLimit = 0;
    final static int upperLimit = 255;

    public static int getLowerLimit() {
        return lowerLimit;
    }

    public static int getUpperLimit() {
        return upperLimit;
    }

    public static int applyBounds(int r) {
        return r >= lowerLimit ? (r <= upperLimit ? r : upperLimit) : lowerLimit;
    }

    public static int applyModuloBounds(int r)  {
        return r >= lowerLimit ? r % upperLimit : lowerLimit;
    }

    public static Color getBoundedColor(int r, int g, int b)    {
        return new Color(applyBounds(r), applyBounds(g), applyBounds(b));
    }

    public static Color getModuloBoundedColor(int r, int g, int b)    {
        return new Color(applyModuloBounds(r), applyModuloBounds(g), applyModuloBounds(b));
    }

}
