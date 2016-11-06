package PictureFilter;

import RGBImage.FilterablePicture;
import SpecialColor.YCbCrColor;

import java.awt.*;

/**
 * Created by Magda on 06.11.2016.
 */
public class SpreadFilter implements PictureFilter{

    private double[] LUT;
    private double maxValue;
    private double minValue;
    private FilterablePicture picture;

    public SpreadFilter(FilterablePicture picture)  {
        this.picture = picture;
        findTotalMinMax();
        LUT = createLookUpTable();
    }

    private void findTotalMinMax()    {
        maxValue = 0;
        minValue = 256;
        double value;
        for(int i=0; i<picture.width(); i++)
            for (int j=0; j<picture.height(); j++)  {
                value = new YCbCrColor(picture.getAt(i,j)).getLuminance();
                if (value < minValue)
                    minValue = value;
                if (value > maxValue)
                    maxValue = value;
            }
    }

    private double[] createLookUpTable()
    {
        final int imax = 255;
        double[] lut = new double[imax+1];

        for (int i = 0; i <= imax; i++)
            lut[i] = (double)imax/(maxValue-minValue)*(i-minValue);
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


