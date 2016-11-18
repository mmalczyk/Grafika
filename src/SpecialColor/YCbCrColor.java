package SpecialColor;

import java.awt.*;
/**
 * Created by Magda on 06.11.2016.
 //http://www.equasys.de/colorconversion.html
 */
public class YCbCrColor {
    private Color color;
    private double Y;
    private double Cb;
    private double Cr;


    public YCbCrColor(Color color) {
        this.color = color;
        Y = calculateY(color);
        Cb = calculateCb(color);
        Cr = calculateCr(color);
    }

    private double calculateY(Color color) {
        return 16 + 0.257*color.getRed() + 0.504*color.getGreen()+ 0.098*color.getBlue();
    }

    private double calculateCb(Color color) {
        return 128 + -0.148*color.getRed() + -0.291*color.getGreen()+ 0.439*color.getBlue();
    }

    private double calculateCr(Color color) {
        return 128 + 0.439*color.getRed() + -0.368*color.getGreen()+ -0.071*color.getBlue();
    }

    private int calculateR() {
        return (int) (1.164*(Y-16) + 0.*(Cb-128)+ 1.596*(Cr-128));
    }

    private int calculateG() {
        return (int) (1.164*(Y-16) + -0.392*(Cb-128)+ -0.813*(Cr-128));
    }

    private int calculateB() {
        return (int) (1.164*(Y-16) + 2.017*(Cb-128)+ 0.*(Cr-128));
    }


    public Color getRGB()   {
        return color;
    }

    public Color getY() {return SafeColor.getBoundedColor((int)Y, (int)Y, (int)Y);}

    public Color getCb() {return SafeColor.getBoundedColor((int)Cb, (int)Cb, (int)Cb);}

    public Color getCr() {return SafeColor.getBoundedColor((int)Cr, (int)Cr, (int)Cr);}

    public double getYValue()  {
        return Y;
    }

    public void setYValue(double Y)  {
        this.Y = Y;
        color = SafeColor.getBoundedColor(calculateR(), calculateG(), calculateB());
    }
}
