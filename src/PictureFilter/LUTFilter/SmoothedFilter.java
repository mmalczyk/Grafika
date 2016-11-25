package PictureFilter.LUTFilter;

import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;
import SpecialColor.YCbCrColor;

import java.awt.*;

/**
 * Created by Magda on 06.11.2016.
 */
public class SmoothedFilter extends AbstractLUTFilter{

    private double[] distribution;

    public SmoothedFilter(FilterablePicture picture)  {
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
        final int iMax = SafeColor.getUpperLimit();
        distribution = new double[iMax+1];
        for(int i=0; i<picture.width(); i++)
            for (int j=0; j<picture.height(); j++)
                distribution[(int)new YCbCrColor(picture.getAt(i,j)).getYValue()]++;

        double sum = picture.width()*picture.height();
        for(int i=1; i<=iMax; i++)
            distribution[i] = distribution[i-1] + distribution[i];

        for(int i=0; i<=iMax; i++)
            distribution[i] = distribution[i]/sum;
    }

    @Override
    protected void fillLookUpTable(double arg) {
        final int iMax = SafeColor.getUpperLimit();
        for (int i = 0; i <= iMax; i++)
            LUT[i] = ((distribution[i]-distribution[0]) / (1-distribution[0])) * iMax;
    }
}


