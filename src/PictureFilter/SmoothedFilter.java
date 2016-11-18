package PictureFilter;

import RGBImage.FilterablePicture;
import SpecialColor.YCbCrColor;

import java.awt.*;

/**
 * Created by Magda on 06.11.2016.
 */
public class SmoothedFilter implements PictureFilter{

    private double[] distribution;
    private double[] LUT;
    private FilterablePicture picture;

    public SmoothedFilter(FilterablePicture picture)  {
        this.picture = picture;
        findDistribution();
        LUT = createLookUpTable();
    }

    private void findDistribution()
    {
        final int imax = 255;
        distribution = new double[imax+1];
        for(int i=0; i<picture.width(); i++)
            for (int j=0; j<picture.height(); j++)
                distribution[(int)new YCbCrColor(picture.getAt(i,j)).getYValue()]++;

        double sum = picture.width()*picture.height();
        for(int i=1; i<=imax; i++)
            distribution[i] = distribution[i-1] + distribution[i];

        for(int i=0; i<=imax; i++)
            distribution[i] = distribution[i]/sum;

    }

    private double[] createLookUpTable()
    {
        final int imax = 255;
        double[] lut = new double[imax+1];

        for (int i = 0; i <= imax; i++)
            lut[i] = ((distribution[i]-distribution[0]) / (1-distribution[0])) * imax;
        return lut;
    }

    public Color filter (int i, int j)
    {
        YCbCrColor color = new YCbCrColor(picture.getAt(i,j));
        double lu = color.getYValue();
        color.setYValue(LUT[(int)lu]);
        return color.getRGB();
    }

}


