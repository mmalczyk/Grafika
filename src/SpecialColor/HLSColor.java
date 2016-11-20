package SpecialColor;

import java.awt.*;

/**
 http://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html
 */
public class HLSColor {
    private Color color;
    private double max;
    private double min;
    private double rScaled;
    private double gScaled;
    private double bScaled;
    private double H;
    private double L;
    private double S;


    public HLSColor(Color color) {
        this.color = color;
        scaleRGB();
        max = max(rScaled, gScaled, bScaled);
        min = min(rScaled, gScaled, bScaled);
        L = calculateL();
        S = calculateS();
        H = calculateH(color);
        scaleHLS();
    }

    private void scaleRGB() {
        rScaled = color.getRed()/255.;
        gScaled = color.getGreen()/255.;
        bScaled = color.getBlue()/255.;
    }

    private void scaleHLS(){
        S = 255.*S;
        L = 255.*L;
        H = H/2.;
    }

    private double min(double r, double g, double b) {
        return Math.min(r, Math.min(g, b));
    }

    private double max(double r, double g, double b) {
        return Math.max(r, Math.max(g, b));
    }

    private double calculateL() {
        return (max+min)/2.;
    }

    private double calculateS() {
        if (L<0.5)
            return (max-min)/(max+min);
        else
            return (max-min)/(2.-(max+min));
    }

    private double calculateH(Color color) {
        double value = 0;
        if (max == rScaled)
            value = 60.*(gScaled-bScaled)/S;
        if (max == gScaled)
            value = 120. + 60.*(bScaled-rScaled)/S;
        if (max == bScaled)
            value = 240. + 60.*(rScaled-gScaled)/S;
        if (value<0)
            return 360.+value;
        else
            return value;
    }

    public Color getRGB()   {
        return color;
    }

    public Color getH() {
        return SafeColor.getBoundedColor((int)H, (int)H, (int)H);}

    public Color getL() {
        return SafeColor.getBoundedColor((int)L, (int)L, (int)L);}


    public Color getS() {
        return SafeColor.getBoundedColor((int)S,(int)S,(int)S);
    }

    public boolean isSkin() {
        return S >= 40. && 0.5 < L/S && L/S < 3. && (H <= 14. || H >= 165.);
    }


    public boolean isGreen() {
        return H >= 25. && H <= 70.;
    }


    public boolean isRed() {
        return (L >= 64.) && (S >= 100.) && (L/S > 0.5) && (L/S < 1.5) && ((H <= 7.) || (H >= 162.));
    }


}

























