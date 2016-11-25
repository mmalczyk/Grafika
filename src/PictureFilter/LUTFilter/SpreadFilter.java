package PictureFilter.LUTFilter;

import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;
import SpecialColor.YCbCrColor;

import java.awt.*;

/**
 * Created by Magda on 06.11.2016.
 */
public class SpreadFilter extends AbstractLUTFilter{

    private double maxValue;
    private double minValue;

    public SpreadFilter(FilterablePicture picture)  {
        initialize(picture, 0, 0);
    }

    public Color filter (int i, int j)
    {
        YCbCrColor color = new YCbCrColor(picture.getAt(i,j));
        double lu = color.getYValue();
        color.setYValue(LUT[(int)lu]);
        return color.getRGB();
    }

    @Override
    protected void prepareForLUT(double arg) {
        maxValue = Double.MIN_VALUE;
        minValue = Double.MAX_VALUE;
        double value;
        for(int i=0; i<picture.width(); i++)
            for (int j=0; j<picture.height(); j++)  {
                value = new YCbCrColor(picture.getAt(i,j)).getYValue();
                if (value < minValue)
                    minValue = value;
                if (value > maxValue)
                    maxValue = value;
            }
    }

    @Override
    protected void fillLookUpTable(double arg) {
        final int iMax = SafeColor.getUpperLimit();
        for (int i = 0; i <= iMax; i++)
            LUT[i] = ((double)iMax/(maxValue-minValue))*(i-minValue);
    }
}


