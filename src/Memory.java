import PictureFilter.FilterType;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Magda on 12.11.2016.
 */
class Memory {

    private final File defaultBaseImage = new File("images\\LENA_512.jpg");
    private final File defaultLayerImage = new File("images\\eagle.jpg");
    @SuppressWarnings("FieldCanBeLocal")
    private final Double defaultArg = 0.;

    private final HashMap<FilterType, File> baseImageMemory = new HashMap<>();
    private final HashMap<FilterType, File> layerImageMemory = new HashMap<>();
    private final HashMap<FilterType, Double> argMemory = new HashMap<>();

    Memory() {
        loadMemory();
    }

    private void loadMemory()   {
        argMemory.put(FilterType.Sepia, 40.);

        argMemory.put(FilterType.Rotated, 30.);

        argMemory.put(FilterType.Displaced, 300.);

        argMemory.put(FilterType.Faded, 200.);

        baseImageMemory.put(FilterType.Added, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Added, new File("images\\eagle.jpg"));
        argMemory.put(FilterType.Added, 0.5);

        baseImageMemory.put(FilterType.AddedAndSaturated, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.AddedAndSaturated, new File("images\\eagle.jpg"));
        argMemory.put(FilterType.AddedAndSaturated, 0.8);

        baseImageMemory.put(FilterType.AddedWithTransparency, new File("images\\sphere.jpg"));
        layerImageMemory.put(FilterType.AddedWithTransparency, new File("images\\texture.jpg"));
        argMemory.put(FilterType.AddedWithTransparency, 0.3);

        baseImageMemory.put(FilterType.Subtracted, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Subtracted, new File("images\\eagle.jpg"));

        baseImageMemory.put(FilterType.DifferencesBySubtraction, new File("images\\cinderella1.png"));
        layerImageMemory.put(FilterType.DifferencesBySubtraction, new File("images\\cinderella2.png"));

        baseImageMemory.put(FilterType.DifferencesByDivision, new File("images\\guy1.png"));
        layerImageMemory.put(FilterType.DifferencesByDivision, new File("images\\guy2.png"));

        baseImageMemory.put(FilterType.Multiplied, new File("images\\LENA_512.jpg"));
        layerImageMemory.put(FilterType.Multiplied, new File("images\\circle.jpg"));

        baseImageMemory.put(FilterType.Divided, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Divided, new File("images\\eagle.jpg"));

        argMemory.put(FilterType.Contrast, 1.9);

        baseImageMemory.put(FilterType.SpreadAndSmoothed, new File("images\\kobieta.jpg"));

        baseImageMemory.put(FilterType.SpreadSmoothedGreyscale, new File("images\\kobieta.jpg"));

        argMemory.put(FilterType.UniformSpreadDisruption, 20.);

        argMemory.put(FilterType.UniformSpreadDisruptionBW, 20.);

        argMemory.put(FilterType.NormalDisruption, 20.);

        argMemory.put(FilterType.NormalDisruptionBW, 20.);

        argMemory.put(FilterType.SaltAndPepper, 5.);

        argMemory.put(FilterType.SaltAndPepperBW, 5.);

        argMemory.put(FilterType.MovingAverageBW, 1.);

        argMemory.put(FilterType.MeanAverageBW, 1.);

        baseImageMemory.put(FilterType.SkinDetection, new File ("images\\face1.jpg"));

        baseImageMemory.put(FilterType.GreenPeppers, new File ("images\\peppers.png"));

        baseImageMemory.put(FilterType.RedEyes, new File ("images\\face2.jpg"));

        argMemory.put(FilterType.Binarised, 80.);

        argMemory.put(FilterType.OptimalThreshold, 80.);

        baseImageMemory.put(FilterType.OtsuBinarised, new File ("images\\gazeta.jpg"));

        baseImageMemory.put(FilterType.BensenBinarisation, new File ("images\\gazeta.jpg"));

        argMemory.put(FilterType.BensenBinarisation, 1.);

        baseImageMemory.put(FilterType.MixedBinarisation, new File ("images\\gazeta.jpg"));

        argMemory.put(FilterType.MixedBinarisation, 90.);

        baseImageMemory.put(FilterType.Erosion, new File("images\\morfology\\figury.png"));

        argMemory.put(FilterType.Erosion, 1.);

        argMemory.put(FilterType.ErosionCircularMask, 2.);

        baseImageMemory.put(FilterType.Dilation, new File("images\\morfology\\figury.png"));

        argMemory.put(FilterType.Dilation, 1.);

        argMemory.put(FilterType.DilationCircularMask, 2.);

        baseImageMemory.put(FilterType.Open, new File("images\\morfology\\figury.png"));

        argMemory.put(FilterType.Open, 2.);

        baseImageMemory.put(FilterType.Close, new File("images\\morfology\\figury.png"));

        argMemory.put(FilterType.Close, 2.);

        argMemory.put(FilterType.ColorErosion, 1.);

        argMemory.put(FilterType.ColorDilation, 1.);

    }

    private File getDefaultBaseImage() {
        return defaultBaseImage;
    }

    File getDefaultLayerImage() {
        return defaultLayerImage;
    }

    private Double getDefaultArg() {
        return defaultArg;
    }

    File baseGet(FilterType key)    {
        if (baseImageMemory.containsKey(key))
            return baseImageMemory.get(key);
        else
            return getDefaultBaseImage();
    }

    File layerGet(FilterType key)    {
        if (layerImageMemory.containsKey(key))
            return layerImageMemory.get(key);
        else
            return getDefaultLayerImage();
    }

    public Double argGet(FilterType key)    {
        if (argMemory.containsKey(key))
            return argMemory.get(key);
        else
            return getDefaultArg();
    }

    public void basePut(FilterType key, File value)    {
        if(baseImageMemory.containsKey(key))
            baseImageMemory.remove(key);
        baseImageMemory.put(key, value);
    }

    public void layerPut(FilterType key, File value)    {
        if(layerImageMemory.containsKey(key))
            layerImageMemory.remove(key);
        layerImageMemory.put(key, value);
    }

    public void argPut(FilterType key, Double value)    {
        if(argMemory.containsKey(key))
            argMemory.remove(key);
        argMemory.put(key, value);
    }

    public void argReset(FilterType key)    {
       argPut(key, getDefaultArg());
    }

}
