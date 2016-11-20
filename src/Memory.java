import PictureFilter.FilterType;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Magda on 12.11.2016.
 */
public class Memory {

    private File defaultBaseImage = new File("images\\LENA_512.jpg");
    private File defaultLayerImage = new File("images\\eagle.jpg");
    private Double defaultArg = 0.;

    private HashMap<FilterType, File> baseImageMemory = new HashMap<>();
    private HashMap<FilterType, File> layerImageMemory = new HashMap<>();
    private HashMap<FilterType, Double> argMemory = new HashMap<>();

    public Memory() {
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

    }

    public File getDefaultBaseImage() {
        return defaultBaseImage;
    }

    public File getDefaultLayerImage() {
        return defaultLayerImage;
    }

    public Double getDefaultArg() {
        return defaultArg;
    }

    public File baseGet(FilterType key)    {
        if (baseImageMemory.containsKey(key))
            return baseImageMemory.get(key);
        else
            return getDefaultBaseImage();
    }

    public File layerGet(FilterType key)    {
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
