package SpecialColor;

import java.awt.*;
import java.rmi.UnexpectedException;
import java.util.NoSuchElementException;

/**
 * Created by Magda on 17.11.2016.
 */
public class HLSColor {
    private Color color;
    private double H;
    private double L;
    private double S;


    public HLSColor(Color color) {
        this.color = color;
        H = calculateH(color);
        L = calculateL(color);
        S = calculateS(color);
    }

    private double min(Color color) {
        return Math.min(color.getRed(), Math.min(color.getGreen(), color.getBlue()));
    }

    private double max(Color color) {
        return Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue()));
    }

    private double calculateL(Color color) {
        return (max(color)+min(color))/2.;
    }

    private double calculateS(Color color) {
        double max = max(color);
        double min = min(color);
        if (L<0.5)
            return (max-min)/(max+min);
        else
            return (max-min)/(2-(max+min));

    }

    private double calculateH(Color color) {
        double max = max(color);
        double value = (60*(color.getGreen()-color.getBlue()))/S;
        if (max == color.getRed())
            return value;
        if (max == color.getGreen())
            return 120+value;
        return 240+value;
    }

    public Color getRGB()   {
        return color;
    }

    public Color getH() {return SafeColor.getBoundedColor((int)H, (int)H, (int)H);}

    public Color getL() {return SafeColor.getBoundedColor((int)L, (int)L, (int)L);}

    public Color getS() {
        int s = (int) (Math.abs(S)*150);
        return SafeColor.getBoundedColor(s, s, s);}

    public boolean isSkin() {
        return S >= 0.2 && 0.5 < L/S && L/S < 3.0 && H <= 28 || H >= 330;
    }
}
