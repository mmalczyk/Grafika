package PictureFilter.AreaFilter;

import PictureFilter.PictureFilter;
import RGBImage.FilterablePicture;
import RGBImage.Picture;

/**
 * Created by Magda on 01.12.2016.
 */
public abstract  class AreaFilter implements PictureFilter{
    protected Picture picture;
    protected Picture mask;
    protected int widthRadius;
    protected int heightRadius;

    protected void initialisation(FilterablePicture picture, String maskFile){
        initialisation(picture, MaskGenerator.readMaskFromFile(maskFile));
    }

    protected void initialisation(FilterablePicture picture, Picture mask){
        this.picture = picture.copyPicture();
        this.mask = mask;
        if (mask.height() % 2 != 1 || mask.width() % 2 != 1)
            throw new IllegalArgumentException("A mask doesn't have a center with integer coordinates");
        widthRadius = mask.width()/2;
        heightRadius = mask.height()/2;
    }

}
