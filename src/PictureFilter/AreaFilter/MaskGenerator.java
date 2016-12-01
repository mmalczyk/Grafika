package PictureFilter.AreaFilter;

import RGBImage.Picture;
import SpecialColor.SafeColor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Magda on 01.12.2016.
 */
public class MaskGenerator {

    private static final HashMap<Integer, String> maskLibrary;
    static
    {
        maskLibrary = new HashMap<>();
        maskLibrary.put(1, "txt\\mask1.txt");
        maskLibrary.put(2, "txt\\mask2.txt");
        maskLibrary.put(3, "txt\\mask3.txt");
    }

    public static void main(String[] args) {

        readMaskFromFile(args[0]);
    }

    public static String loadMaskAddress(int key)   {
        return maskLibrary.get(key);
    }

    public static Picture readMaskFromFile(String filename) {
        Scanner input;
        Picture picture = null;
        try {
            input = new Scanner(new File(filename));

            int width = input.nextInt();
            int height = input.nextInt();

            picture = new Picture(width, height);

            int counterH = 0;
            int counterW = 0;
            while(input.hasNext() && counterH < height)
            {
                counterW = 0;
                while (input.hasNext() && counterW < width)
                {
                    picture.set(counterW, counterH, SafeColor.binaryColor(input.nextInt()));
                    counterW++;
                }

                counterH++;
            }
            if (counterH != height || counterW != width)
                System.out.println("The number of elements doesn't match the given row and column count.");

        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return picture;
    }

    public static Picture generateCircularPicture(int radius){
        int width = radius*2+1;
        int height = width;
        int center = radius;
        Picture picture = new Picture(width, height);

        for (int i=0; i<width; i++)
            for (int j=0; j<height; j++)
                if ((i-center)*(i-center)+(j-center)*(j-center) < radius*radius)
                    picture.set(i, j, SafeColor.binaryColor(1));
                else
                    picture.set(i,j, SafeColor.binaryColor(0));
        return picture;
    }

}
