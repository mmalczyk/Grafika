package PictureFilter;

import RGBImage.FilterablePicture;
import RGBImage.Picture;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Magda on 12.11.2016.
 */
public abstract class AbstractMeanFilter implements PictureFilter {

    protected Picture picture;
    protected static int radius = 1;

    protected AbstractMeanFilter(FilterablePicture picture, int radius)  {
        this.picture = picture.copyPicture();
        this.radius = radius;
    }

    protected ArrayList<Color> getArea(int x, int y) {
        ArrayList<Color> pixels = new ArrayList<>();
        for (int i = x-radius; i <= x+radius; i++)
            for (int j = y-radius; j <= y+radius; j++)
                if (i>=0 && j>=0 && i<picture.width() && j<picture.height())
                    pixels.add(picture.get(i, j));
        return pixels;
    }

    protected abstract Color calculate(ArrayList<Color> pixels, int i, int j);

    public Color filter (int i, int j)  {
        return calculate(getArea(i,j), i, j);
    }
}
