package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;
import SpecialColor.YCbCrColor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class MeanColorFilter extends AbstractMeanFilter {
    public MeanColorFilter(FilterablePicture picture, int radius) {
        super(picture, radius);
    }

    @Override
    protected Color calculate(ArrayList<Color> pixels, int i, int j) {
        YCbCrColor newColor = new YCbCrColor(pixels.get(pixels.size()/2));
        YCbCrColor original = new YCbCrColor(picture.get(i,j));
        original.setYValue(newColor.getYValue());
        return original.getRGB();
    }

}
