package PictureFilter.MeanFilter;

import RGBImage.FilterablePicture;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 25.11.2016.
 */
public class MixedBinarisationFilter extends BensenBinarisationFilter{
    private double globalThreshold;
    private double margin;

    public MixedBinarisationFilter(FilterablePicture picture, int radius, double globalThreshold, double margin) {
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
