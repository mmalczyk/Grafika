package RGBImage;

import PictureFilter.PictureFilter;

import java.awt.*;

import PictureFilter.FilterType;
import PictureFilter.FilterFactory;

/**
 * Created by Magda on 05.10.2016.
 * Framework for picture operations.
 */
public class FilterablePicture {

    private class SafePictureEdges {

        private int lowerBound = 0;
        private int upperBound;

        private SafePictureEdges(int bound)    {
            upperBound = bound - 1;
        }

        private int getUpperBound() {
            return upperBound + 1;
        }

        private int boundOff(Integer r) {
            return r > lowerBound ? (r < upperBound ? r : upperBound) : lowerBound;
        }
    }

    private Picture picture;

    private PictureFilter filter;
    private SafePictureEdges width;
    private SafePictureEdges height;

    public FilterablePicture(String path, FilterType filterType)   {
        picture = new Picture(path);
        picture.setOriginUpperLeft();
        width = new SafePictureEdges(picture.width());
        height = new SafePictureEdges(picture.height());

        filter = FilterFactory.getFilter(this, filterType);
        picture = applyFilter(filter);
    }

    //private methods
    private Picture applyFilter(PictureFilter filter) {
        if (filter != null) {
            Picture filteredPicture = new Picture(picture.width(), picture.height());
            for (int i = 0; i < picture.width(); i++)
                for (int j = 0; j < picture.height(); j++)
                    filteredPicture.set(i, j, pickColor(i, j));
            return filteredPicture;
        }
        else
            return getPicture();
    }

    private Color pickColor(int i, int j) {
        return filter.filter(i, j);
    }

    //public methods

    public Picture getPicture() {
        return picture;
    }

    public Color getAt(int i, int j)    {
        i = width.boundOff(i);
        j = height.boundOff(j);
        return picture.get(i, j);
    }

    public int width()   {
        return width.getUpperBound();
    }

    public int height()  {
        return height.getUpperBound();
    }



}
