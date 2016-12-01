package PictureFilter.AreaFilter;

import RGBImage.FilterablePicture;
import RGBImage.Picture;
import SpecialColor.SafeColor;
import SpecialColor.YCbCrColor;

import java.awt.*;

/**
 * Created by Magda on 01.12.2016.
 */
public class ColorDilationFilter extends AreaFilter{
    public ColorDilationFilter(FilterablePicture picture, String maskFile)  {
        initialisation(picture, maskFile);
    }

    public ColorDilationFilter(FilterablePicture picture, Picture mask)   {
        initialisation(picture, mask);
    }

    public Color filter (int x, int y)  {
        Color max = null;
        double luminosity = 0;
        for (int i = x-widthRadius, mx = 0; i <= x+widthRadius; i++, mx++)
            for (int j = y-heightRadius, my = 0; j <= y+heightRadius; j++, my++)
                if (i>=0 && j>=0 && i<picture.width() && j<picture.height())
                    if (mask.get(mx, my).equals(SafeColor.binaryColor(1)))
                    {
                        YCbCrColor color = new YCbCrColor(picture.get(i,j));
                        if (luminosity < color.getYValue())
                        {
                            max = color.getRGB();
                            luminosity = color.getYValue();
                        }

                    }
        return max;
    }

}
