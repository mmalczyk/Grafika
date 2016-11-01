package RGBImage;

import Jama.Matrix;
import PictureFilter.PictureFilter;

import java.awt.*;

import PictureFilter.FilterType;
import PictureFilter.FilterFactory;

/**
 * Created by Magda on 05.10.2016.
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

    private Matrix redPicture;
    private Matrix greenPicture;
    private Matrix bluePicture;
    private Picture picture;

    private PictureFilter filter;
    private SafePictureEdges width;
    private SafePictureEdges height;

    public FilterablePicture(String path, FilterType filterType)   {
        picture = new Picture(path);
        picture.setOriginUpperLeft();
        width = new SafePictureEdges(picture.width());
        height = new SafePictureEdges(picture.height());

        redPicture = new Matrix(width(), height());
        greenPicture = new Matrix(width(), height());
        bluePicture = new Matrix(width(), height());
        splitIntoRGB();
        filter = FilterFactory.getFilter(this, filterType);
        picture = applyFilter(filter);
    }

    //private methods

    private void splitIntoRGB() {
        Color color;
        for (int i=0; i <width(); i++)
            for (int j = 0; j < height(); j++)
            {
                color = picture.get(i, j);
                redPicture.set(i, j, color.getRed());
                greenPicture.set(i, j, color.getGreen());
                bluePicture.set(i, j, color.getBlue());
            }
    }

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


    //protected methods

    protected Color pickColor(int i, int j) {
        return filter.filter(i, j);
    }

    //public methods

    public Picture getPicture() {
        return picture;
    }

    public Color getAt(int i, int j, Color color)  {
        i = width.boundOff(i);
        j = height.boundOff(j);
        if (color == null)
            return picture.get(i, j);
        if (color.equals(Color.RED))
            return new Color((int)redPicture.get(i, j), 0, 0);
        if (color.equals(Color.GREEN))
            return new Color(0, (int)greenPicture.get(i, j), 0);
        if (color.equals(Color.BLUE))
            return new Color(0, 0, (int)bluePicture.get(i, j));
        throw new UnsupportedOperationException("No such color category supported");
    }

    public int getInt(int i, int j, Color color)  {
        i = width.boundOff(i);
        j = height.boundOff(j);
        if (color == null)
            return picture.get(i, j).getRGB();
        if (color.equals(Color.RED))
            return (int) redPicture.get(i, j);
        if (color.equals(Color.GREEN))
            return (int) greenPicture.get(i, j);
        if (color.equals(Color.BLUE))
            return (int) bluePicture.get(i, j);
        throw new UnsupportedOperationException("No such color category supported");
    }

    public int width()   {
        return width.getUpperBound();
    }

    public int height()  {
        return height.getUpperBound();
    }



}
