package PictureFilter.AreaFilter;

import PictureFilter.PictureFilter;
import RGBImage.FilterablePicture;
import RGBImage.Picture;
import SpecialColor.SafeColor;

import java.awt.*;

/**
 * We're assuming a binary picture here
 * Created by Magda on 30.11.2016.
 */
public class ErosionFilter extends AreaFilter{
    public ErosionFilter(FilterablePicture picture, String maskFile)  {
        initialisation(picture, maskFile);
    }

    public ErosionFilter(FilterablePicture picture, Picture mask)   {
        initialisation(picture, mask);
    }

    public Color filter (int x, int y)  {
        if (picture.get(x, y).equals(SafeColor.binaryColor(0)))
            return SafeColor.binaryColor(0);
        for (int i = x-widthRadius, mx = 0; i <= x+widthRadius; i++, mx++)
            for (int j = y-heightRadius, my = 0; j <= y+heightRadius; j++, my++)
                if (i>=0 && j>=0 && i<picture.width() && j<picture.height())
                    if (mask.get(mx, my).equals(SafeColor.binaryColor(1)) && picture.get(i,j).equals(SafeColor.binaryColor(0)))
                        return SafeColor.binaryColor(0);
        return picture.get(x,y);
    }

}
