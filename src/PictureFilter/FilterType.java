package PictureFilter;

import java.util.EnumSet;

import static java.util.EnumSet.of;

/**
 * Created by Magda on 13.10.2016.
 */
public enum FilterType {
    Default(0), Red(1), Green(2), Blue(3), BlackAndWhite(4), Negative(5), Sepia(6), Rotated(7), Displaced(8),
    LUT(9), Faded(10), Added(11), AddedAndSaturated(12), AddedWithTransparency(13), Subtracted(14), Multiplied(15),
    Divided(16), DifferencesBySubtraction(17), DifferencesByDivision(18), RHistogram(19), GHistogram(20), BHistogram(21),
    GreyScaleHistogram(22), Contrast(23), LHistogram(24), Spread(25), Smoothed(26), SpreadAndSmoothed(27),
    SpreadSmoothedGreyscale(28);

    private int value;

    private static String[] names = new String[]{"Original", "Red", "Green", "Blue", "B&W", "Negative", "Sepia",
            "Rotated", "Displaced", "LUT", "Faded", "Added", "AddedAndSaturated", "AddedWithTransparency",
            "Subtracted", "Multiplied", "Divided", "DifferencesBySubtraction", "DifferencesByDivision", "RHistogram",
            "GHistogram", "BHistogram", "GreyScaleHistogram", "Contrast", "LHistogram", "Spread", "Smoothed",
            "SpreadAndSmoothed", "SpreadSmoothedGreyscale"
    };

    FilterType(int value)   {
        this.value = value;
    }

    public String getName()    {
        return names[value];
    }

    public boolean isCompound()  {
        return EnumSet.of(Added, AddedAndSaturated, AddedWithTransparency, Subtracted, Multiplied, Divided, DifferencesByDivision,
                DifferencesBySubtraction)
                .contains(this);
    }

    public boolean isHistogram()    {
        return EnumSet.of(RHistogram, GHistogram, BHistogram, GreyScaleHistogram, LHistogram).contains(this);
    }

    public boolean hasVariable()    {
        return EnumSet.of(Sepia, Rotated, Displaced, Faded, Added, AddedAndSaturated, AddedWithTransparency, Contrast).contains(this);
    }

    public boolean isLayered()  {
        return EnumSet.of(SpreadAndSmoothed, SpreadSmoothedGreyscale).contains(this);
    }

    public FilterType[] getAllSubfilters()  {
        if (isLayered())    {
            if (this == SpreadAndSmoothed)
                return new FilterType[]{Smoothed, Spread};
            else if (this == SpreadSmoothedGreyscale)
                return new FilterType[]{Smoothed, Spread, BlackAndWhite};
            throw new UnsupportedOperationException("Enum initialization error");
        }
        else
            return new FilterType[]{this};
    }
}
