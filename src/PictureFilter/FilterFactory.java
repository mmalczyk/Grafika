package PictureFilter;

import RGBImage.FilterablePicture;
import RGBImage.SafeColor;

import java.awt.*;
import java.io.File;

/**
 * Created by Magda on 15.10.2016.
 */
public class FilterFactory {

    private static FilterablePicture layer;

    public static void setLayer(File layerImage) {
        layer = new FilterablePicture(layerImage.getPath(), FilterType.Default);
    }

    private static int[] createLookUpTable(int newB)
    {
        int[] lut = new int[256];
        int B = 8;
        int delta = (int)Math.pow(2, B - newB);
        for (int i = 0; i < 256; i++)
            lut[i] = getColor(i, delta);
        return lut;
    }

    private static int getColor(int oldColor, int delta)
    {
        double firstVar = (oldColor - (delta / 2) - 1) / delta;
        int t = (delta/2) - 1;
        return (int) Math.floor(Math.max(firstVar, 0) * delta + t);
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
        return (int i, int j) -> picture.getAt(i, j, null);
    }

    private static PictureFilter getRedFilter(FilterablePicture picture) {
        return (int i, int j) -> picture.getAt(i, j, Color.RED);
    }

    private static PictureFilter getGreenFilter(FilterablePicture picture) {
        return (int i, int j) -> picture.getAt(i, j, Color.GREEN);
    }

    private static PictureFilter getBlueFilter(FilterablePicture picture) {
        return (int i, int j) -> picture.getAt(i, j, Color.BLUE);
    }

    private static PictureFilter getBlackAndWhiteFilter(FilterablePicture picture)  {
        return (int i, int j) -> {
            double avg = (
                    picture.getInt(i, j, Color.RED) +
                    picture.getInt(i, j, Color.GREEN) +
                    picture.getInt(i, j, Color.BLUE))/3;
            return new Color((int) avg, (int) avg, (int) avg);
        };
    }

    private static PictureFilter getNegativeFilter(FilterablePicture picture)   {
        return (int i, int j) ->
                new Color(
                        255-picture.getInt(i, j, Color.RED),
                        255-picture.getInt(i, j, Color.GREEN),
                        255-picture.getInt(i, j, Color.BLUE)
                );
    }

    private static PictureFilter getSepia(FilterablePicture picture)    {
        return (int i, int j) ->    {
            final int W = 40;
            int avg = ( picture.getInt(i, j, Color.RED) +
                        picture.getInt(i, j, Color.GREEN) +
                        picture.getInt(i, j, Color.BLUE)) /3;
            return SafeColor.getBoundedColor(avg + 2*W , avg + W, avg);
        };
    }

    private static PictureFilter getRotated(FilterablePicture picture)    {
        return (int x, int y) ->    {
            final int degrees = 30;
            final int w = (picture.width()-1)/2;
            final int h = (picture.height()-1)/2;
            return picture.getAt(
                    w + (int) (((x-w) * Math.cos(Math.toRadians(degrees)) - (y-h)*Math.sin(Math.toRadians(degrees)))),
                    h + (int) (((x-w) * Math.sin(Math.toRadians(degrees)) + (y-h)*Math.cos(Math.toRadians(degrees)))),
                    null
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
                    y  > picture.height() ? - picture.height() + y : y,
                    null
            );
        };
    }

    private static PictureFilter getLUT(FilterablePicture picture) {
        return (int x, int y) ->    {
            int[] LUT = createLookUpTable(1);
            int newRed = LUT[picture.getInt(x, y, Color.RED)];
            int newGreen = LUT[picture.getInt(x, y, Color.GREEN)];
            int newBlue = LUT[picture.getInt(x, y, Color.BLUE)];
            return new Color(Math.abs(newRed), Math.abs(newGreen), Math.abs(newBlue));
        };
    }

    private static PictureFilter getFaded(FilterablePicture picture) {
        return (int x, int y) ->    {
            int n = 200;
            return SafeColor.getBoundedColor(
                    picture.getInt(x, y, Color.RED) + n,
                    picture.getInt(x, y, Color.GREEN) + n,
                    picture.getInt(x, y, Color.BLUE) + n
            );
        };
    }

    private static PictureFilter getAdded(FilterablePicture picture) {
        return (x, y) ->    {
            double s = 0.5;
            return SafeColor.getBoundedColor(
                    (int)(s*picture.getInt(x, y, Color.RED) + (1-s)*layer.getInt(x,y, Color.RED)),
                    (int)(s*picture.getInt(x, y, Color.GREEN) + (1-s)*layer.getInt(x,y, Color.GREEN)),
                    (int)(s*picture.getInt(x, y, Color.BLUE) + (1-s)*layer.getInt(x,y, Color.BLUE))
            );
        };
    }

    private static PictureFilter getAddedAndSaturated(FilterablePicture picture) {
        return (x, y) -> {
            double s = 0.8;
            return SafeColor.getBoundedColor(
                    picture.getInt(x, y, Color.RED) + (int)(s*layer.getInt(x, y, Color.RED)),
                    picture.getInt(x, y, Color.GREEN) + (int)(s*layer.getInt(x, y, Color.GREEN)),
                    picture.getInt(x, y, Color.BLUE) + (int)(s*layer.getInt(x, y, Color.BLUE))
            );
        };
    }

    private static PictureFilter getAddedWithTransparency(FilterablePicture picture) {
        return (x, y) -> {
            double s = 0.3;
            int r = picture.getInt(x, y, Color.RED);
            int g = picture.getInt(x, y, Color.GREEN);
            int b = picture.getInt(x, y, Color.BLUE);
            return SafeColor.getBoundedColor(
                    r == SafeColor.getUpperLimit() ? r : (int)(r + s*layer.getInt(x, y, Color.RED)),
                    g == SafeColor.getUpperLimit() ? g : (int)(g + s*layer.getInt(x, y, Color.GREEN)),
                    b == SafeColor.getUpperLimit() ? b : (int)(b + s*layer.getInt(x, y, Color.BLUE))
            );
        };
    }

    private static PictureFilter getSubtracted(FilterablePicture picture) {
        return (x, y) ->    {
            double s = 0.5;
            return SafeColor.getBoundedColor(
                    (int)(picture.getInt(x, y, Color.RED) - (1-s)*layer.getInt(x,y, Color.RED)),
                    (int)(picture.getInt(x, y, Color.GREEN) - (1-s)*layer.getInt(x,y, Color.GREEN)),
                    (int)(picture.getInt(x, y, Color.BLUE) - (1-s)*layer.getInt(x,y, Color.BLUE))
            );
        };
    }

    private static PictureFilter getMultiplied(FilterablePicture picture) {
        return (x, y) ->    {/*
            double s = 0.9;
            return SafeColor.getBoundedColor(
                    (int)(picture.getInt(x, y, Color.RED) * (1-s)*layer.getInt(x,y, Color.RED)),
                    (int)(picture.getInt(x, y, Color.GREEN) * (1-s)*layer.getInt(x,y, Color.GREEN)),
                    (int)(picture.getInt(x, y, Color.BLUE) * (1-s)*layer.getInt(x,y, Color.BLUE))
            );*/
            return SafeColor.getBoundedColor(
                    (int)(picture.getInt(x, y, Color.RED) * (layer.getInt(x,y, Color.RED)/255)),
                    (int)(picture.getInt(x, y, Color.GREEN) * (layer.getInt(x,y, Color.GREEN)/255)),
                    (int)(picture.getInt(x, y, Color.BLUE) * (layer.getInt(x,y, Color.BLUE)/255))
            );
        };
    }

    private static PictureFilter getDivided(FilterablePicture picture) {
        return (x, y) ->    {/*
            double s = 0.5;
            double r = (1-s)*layer.getInt(x,y, Color.RED);
            double g = (1-s)*layer.getInt(x,y, Color.GREEN);
            double b = (1-s)*layer.getInt(x,y, Color.BLUE);
            return SafeColor.getBoundedColor(
                    r==0 ? 255: (int)(s*picture.getInt(x, y, Color.RED) / r),
                    r==0 ? 255: (int)(s*picture.getInt(x, y, Color.GREEN) / g),
                    r==0 ? 255: (int)(s*picture.getInt(x, y, Color.BLUE) / b)
            );*/
            double r = layer.getInt(x,y, Color.RED);
            double g = layer.getInt(x,y, Color.GREEN);
            double b = layer.getInt(x,y, Color.BLUE);
            return SafeColor.getBoundedColor(
                    r==0 ? 255: (int)(picture.getInt(x, y, Color.RED) / r),
                    r==0 ? 255: (int)(picture.getInt(x, y, Color.GREEN) / g),
                    r==0 ? 255: (int)(picture.getInt(x, y, Color.BLUE) / b));
        };
    }

    private static PictureFilter getDifferencesBySubtraction(FilterablePicture picture) {
        return (x, y) -> picture.getInt(x,y,null) - layer.getInt(x,y,null) == 0 ? picture.getAt(x,y,null) : Color.RED;
    }

    private static PictureFilter getDifferencesByDivision(FilterablePicture picture) {
        return (x, y) -> (double)picture.getInt(x,y,null) / (double)layer.getInt(x,y,null) == 1. ? picture.getAt(x,y,null) : Color.RED;
    }

}
