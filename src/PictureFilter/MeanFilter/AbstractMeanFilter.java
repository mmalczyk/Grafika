package PictureFilter.MeanFilter;

import PictureFilter.PictureFilter;
import RGBImage.FilterablePicture;
import RGBImage.Picture;
import SpecialColor.LuminanceComparator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Magda on 12.11.2016.
 */
public abstract class AbstractMeanFilter implements PictureFilter {

    final Picture picture;
    private int radius = 1;
    private Comparator<Color> pixelComparator;

    AbstractMeanFilter(FilterablePicture picture, int radius)  {
        this.picture = picture.copyPicture();
        this.radius = radius;
        pixelComparator = new LuminanceComparator();
    }

    private ArrayList<Color> getArea(int x, int y) {
        ArrayList<Color> pixels = new ArrayList<>();
        for (int i = x-radius; i <= x+radius; i++)
            for (int j = y-radius; j <= y+radius; j++)
                if (i>=0 && j>=0 && i<picture.width() && j<picture.height())
                    pixels.add(picture.get(i, j));
        pixels.sort(pixelComparator);
        return pixels;
    }

    protected abstract Color calculate(ArrayList<Color> pixels, int i, int j);

    public Color filter (int i, int j)  {
        return calculate(getArea(i,j), i, j);
    }

    void setPixelComparator(Comparator<Color> pixelComparator)    {
        this.pixelComparator = pixelComparator;
    }
}
