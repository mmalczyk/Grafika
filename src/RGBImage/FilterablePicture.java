package RGBImage;

import java.awt.*;

import PictureFilter.FilterType;
import PictureFilter.FilterFactory;
import PictureFilter.FilterWrapper;
import PictureFilter.PictureFilter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import javax.swing.*;

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

    private FilterWrapper filter;
    private SafePictureEdges width;
    private SafePictureEdges height;

    public FilterablePicture(String path, FilterType filterType, Double arg)   {
        picture = new Picture(path);
        picture.setOriginUpperLeft();
        width = new SafePictureEdges(picture.width());
        height = new SafePictureEdges(picture.height());

        FilterType[] filterTypes = filterType.getAllSubfilters();
        for (FilterType type: filterTypes)    {
            filter = FilterFactory.getFilter(this, type, arg);
            picture = applyFilter(filter);
        }
    }

    public FilterType getFilterType()   {
        return filter.getType();
    }

    //private methods
    private Picture applyFilter(FilterWrapper filter) {
        if (filter != null) {
            Picture filteredPicture = new Picture(picture.width(), picture.height());
            for (int i = 0; i < picture.width(); i++)
                for (int j = 0; j < picture.height(); j++)
                    filteredPicture.set(i, j, pickColor(i, j));
            return filteredPicture;
        }
        else
            return picture;
    }

    private Color pickColor(int i, int j) {
        return filter.filter(i, j);
    }

    //public methods

    public JComponent getJComponent()    {
        if (filter.getType().isHistogram())
            return getHistogram().getPanel();
        else
            return picture.getJLabel();
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

    private PictureHistogram getHistogram()    {
        return new PictureHistogram(this);
    }

    public void save(String fileName)   {
        picture.save(fileName);
    }

}
