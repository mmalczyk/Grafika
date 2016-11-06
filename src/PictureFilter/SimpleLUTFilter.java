package PictureFilter;
import RGBImage.FilterablePicture;
import SpecialColor.ColorCalculator;

import java.awt.*;

/**
 * Created by Magda on 01.11.2016.
 * Picture filter that needs a lookup table
 */
public class SimpleLUTFilter implements PictureFilter{

    protected int[] LUT;
    protected FilterablePicture picture;

    public SimpleLUTFilter(FilterablePicture picture)  {
        this.picture = picture;
        LUT = createLookUpTable(1);
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
        double firstVar = ((double)color - ((double)delta / 2.) - 1.) / (double)delta;
        int t = (delta/2) - 1;
        return (int)Math.floor(Math.max(firstVar, 0) * delta + t);
    }

    public Color filter (int i, int j)
    {
        Color color = picture.getAt(i,j);
        int newRed = LUT[color.getRed()];
        int newGreen = LUT[color.getGreen()];
        int newBlue = LUT[color.getBlue()];
        return new Color(Math.abs(newRed), Math.abs(newGreen), Math.abs(newBlue));
    }
}
