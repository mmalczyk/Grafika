package PictureFilter;

import RGBImage.FilterablePicture;
import RGBImage.Picture;
import RGBImage.SafeColor;

import java.awt.*;

/**
 * Created by Magda on 23.10.2016.
 */
public abstract class CompoundPictureFilter implements PictureFilter{

    FilterablePicture basePicture;
    FilterablePicture resourcePicture;

    public void loadBasePicture(FilterablePicture picture) {
        basePicture = picture;
    }

    public void loadResourcePicture(FilterablePicture picture) {
        resourcePicture = picture;
    }

    protected Color filter(int x, int y, int baseSaturation, int resourceSaturation)   {
        return SafeColor.getBoundedColor(
                (int) operation(baseSaturation*basePicture.getInt(x, y, Color.RED), resourceSaturation*resourcePicture.getInt(x,y, Color.RED)),
                (int) operation(baseSaturation*basePicture.getInt(x, y, Color.GREEN), resourceSaturation*resourcePicture.getInt(x,y, Color.GREEN)),
                (int) operation(baseSaturation*basePicture.getInt(x, y, Color.BLUE), resourceSaturation*resourcePicture.getInt(x,y, Color.BLUE))
        );
    }

    protected abstract double operation(double a, double b);

}
