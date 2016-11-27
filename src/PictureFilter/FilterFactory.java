package PictureFilter;

import PictureFilter.LUTFilter.*;
import PictureFilter.MeanFilter.*;
import SpecialColor.ColorCalculator;
import RGBImage.FilterablePicture;
import SpecialColor.HLSColor;
import SpecialColor.YCbCrColor;

import java.awt.*;
import java.io.File;

/**
 * Created by Magda on 15.10.2016.
 * Factory that provides all picture filters.
 */
public class FilterFactory {

    private static FilterablePicture layer;

    public static void setLayer(File layerImage) {
        layer = new FilterablePicture(layerImage.getPath(), FilterType.Default, null);
    }

    public static FilterWrapper getFilter(FilterablePicture picture, FilterType modType, Double arg) {
        PictureFilter filter;
        switch(modType) {
            case Red: filter = getRedFilter(picture, arg); break;
            case Green: filter = getGreenFilter(picture, arg); break;
            case Blue: filter = getBlueFilter(picture, arg); break;
            case BlackAndWhite: filter = getBlackAndWhiteFilter(picture, arg); break;
            case Negative: filter = getNegativeFilter(picture, arg); break;
            case Sepia: filter = getSepia(picture, arg); break;
            case Rotated: filter = getRotated(picture, arg); break;
            case Displaced: filter = getDisplaced(picture, arg); break;
            case LUT: filter = getLUT(picture, arg); break;
            case Faded: filter = getFaded(picture, arg); break;
            case Added: filter = getAdded(picture, arg); break;
            case AddedAndSaturated: filter = getAddedAndSaturated(picture, arg); break;
            case AddedWithTransparency: filter = getAddedWithTransparency(picture, arg); break;
            case Subtracted: filter = getSubtracted(picture, arg); break;
            case Multiplied: filter = getMultiplied(picture, arg); break;
            case Divided: filter = getDivided(picture, arg); break;
            case DifferencesBySubtraction: filter = getDifferencesBySubtraction(picture, arg); break;
            case DifferencesByDivision: filter = getDifferencesByDivision(picture, arg); break;
            case Contrast: filter = getContrastFilter(picture, arg); break;
            case Spread: filter = getSpreadFilter(picture, arg); break;
            case Smoothed: filter = getSmoothedFilter(picture, arg); break;
            case UniformSpreadDisruption: filter = getUniformSpreadFilter(picture, arg); break;
            case NormalDisruption: filter = getNormalDistributionFilter(picture, arg); break;
            case SaltAndPepper: filter = getSaltAndPepperFilter(picture, arg); break;
            case SaltAndPepperBW: filter = getSaltAndPepperBWFilter(picture, arg); break;
            case MovingAverageBW: filter = getMovingAverageBWFilter(picture, arg); break;
            case MeanAverageBW: filter = getMeanBWFilter(picture, arg); break;
            case Y: filter = getYFilter(picture, arg); break;
            case Cb: filter = getCbFilter(picture, arg); break;
            case Cr: filter = getCrFilter(picture, arg); break;
            case YCbCrToRGB: filter = getYCbCrToRGBFilter(picture, arg); break;
            case HLS: filter = getHLSFilter(picture, arg); break;
            case H: filter = getHFilter(picture, arg); break;
            case L: filter = getLFilter(picture, arg); break;
            case S: filter = getSFilter(picture, arg); break;
            case SkinDetection: filter = getSkinDetectionFilter(picture, arg); break;
            case RedEyes: filter = getRedEyesFilter(picture, arg); break;
            case GreenPeppers: filter = getGreenPeppersFilter(picture, arg); break;
            case MovingAverage: filter = getMovingAverageColorFilter(picture, arg); break;
            case MeanAverage: filter = getMeanColorFilter(picture, arg); break;
            case Binarised: filter = getBinarisationFilter(picture, arg); break;
            case OptimalThreshold: filter = getOptimalThresholdBinarisationFilter(picture, arg); break;
            case OtsuBinarised: filter = getOtsuBinarisationFilter(picture, arg); break;
            case BensenBinarisation: filter = getBensenBinarisationFilter(picture, arg); break;
            case MixedBinarisation: filter = getMixedBinarisationFilter(picture, arg); break;

            default: filter = getDefaultFilter(picture, arg);
        }
        return new FilterWrapper(filter, modType);
    }

    private static PictureFilter getDefaultFilter(FilterablePicture picture, Double arg) {
        return picture::getAt;
    }

    private static PictureFilter getRedFilter(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.getRed(picture.getAt(i, j));
    }

    private static PictureFilter getGreenFilter(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.getGreen(picture.getAt(i, j));
    }

    private static PictureFilter getBlueFilter(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.getBlue(picture.getAt(i, j));
    }

    private static PictureFilter getBlackAndWhiteFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) ->  ColorCalculator.getBlackAndWhite(picture.getAt(i, j));
    }

    private static PictureFilter getNegativeFilter(FilterablePicture picture, Double arg)   {
        return (int i, int j) -> ColorCalculator.getNegative(picture.getAt(i, j));
    }

    private static PictureFilter getSepia(FilterablePicture picture, Double arg)    {
        final int W = (int)arg.doubleValue();
        return (int i, int j) -> ColorCalculator.getSepia(picture.getAt(i, j), W);
    }

    private static PictureFilter getRotated(FilterablePicture picture, Double arg)    {
        return (int x, int y) ->    {
            final int degrees = (int)arg.doubleValue();
            final int w = (picture.width()-1)/2;
            final int h = (picture.height()-1)/2;
            return picture.getAt(
                    w + (int) (((x-w) * Math.cos(Math.toRadians(degrees)) - (y-h)*Math.sin(Math.toRadians(degrees)))),
                    h + (int) (((x-w) * Math.sin(Math.toRadians(degrees)) + (y-h)*Math.cos(Math.toRadians(degrees))))
            );
        };
    }

    private static PictureFilter getDisplaced(FilterablePicture picture, Double arg) {
        return (int x, int y) -> {
            int m = (int)arg.doubleValue();
            int n = 0;
            x = x + m;
            y = y + n;
            return picture.getAt(
                    x  > picture.width() ? - picture.width() + x : x,
                    y  > picture.height() ? - picture.height() + y : y
            );
        };
    }

    private static PictureFilter getLUT(FilterablePicture picture, Double arg) {
        return new SimpleLUTFilter(picture);
    }

    private static PictureFilter getFaded(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.getFaded(picture.getAt(i, j), (int)arg.doubleValue());
    }

    private static PictureFilter getAdded(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.add(picture.getAt(i, j), layer.getAt(i,j), arg);
    }

    private static PictureFilter getAddedAndSaturated(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.addWithSaturation(picture.getAt(i, j), layer.getAt(i,j), arg);
    }

    private static PictureFilter getAddedWithTransparency(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.addWithTransparency(picture.getAt(i, j), layer.getAt(i,j), arg);
    }

    private static PictureFilter getSubtracted(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.subtract(picture.getAt(i, j), layer.getAt(i,j));
    }

    private static PictureFilter getMultiplied(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.multiply(picture.getAt(i, j), layer.getAt(i,j));
    }

    private static PictureFilter getDivided(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.divide(picture.getAt(i, j), layer.getAt(i,j));
    }

    private static PictureFilter getDifferencesBySubtraction(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.addWithDifferencesA(picture.getAt(i, j), layer.getAt(i,j), Color.RED);
    }

    private static PictureFilter getDifferencesByDivision(FilterablePicture picture, Double arg) {
        return (int i, int j) -> ColorCalculator.addWithDifferencesB(picture.getAt(i, j), layer.getAt(i,j), Color.RED);
    }

    private static PictureFilter getContrastFilter(FilterablePicture picture, Double arg) {
        return new ContrastFilter(picture, arg);
    }

    private static PictureFilter getSpreadFilter(FilterablePicture picture, Double arg) {
        return new SpreadFilter(picture);
    }

    private static PictureFilter getSmoothedFilter(FilterablePicture picture, Double arg) {
        return new SmoothedFilter(picture);
    }

    private static PictureFilter getUniformSpreadFilter(FilterablePicture picture, Double probability) {
        final double level = 25;
        return (int i, int j) -> ColorCalculator.addUniformDisruption(picture.getAt(i, j), level, probability);
    }

    private static PictureFilter getNormalDistributionFilter(FilterablePicture picture, Double probability) {
        final double mean = 20;
        final double deviation = 20;
        return (int i, int j) -> ColorCalculator.addNormalDisruption(picture.getAt(i, j), deviation, mean, probability);
    }

    private static PictureFilter getSaltAndPepperFilter(FilterablePicture picture, Double probability) {
        return (int i, int j) -> ColorCalculator.addSaltAndPepper(picture.getAt(i, j), probability);
    }

    private static PictureFilter getSaltAndPepperBWFilter(FilterablePicture picture, Double probability) {
        return (int i, int j) -> ColorCalculator.addSaltAndPepperBW(picture.getAt(i, j), probability);
    }

    private static PictureFilter getMovingAverageBWFilter(FilterablePicture picture, Double radius) {
        return new MovingAverageBWFilter(picture, radius);
    }

    private static PictureFilter getMeanBWFilter(FilterablePicture picture, Double radius) {
        return new MeanBWFilter(picture, radius);
    }

    private static PictureFilter getYFilter(FilterablePicture picture, Double arg)   {
        return (int i, int j) -> ColorCalculator.getY(picture.getAt(i, j));
    }

    private static PictureFilter getCbFilter(FilterablePicture picture, Double arg)   {
        return (int i, int j) -> ColorCalculator.getCb(picture.getAt(i, j));
    }

    private static PictureFilter getCrFilter(FilterablePicture picture, Double arg)   {
        return (int i, int j) -> ColorCalculator.getCr(picture.getAt(i, j));
    }

    private static PictureFilter getYCbCrToRGBFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> new YCbCrColor(picture.getAt(i,j)).getRGB();
    }

    private static PictureFilter getHLSFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> new HLSColor(picture.getAt(i,j)).getRGB();
    }

    private static PictureFilter getHFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> new HLSColor(picture.getAt(i,j)).getH();
    }

    private static PictureFilter getLFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> new HLSColor(picture.getAt(i,j)).getL();
    }

    private static PictureFilter getSFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> new HLSColor(picture.getAt(i,j)).getS();
    }

    private static PictureFilter getSkinDetectionFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> ColorCalculator.detectSkin(picture.getAt(i,j));
    }

    private static PictureFilter getGreenPeppersFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> ColorCalculator.detectGreen(picture.getAt(i,j));
    }

    private static PictureFilter getRedEyesFilter(FilterablePicture picture, Double arg)  {
        return (int i, int j) -> ColorCalculator.detectRed(picture.getAt(i,j));
    }

    private static PictureFilter getMovingAverageColorFilter(FilterablePicture picture, Double radius) {
        return new MovingAverageColorFilter(picture, (int)radius.doubleValue());
    }

    private static PictureFilter getMeanColorFilter(FilterablePicture picture, Double radius) {
        return new MeanColorFilter(picture, (int)radius.doubleValue());
    }

    private static PictureFilter getBinarisationFilter(FilterablePicture picture, Double arg) {
        return new BinarisationFilter(picture, arg);
    }

    private static PictureFilter getOptimalThresholdBinarisationFilter(FilterablePicture picture, Double arg) {
        return new OptimalThresholdBinarisationFilter(picture, arg);
    }

    private static PictureFilter getOtsuBinarisationFilter(FilterablePicture picture, Double arg) {
        return new OtsuBinarisationFilter(picture);
    }

    private static PictureFilter getBensenBinarisationFilter(FilterablePicture picture, Double arg) {
        return new BensenBinarisationFilter(picture, (int)arg.doubleValue());
    }

    private static PictureFilter getMixedBinarisationFilter(FilterablePicture picture, Double arg) {
        return new MixedBinarisationFilter(picture, 5, arg, 50.);
    }

}
