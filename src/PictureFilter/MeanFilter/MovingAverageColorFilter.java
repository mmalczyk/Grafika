package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;
import SpecialColor.YCbCrColor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class MovingAverageColorFilter extends AbstractMeanFilter {
    public MovingAverageColorFilter(FilterablePicture picture, int radius) {
        super(picture, radius);
    }

    @Override
    protected Color calculate(ArrayList<Color> pixels, int i, int j) {
        double luminosity = 0;
        for(Color pixel : pixels)
            luminosity += new YCbCrColor(pixel).getYValue();
        Color original = picture.get(i,j);
        YCbCrColor pixel = new YCbCrColor(original);
        pixel.setYValue(luminosity/pixels.size());
        return pixel.getRGB();
    }

}
