package PictureFilter.LUTFilter;

import RGBImage.FilterablePicture;
import SpecialColor.SafeColor;
import SpecialColor.YCbCrColor;

import java.util.stream.IntStream;

/**
 * Created by Magda on 24.11.2016.
 */
public class OtsuBinarisationFilter extends BinarisationFilter {
    int[] histogram;
    double[] probabilities;
    double[] classProbabilitiesA;
    double[] classProbabilitiesB;
    double pixelCount;

    public OtsuBinarisationFilter(FilterablePicture picture) {
        super(picture, 1.);
    }

    @Override
    protected void prepareForLUT(double threshold) {
        //super.prepareForLUT(threshold);
        fillHistogram();
        //fillProbabilities();
        findThresholdOtsu();
    }

    private void fillProbabilities() {
        probabilities = new double[SafeColor.getUpperLimit()+1];

        //TODO is that right?
        double value = ((double)histogram[0])/pixelCount;
        probabilities[0] = value;
        classProbabilitiesA[0] = 0;
        classProbabilitiesB[0] = 1.;

        for(int i=1; i<=SafeColor.getUpperLimit(); i++)
        {
            value = ((double)histogram[i])/pixelCount;
            probabilities[i] = value;
            classProbabilitiesA[i] = value + classProbabilitiesA[i-1];
            classProbabilitiesB[0] = 1. - classProbabilitiesA[i];
        }

    }

    private void fillHistogram()    {
        histogram = new int[SafeColor.getUpperLimit()+1];
        pixelCount = picture.width()*picture.height();
        double luminosity;
        for (int i = 0; i < picture.width(); i++)
            for (int j = 0; j < picture.height(); j++)
            {
                luminosity = new YCbCrColor(picture.getAt(i,j)).getYValue();
                histogram[(int)luminosity]++;
            }
        String h = histogramString();
        return;
    }

    private String histogramString()    {
        String s = "[";
        for (double value : histogram)
            s+=value+",";
        return s.substring(0, s.length()-1)+"]";
    }

    private double skalarMultiplication(int array1[], int array2[]){
        //TODO might want to put this function somewhere else
        double result = 0.;
        if (array1.length != array2.length)
            throw new IllegalArgumentException("Arrays of unequal length");
        for (int i=0; i<array1.length; i++)
            result += array1[i]*array2[i];
        return  result;
    }

    private void findThresholdOtsu() {
        //https://en.wikipedia.org/wiki/Otsu's_method#MATLAB_implementation
        double sumB = 0., wB = 0., wF = 0., maximum = 0.0, mB = 0., mF = 0., between = 0.;
        double sum1 = skalarMultiplication(
                histogram,
                IntStream.iterate(0, n -> n + 1).limit(SafeColor.getUpperLimit() + 1).toArray());
        for (int i = 1; i <= SafeColor.getUpperLimit(); i++) {
            wB = wB + histogram[i];
            if (wB == 0)
                continue;
            wF = pixelCount - wB;
            if (wF == 0)
                break;
            sumB = sumB + (double)i * (double)histogram[i];
            mB = sumB / wB;
            mF = (sum1 - sumB) / wF;
            between = wB * wF * (mB - mF) * (mB - mF);
            if (between >= maximum) {
                threshold = i;
                maximum = between;
            }
        }
    }

}
