package PictureFilter;
import RGBImage.ColorCalculator;
import RGBImage.FilterablePicture;

import java.awt.*;

/**
 * Created by Magda on 01.11.2016.
 * Picture filter that needs a lookup table
 */
class LUTFilter implements PictureFilter{

    private int[] LUT;
    private FilterablePicture picture;

    LUTFilter(FilterablePicture picture)  {
        LUT = createLookUpTable(1);
        this.picture = picture;
    }

    private static int[] createLookUpTable(int newB)
    {
        int[] lut = new int[256];
        int B = 8;
        int delta = (int)Math.pow(2, B - newB);
        for (int i = 0; i < 256; i++)
            lut[i] = getColor(i, delta);
        return lut;
    }

    private static int getColor(int color, int delta)   {
        double firstVar = (color - (delta / 2) - 1) / delta;
        int t = (delta/2) - 1;
        return (int)Math.floor(Math.max(firstVar, 0) * delta + t);
    }

    @Override
    public Color filter(int i, int j) {
        return ColorCalculator.getFromLUT(picture.getAt(i,j), LUT);
    }
}
