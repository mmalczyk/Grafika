package PictureFilter.LUTFilter;
import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;

import java.awt.*;

/**
 * Created by Magda on 01.11.2016.
 * Picture filter that needs a lookup table
 */
public class SimpleLUTFilter extends AbstractLUTFilter{

    public SimpleLUTFilter(FilterablePicture picture)  {
        initialize(picture, 0, 1);
    }

    @Override
    protected void prepareForLUT(double arg) {
        //empty by design
    }

    @Override
    protected void fillLookUpTable(double newB) {
        int B = 8;
        int delta = (int)Math.pow(2, B - newB);
        for (int i = 0; i < SafeColor.getUpperLimit(); i++)
            LUT[i] = getColor(i, delta);
    }

    private static int getColor(int color, int delta)   {
        double firstVar = ((double)color - ((double)delta / 2.) - 1.) / (double)delta;
        int t = (delta/2) - 1;
        return (int)Math.floor(Math.max(firstVar, 0) * delta + t);
    }

    public Color filter (int i, int j)
    {
        Color color = picture.getAt(i,j);
        int newRed = (int)LUT[color.getRed()];
        int newGreen = (int)LUT[color.getGreen()];
        int newBlue = (int)LUT[color.getBlue()];
        return new Color(Math.abs(newRed), Math.abs(newGreen), Math.abs(newBlue));
    }
}
