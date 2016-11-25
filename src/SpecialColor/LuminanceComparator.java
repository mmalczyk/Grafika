package SpecialColor;

import java.awt.Color;

import java.util.Comparator;

/**
 * Created by Magda on 25.11.2016.
 */
public class LuminanceComparator implements Comparator<Color>{

    @Override
    public int compare(Color color1, Color color2) {
        double comparison = (new YCbCrColor(color1)).getYValue()-(new YCbCrColor(color2)).getYValue();
        if (comparison == 0)
            return 0;
        if (comparison < 0)
            return (int)Math.floor(comparison);
        else
            return (int)Math.ceil(comparison);
    }
}
