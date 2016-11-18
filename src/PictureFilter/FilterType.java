package PictureFilter;

import javafx.scene.control.Skin;

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
    SpreadSmoothedGreyscale(28), UniformSpreadDisruption(29), UniformSpreadDisruptionBW(30), NormalDisruption(31),
    NormalDisruptionBW(32), SaltAndPepper(33), SaltAndPepperBW(34), MovingAverage(35), MeanAverage(36), toYCbCr(37),
    Y(38), Cb(39), Cr(40), YCbCrToRGB(41), HLS(42), H(43), L(44), S(45), SkinDetection(46);


    private int value;

    private static String[] names = new String[]{"Original", "Red", "Green", "Blue", "B&W", "Negative", "Sepia",
            "Rotated", "Displaced", "LUT", "Faded", "Added", "AddedAndSaturated", "AddedWithTransparency",
            "Subtracted", "Multiplied", "Divided", "DifferencesBySubtraction", "DifferencesByDivision", "RHistogram",
            "GHistogram", "BHistogram", "GreyScaleHistogram", "Contrast", "LHistogram", "Spread", "Smoothed",
            "SpreadAndSmoothed", "SpreadSmoothedGreyscale", "UniformSpreadDisruption", "UniformSpreadDisruptionB&W",
            "NormalDisruption", "NormalDisruptionBW", "SaltAndPepper", "SaltAndPepperBW", "MovingAverage", "MeanAverage",
            "toYCbCr", "Y", "Cb", "Cr", "YCbCrToRGB", "HLS", "H", "L", "S", "SkinDetection"
    };

    private static FilterType[] mainTypes = new FilterType[]{
            Default, Red, Green, Blue, BlackAndWhite, Negative, Sepia, Rotated, Displaced,
            LUT, Faded, Added, AddedAndSaturated, AddedWithTransparency, Subtracted, Multiplied,
            Divided, DifferencesBySubtraction, DifferencesByDivision, RHistogram, GHistogram, BHistogram,
            GreyScaleHistogram, Contrast, LHistogram, Spread, Smoothed, SpreadAndSmoothed,
            SpreadSmoothedGreyscale, UniformSpreadDisruption, UniformSpreadDisruptionBW, NormalDisruption,
            NormalDisruptionBW, SaltAndPepper, SaltAndPepperBW, MovingAverage, MeanAverage, toYCbCr, YCbCrToRGB, HLS,
            SkinDetection

    };

    public static FilterType[] getMainTypes()   {
        return mainTypes;
    }

    FilterType(int value)   {
        this.value = value;
    }

    public String getName()    {
        return names[value];
    }

    public boolean isCompound()  {
        return EnumSet.of(Added, AddedAndSaturated, AddedWithTransparency, Subtracted, Multiplied, Divided,
                DifferencesByDivision, DifferencesBySubtraction)
                .contains(this);
    }

    public boolean isHistogram()    {
        return EnumSet.of(RHistogram, GHistogram, BHistogram, GreyScaleHistogram, LHistogram).contains(this);
    }

    public boolean hasVariable()    {
        return EnumSet.of(Sepia, Rotated, Displaced, Faded, Added, AddedAndSaturated, AddedWithTransparency,
                Contrast, UniformSpreadDisruption, UniformSpreadDisruptionBW, NormalDisruption, NormalDisruptionBW,
                SaltAndPepper, SaltAndPepperBW, MovingAverage, MeanAverage
                ).contains(this);
    }

    public boolean isLayered()  {
        return EnumSet.of(SpreadAndSmoothed, SpreadSmoothedGreyscale,
                UniformSpreadDisruptionBW, NormalDisruptionBW, SaltAndPepperBW).contains(this);
    }

    public FilterType[] getSubTypes() {
        if (this == toYCbCr)
            return new FilterType[]{Y, Cb, Cr};
        else if (this == YCbCrToRGB)
            return new FilterType[]{Default};
        else if (this == HLS)
            return new FilterType[]{H, L, S};
        else if (this == SkinDetection)
            return new FilterType[]{Default};
        else
            return new FilterType[]{};
    }

    public FilterType[] getAllSubFilters()  {
        if (isLayered())    {
            if (this == SpreadAndSmoothed)
                return new FilterType[]{Smoothed, Spread};
            else if (this == SpreadSmoothedGreyscale)
                return new FilterType[]{Smoothed, Spread, BlackAndWhite};
            else if (this == UniformSpreadDisruptionBW)
                return new FilterType[]{BlackAndWhite, UniformSpreadDisruption};
            else if (this == NormalDisruptionBW)
                return new FilterType[]{BlackAndWhite, NormalDisruption};
            else if (this == SaltAndPepperBW)
                return new FilterType[]{BlackAndWhite, SaltAndPepperBW};
            throw new UnsupportedOperationException("Enum initialization error");
        }
        else
            return new FilterType[]{this};
    }
}
