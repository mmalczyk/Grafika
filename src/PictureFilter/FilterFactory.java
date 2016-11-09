package PictureFilter;

import SpecialColor.ColorCalculator;
import RGBImage.FilterablePicture;

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

    private static PictureFilter getUniformSpreadFilter(FilterablePicture picture, Double level) {
        final double probability = 10;
        return (int i, int j) -> ColorCalculator.addUniformSpreadDisruption(picture.getAt(i, j), level, probability);
    }
}
