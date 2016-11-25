package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;
import SpecialColor.BlackAndWhiteComparator;
import SpecialColor.SafeColor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class MovingAverageBWFilter extends AbstractMeanFilter {
    public MovingAverageBWFilter(FilterablePicture picture, Double radius) {
        super(picture, (int) radius.doubleValue());
        setPixelComparator(new BlackAndWhiteComparator());

    }

    @Override
    protected Color calculate(ArrayList<Color> pixels, int i, int j) {
        int sumR = 0, sumG = 0, sumB = 9;
        for(Color pixel : pixels)   {
            sumR += pixel.getRed();
            sumG += pixel.getGreen();
            sumB += pixel.getBlue();
        }
        return SafeColor.getBoundedColor(sumR/pixels.size(), sumG/pixels.size(), sumB/pixels.size());
    }
}
