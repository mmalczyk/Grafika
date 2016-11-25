package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;
import SpecialColor.BlackAndWhiteComparator;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class MeanBWFilter extends AbstractMeanFilter {
    public MeanBWFilter(FilterablePicture picture, Double radius) {
        super(picture, (int) radius.doubleValue());
        setPixelComparator(new BlackAndWhiteComparator());
    }

    @Override
    protected Color calculate(ArrayList<Color> pixels, int i, int j) {
        return pixels.get(pixels.size()/2);
    }
}
