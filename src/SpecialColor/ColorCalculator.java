package SpecialColor;
import java.awt.*;



/**
 * Created by Magda on 01.11.2016.
 * Class provides functions that take a SpecialColor object and return another - a modified version of the first one
 */
public class ColorCalculator {

    //private

    private static int getAvg(Color color)  {
        return (color.getRed() + color.getGreen() + color.getBlue())/3;
    }

    //public

    public static Color getRed(Color color)  {
        return new Color(color.getRed(), 0, 0);
    }

    public static Color getGreen(Color color)  {
        return new Color(0, color.getGreen(), 0);
    }

    public static Color getBlue(Color color)  {
        return new Color(0, 0, color.getBlue());
    }

    public static Color getBlackAndWhite(Color color)   {
        int avg = getAvg(color);
        return new Color(avg, avg, avg);
    }

    public static Color getNegative(Color color) {
        return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
    }

    public static Color getSepia(Color color, int W)    {
        int avg = getAvg(color);
        return SafeColor.getBoundedColor(avg + 2*W , avg + W, avg);

    }

    public static Color getFaded(Color color, int N)    {
        return SafeColor.getBoundedColor(color.getRed() + N, color.getGreen() + N, color.getBlue() + N);
    }

    private static Color add(Color color1, Color color2, double s1, double s2)   {
        return SafeColor.getBoundedColor(
                (int)(s1*color1.getRed() + s2*color2.getRed()),
                (int)(s1*color1.getGreen() + s2*color2.getGreen()),
                (int)(s1*color1.getBlue() + s2*color2.getBlue())
        );
    }

    public static Color add(Color color1, Color color2, double s)   {
        return add(color1, color2, s, s-1);
    }

    public static Color addWithSaturation(Color color1, Color color2, double s)   {
        return add(color1, color2, 1, s);
    }

    public static Color addWithTransparency(Color color1, Color color2, double s){
        int r = color1.getRed();
        int g = color1.getGreen();
        int b = color1.getBlue();
        return SafeColor.getBoundedColor(
                r == SafeColor.getUpperLimit() ? r : (int)(r + s*color2.getRed()),
                g == SafeColor.getUpperLimit() ? g : (int)(g + s*color2.getGreen()),
                b == SafeColor.getUpperLimit() ? b : (int)(b + s*color2.getBlue())
        );
    }

    public static Color subtract(Color color1, Color color2)    {
        return SafeColor.getBoundedColor(
                color1.getRed() - color2.getRed(),
                color1.getGreen() - color2.getGreen(),
                color1.getBlue() - color2.getBlue()
        );
    }

    public static Color multiply(Color color1, Color color2)    {
        return SafeColor.getBoundedColor(
                color1.getRed() * (color2.getRed()/255),
                color1.getGreen() * (color2.getGreen()/255),
                color1.getBlue() * (color2.getBlue()/255)
        );
    }

    public static Color divide(Color color1, Color color2)  {
        double r = color2.getRed();
        double g = color2.getGreen();
        double b = color2.getBlue();
        int N = 50;
        return SafeColor.getBoundedColor(
                r==0 ? 255: (int)(color1.getRed() / r) * N,
                r==0 ? 255: (int)(color1.getGreen() / g) * N,
                r==0 ? 255: (int)(color1.getBlue() / b) * N);
    }

    public static Color addWithDifferencesA(Color color1, Color color2, Color highlight)  {
        return color1.getRGB() - color2.getRGB() == 0 ? color1 : highlight;
    }

    public static Color addWithDifferencesB(Color color1, Color color2, Color highlight)  {
        return (double) color1.getRGB() / (double) color2.getRGB() == 1. ? color1 : highlight;
    }

}
