package PictureFilter;

import RGBImage.ColorCalculator;
import RGBImage.FilterablePicture;
import RGBImage.SafeColor;

import java.awt.*;
import java.io.File;

/**
 * Created by Magda on 15.10.2016.
 * Factory that provides all picture filters.
 */
public class FilterFactory {

    private static FilterablePicture layer;

    public static void setLayer(File layerImage) {
        layer = new FilterablePicture(layerImage.getPath(), FilterType.Default);
    }

    public static PictureFilter getFilter(FilterablePicture picture, FilterType modType) {
        switch(modType) {
            case Red: return getRedFilter(picture);
            case Green: return getGreenFilter(picture);
            case Blue: return getBlueFilter(picture);
            case BlackAndWhite: return getBlackAndWhiteFilter(picture);
            case Negative: return getNegativeFilter(picture);
            case Sepia: return getSepia(picture);
            case Rotated: return getRotated(picture);
            case Displaced: return getDisplaced(picture);
            case LUT: return getLUT(picture);
            case Faded: return getFaded(picture);
            case Added: return getAdded(picture);
            case AddedAndSaturated: return getAddedAndSaturated(picture);
            case AddedWithTransparency: return getAddedWithTransparency(picture);
            case Subtracted: return getSubtracted(picture);
            case Multiplied: return getMultiplied(picture);
            case Divided: return getDivided(picture);
            case DifferencesBySubtraction: return getDifferencesBySubtraction(picture);
            case DifferencesByDivision: return getDifferencesByDivision(picture);
            default: return getDefaultFilter(picture);
        }
    }

    private static PictureFilter getDefaultFilter(FilterablePicture picture) {
        return picture::getAt;
    }

    private static PictureFilter getRedFilter(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.getRed(picture.getAt(i, j));
    }

    private static PictureFilter getGreenFilter(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.getGreen(picture.getAt(i, j));
    }

    private static PictureFilter getBlueFilter(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.getBlue(picture.getAt(i, j));
    }

    private static PictureFilter getBlackAndWhiteFilter(FilterablePicture picture)  {
        return (int i, int j) ->  ColorCalculator.getBlackAndWhite(picture.getAt(i, j));
    }

    private static PictureFilter getNegativeFilter(FilterablePicture picture)   {
        return (int i, int j) -> ColorCalculator.getNegative(picture.getAt(i, j));
    }

    private static PictureFilter getSepia(FilterablePicture picture)    {
        final int W = 40;
        return (int i, int j) -> ColorCalculator.getSepia(picture.getAt(i, j), W);
    }

    private static PictureFilter getRotated(FilterablePicture picture)    {
        return (int x, int y) ->    {
            final int degrees = 30;
            final int w = (picture.width()-1)/2;
            final int h = (picture.height()-1)/2;
            return picture.getAt(
                    w + (int) (((x-w) * Math.cos(Math.toRadians(degrees)) - (y-h)*Math.sin(Math.toRadians(degrees)))),
                    h + (int) (((x-w) * Math.sin(Math.toRadians(degrees)) + (y-h)*Math.cos(Math.toRadians(degrees))))
            );
        };
    }

    private static PictureFilter getDisplaced(FilterablePicture picture) {
        return (int x, int y) -> {
            int m = 300;
            int n = 0;
            x = x + m;
            y = y + n;
            return picture.getAt(
                    x  > picture.width() ? - picture.width() + x : x,
                    y  > picture.height() ? - picture.height() + y : y
            );
        };
    }

    private static PictureFilter getLUT(FilterablePicture picture) {
        return new LUTFilter(picture);
    }

    private static PictureFilter getFaded(FilterablePicture picture) {
        final int n = 200;
        return (int i, int j) -> ColorCalculator.getFaded(picture.getAt(i, j), n);
    }

    private static PictureFilter getAdded(FilterablePicture picture) {
        final double s = 0.5;
        return (int i, int j) -> ColorCalculator.add(picture.getAt(i, j), layer.getAt(i,j), s);
    }

    private static PictureFilter getAddedAndSaturated(FilterablePicture picture) {
        final double s = 0.8;
        return (int i, int j) -> ColorCalculator.addWithSaturation(picture.getAt(i, j), layer.getAt(i,j), s);
    }

    private static PictureFilter getAddedWithTransparency(FilterablePicture picture) {
        final double s = 0.3;
        return (int i, int j) -> ColorCalculator.addWithTransparency(picture.getAt(i, j), layer.getAt(i,j), s);
    }

    private static PictureFilter getSubtracted(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.subtract(picture.getAt(i, j), layer.getAt(i,j));
    }

    private static PictureFilter getMultiplied(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.multiply(picture.getAt(i, j), layer.getAt(i,j));
    }

    private static PictureFilter getDivided(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.divide(picture.getAt(i, j), layer.getAt(i,j));
    }

    private static PictureFilter getDifferencesBySubtraction(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.addWithDifferencesA(picture.getAt(i, j), layer.getAt(i,j), Color.RED);
    }

    private static PictureFilter getDifferencesByDivision(FilterablePicture picture) {
        return (int i, int j) -> ColorCalculator.addWithDifferencesB(picture.getAt(i, j), layer.getAt(i,j), Color.RED);
    }

}
