package PictureFilter;


import java.awt.*;
import SpecialColor.YCbCrColor;
import RGBImage.FilterablePicture;
/**
 * Created by Magda on 05.11.2016.
 */
class ContrastFilter implements PictureFilter{

    protected double[] LUT;
    protected FilterablePicture picture;

    ContrastFilter(FilterablePicture picture, double contrast)  {
        this.picture = picture;
        LUT = createLookUpTable(contrast);
    }

    private static double[] createLookUpTable(double a)
    {
        final int imax = 255;
        double[] lut = new double[imax+1];

        double verge;
        for (int i = 0; i <= imax; i++)
        {
            verge = a*(i-(double)imax/2.) + (double)imax/2.;
            if (verge<0)
                lut[i] = 0;
            else if (verge>imax)
                lut[i] = imax;
            else
                lut[i] = (int)verge;

        }
        return lut;
    }

    public Color filter (int i, int j)
    {
        YCbCrColor color = new YCbCrColor(picture.getAt(i,j));
        double lu = color.getLuminance();
        color.setLuminance(LUT[(int)lu]);
        return color.getRGB();
    }

}


