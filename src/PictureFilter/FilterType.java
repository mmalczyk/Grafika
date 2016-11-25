package PictureFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;

/**
 * Created by Magda on 13.10.2016.
 */
public enum FilterType {
    Default(0), Red(1), Green(2), Blue(3), BlackAndWhite(4), Negative(5), Sepia(6), Rotated(7), Displaced(8),
    LUT(9), Faded(10), Added(11), AddedAndSaturated(12), AddedWithTransparency(13), Subtracted(14), Multiplied(15),
    Divided(16), DifferencesBySubtraction(17), DifferencesByDivision(18), RHistogram(19), GHistogram(20), BHistogram(21),
    GreyScaleHistogram(22), Contrast(23), LHistogram(24), Spread(25), Smoothed(26), SpreadAndSmoothed(27),
    SpreadSmoothedGreyscale(28), UniformSpreadDisruption(29), UniformSpreadDisruptionBW(30), NormalDisruption(31),
    NormalDisruptionBW(32), SaltAndPepper(33), SaltAndPepperBW(34), MovingAverageBW(35), MeanAverageBW(36), toYCbCr(37),
    Y(38), Cb(39), Cr(40), YCbCrToRGB(41), HLS(42), H(43), L(44), S(45), SkinDetection(46), GreenPeppers(47),
    RedEyes(48), MovingAverage(49), MeanAverage(50), Binarised(51), OptimalThreshold(52), OtsuBinarised(53),
    BensenBinarisation(54), MixedBinarisation(55);


    private final int value;

    private static final String[] names = new String[]{"Original", "Red", "Green", "Blue", "B&W", "Negative", "Sepia",
            "Rotated", "Displaced", "LUT", "Faded", "Added", "AddedAndSaturated", "AddedWithTransparency",
            "Subtracted", "Multiplied", "Divided", "DifferencesBySubtraction", "DifferencesByDivision", "RHistogram",
            "GHistogram", "BHistogram", "GreyScaleHistogram", "Contrast", "LHistogram", "Spread", "Smoothed",
            "SpreadAndSmoothed", "SpreadSmoothedGreyscale", "UniformSpreadDisruption", "UniformSpreadDisruptionB&W",
            "NormalDisruption", "NormalDisruptionBW", "SaltAndPepper", "SaltAndPepperBW", "MovingAverageBW", "MeanAverageBW",
            "toYCbCr", "Y", "Cb", "Cr", "YCbCrToRGB", "HLS", "H", "L", "S", "SkinDetection", "GreenPeppers", "RedEyes",
            "MovingAverage", "MeanAverage", "Binarised", "OptimalThreshold", "OtsuBinarised", "BensenBinarisation",
            "MixedBinarisation"
    };

    private static final ArrayList<FilterType> mainTypes = new ArrayList<FilterType>(Arrays.asList(new FilterType[]{
            Default, Red, Green, Blue, BlackAndWhite, Negative, Sepia, Rotated, Displaced,
            LUT, Faded, Added, AddedAndSaturated, AddedWithTransparency, Subtracted, Multiplied,
            Divided, DifferencesBySubtraction, DifferencesByDivision, RHistogram, GHistogram, BHistogram,
            GreyScaleHistogram, Contrast, LHistogram, Spread, Smoothed, SpreadAndSmoothed,
            SpreadSmoothedGreyscale, UniformSpreadDisruption, UniformSpreadDisruptionBW, NormalDisruption,
            NormalDisruptionBW, SaltAndPepper, SaltAndPepperBW, MovingAverageBW, MeanAverageBW, toYCbCr, YCbCrToRGB, HLS,
            SkinDetection, GreenPeppers, RedEyes, MovingAverage, MeanAverage, Binarised, OptimalThreshold, OtsuBinarised,
            BensenBinarisation, MixedBinarisation
    }));

    public static ArrayList<FilterType> getMainTypes()   {
        Collections.reverse(mainTypes);
        return mainTypes;
    }

    FilterType(int value)   {
        this.value = value;
    }

    public String getName()    {
        return names[value];
    }

    public boolean isPictureCompound()  {
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
                SaltAndPepper, SaltAndPepperBW, MovingAverageBW, MeanAverageBW, MovingAverage, MeanAverage, Binarised,
                OptimalThreshold, BensenBinarisation, MixedBinarisation
                ).contains(this);
    }

    private boolean isFilterLayered()  {
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
        else if (this == GreenPeppers)
            return new FilterType[]{Default};
        else if (this == RedEyes)
            return new FilterType[]{Default};
        else if (this == MovingAverage)
            return new FilterType[]{Default};
        else if (this == MeanAverage)
            return new FilterType[]{Default};
        else
            return new FilterType[]{};
    }

    public FilterType[] getAllLayers()  {
        if (isFilterLayered())    {
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
