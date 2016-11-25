package SpecialColor;

import java.awt.*;
import java.util.Comparator;

/**
 * Created by Magda on 25.11.2016.
 */
public class BlackAndWhiteComparator implements Comparator<Color> {

    @Override
    public int compare(Color color1, Color color2) {
        return Integer.compare(color1.getRGB(), color2.getRGB());
    }

}
