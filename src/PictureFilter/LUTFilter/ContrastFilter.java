package PictureFilter.LUTFilter;


import java.awt.*;

import SpecialColor.SafeColor;
import SpecialColor.YCbCrColor;
import RGBImage.FilterablePicture;
/**
 * Created by Magda on 05.11.2016.
 */
public class ContrastFilter extends AbstractLUTFilter {

    public ContrastFilter(FilterablePicture picture, double contrast)  {
        initialize(picture, 0, contrast);
    }

    @Override
    protected void prepareForLUT(double arg) {
        //empty by design
    }


    @Override
    protected void fillLookUpTable(double a)
    {
        final int iMax = SafeColor.getUpperLimit();
        double verge;
        for (int i = 0; i <= iMax; i++)
        {
            verge = a*(i-(double)iMax/2.) + (double)iMax/2.;
            if (verge<0)
                LUT[i] = 0;
            else if (verge>iMax)
                LUT[i] = iMax;
            else
                LUT[i] = verge;
        }
    }

    @Override
    public Color filter (int i, int j)
    {
        YCbCrColor color = new YCbCrColor(picture.getAt(i,j));
        double lu = color.getYValue();
        color.setYValue(LUT[(int)lu]);
        return color.getRGB();
    }

}


