package PictureFilter.LUTFilter;

import PictureFilter.PictureFilter;
import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;

/**
 * Created by Magda on 24.11.2016.
 */
abstract class AbstractLUTFilter implements PictureFilter {
    double[] LUT;
    FilterablePicture picture;

    final void initialize(FilterablePicture picture, double prepArg, double LUTArg)    {
        this.picture = picture;
        LUT = new double[SafeColor.getUpperLimit()+1];
        prepareForLUT(prepArg);
        fillLookUpTable(LUTArg);
    }

    protected abstract void prepareForLUT(double arg);
    protected abstract void fillLookUpTable(double arg);
}
