package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;
import SpecialColor.YCbCrColor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class BensenBinarisationFilter extends AbstractMeanFilter {

    public BensenBinarisationFilter(FilterablePicture picture, int radius) {
        super(picture, radius);
    }

    double setThreshold(ArrayList<Color> pixels)  {
        YCbCrColor lowest = new YCbCrColor(pixels.get(0));
        YCbCrColor highest = new YCbCrColor(pixels.get(pixels.size()-1));
        return (lowest.getYValue()+highest.getYValue())/2.;
    }

    @Override
    protected Color calculate(ArrayList<Color> pixels, int i, int j) {
        double threshold = setThreshold(pixels);
        YCbCrColor original = new YCbCrColor(picture.get(i,j));
        return original.getYValue() < threshold ? Color.WHITE : Color.BLACK;
    }
}
