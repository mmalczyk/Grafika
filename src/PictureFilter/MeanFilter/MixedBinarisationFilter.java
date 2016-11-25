package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class MixedBinarisationFilter extends BensenBinarisationFilter{
    private final double globalThreshold;
    private final double margin;

    public MixedBinarisationFilter(FilterablePicture picture, @SuppressWarnings("SameParameterValue") int radius, double globalThreshold, @SuppressWarnings("SameParameterValue") double margin) {
        super(picture, radius);
        this.globalThreshold = globalThreshold;
        this.margin = margin;
    }

    @Override
    protected double setThreshold(ArrayList<Color> pixels) {
        double localThreshold = super.setThreshold(pixels);
        if (globalThreshold-margin<localThreshold && localThreshold<globalThreshold+margin)
            return localThreshold;
        else
            return globalThreshold;
    }
}
