package PictureFilter.LUTFilter;

import RGBImage.FilterablePicture;
import SpecialColor.YCbCrColor;

/**
 * Created by Magda on 24.11.2016.
 */
public class OptimalThresholdBinarisationFilter extends BinarisationFilter{
    private double avgBelow;
    private double avgAbove;

    public OptimalThresholdBinarisationFilter(FilterablePicture picture, double threshold) {
        super(picture, threshold);
    }

    @Override
    protected void prepareForLUT(double threshold) {
        super.prepareForLUT(threshold);
        double newThreshold = threshold;
        do  {
            threshold = newThreshold;   //at the beginning no change in threshold
            setAvg(threshold);
            newThreshold = (avgBelow+avgAbove)/2.0;
        } while ((int)threshold != (int)newThreshold);
        this.threshold = threshold;
    }

    private void setAvg(double threshold) {
        double meanBelow = 0, meanAbove = 0;
        int countBelow = 0, countAbove = 0;
        double YValue;
        for (int i = 0; i < picture.width(); i++)
            for (int j = 0; j < picture.height(); j++)
            {
                YValue = new YCbCrColor(picture.getAt(i,j)).getYValue();
                if (YValue<threshold)   {
                    countBelow++;
                    meanBelow += YValue;
                }
                else{
                    countAbove++;
                    meanAbove += YValue;
                }
            }
        avgBelow = countBelow == 0. ? 0 : meanBelow/countBelow;
        avgAbove = countAbove == 0. ? 0 : meanAbove/countAbove;
    }

}
