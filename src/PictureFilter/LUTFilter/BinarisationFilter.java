package PictureFilter.LUTFilter;

import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;
import SpecialColor.YCbCrColor;

import java.awt.*;

/**
 * Created by Magda on 24.11.2016.
 */
public class BinarisationFilter extends AbstractLUTFilter{

    double threshold;

    public BinarisationFilter(FilterablePicture picture, double threshold)  {
        initialize(picture, threshold, threshold);
    }

    @Override
    protected void prepareForLUT(double threshold) {
        if (threshold < SafeColor.getLowerLimit() || threshold > SafeColor.getUpperLimit())
            throw new IllegalArgumentException("Pixel outside range");
        this.threshold = threshold;
    }

    @Override
    protected void fillLookUpTable(double arg) {
        for (int i = SafeColor.getLowerLimit(); i < threshold; i++)
            LUT[i] = SafeColor.getLowerLimit();
        for (int i=(int)threshold; i <= SafeColor.getUpperLimit(); i++)
            LUT[i] = SafeColor.getUpperLimit();
    }

    public Color filter (int i, int j)
    {
        YCbCrColor color = new YCbCrColor(picture.getAt(i,j));
        int value = (int) LUT[(int)color.getYValue()];
        return new Color(value, value, value);
    }

}
