package PictureFilter.LUTFilter;

import PictureFilter.PictureFilter;
import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;

/**
 * Created by Magda on 24.11.2016.
 */
public abstract class AbstractLUTFilter implements PictureFilter {
    protected double[] LUT;
    protected FilterablePicture picture;

    protected final void initialize(FilterablePicture picture, double prepArg, double LUTarg)    {
        this.picture = picture;
        LUT = new double[SafeColor.getUpperLimit()+1];
        prepareForLUT(prepArg);
        fillLookUpTable(LUTarg);
    }

    protected abstract void prepareForLUT(double arg);
    protected abstract void fillLookUpTable(double arg);
}
