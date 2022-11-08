package com.example.acg_labs.service;

import com.example.acg_labs.math.Calculation;
import javafx.scene.paint.Color;

public class LightingFong {
    private static final LightingFong INSTANCE = new LightingFong();
    private Calculation calc = Calculation.getInstance();

    private static Color ia = Color.rgb(255, 0, 255);
    private static Color id = Color.rgb(255, 0, 255);
    private static Color is = Color.rgb(255, 255, 255);

    private static double a = 64;

    private static double kAmbient = 0.1 ;
    private static double kDiffuse = 0.6 ;
    private static double kSpecular = 0.5 ;

    public static Color La;
    public static Color Ld;
    public static Color Ls;
    private static double[] light = new double[] {0.0, 0.0, -1.0, 0};

    private LightingFong() {
    }

    public static LightingFong getInstance() {
        return INSTANCE;
    }

    private void ambient() {
        La = Color.rgb((int)(ia.getRed() * 255 * kAmbient), (int)(ia.getGreen() * 255 * kAmbient),(int)(ia.getBlue() * 255 * kAmbient));
    }

    private void diffuse(double[] normal) {
        double temp = kDiffuse * Math.max(calc.dotProduct(normal, light), 0.0);
        Ld = Color.rgb((int)(temp * id.getRed() * 255), (int)(temp * id.getGreen() * 255), (int)(temp * id.getBlue() * 255));
    }

    private void specular(double[] normal) {
        double tmp = calc.dotProduct(light, normal);
        tmp = calc.dotProduct(new double[]{tmp, tmp, tmp, 0.0}, normal);
        double[] R = calc.subtractVector(light,
                new double[]{2 * tmp, 2 * tmp, 2 * tmp, 0.0});
        double temp = kSpecular * Math.pow(Math.max(calc.dotProduct(R, new double[]{0.0, 0.0, 0.0}), 0.0), a);
        Ls = Color.rgb((int)(temp * is.getRed() * 255), (int)(temp * is.getGreen() * 255), (int)(temp * is.getBlue() * 255));
    }

    public Color getColor(double[] normal) {
        ambient();
        diffuse(normal);
        specular(normal);
//        int Lred = (int) ((La.getRed() + Ls.getRed() + Ld.getRed()) / 3 * 255);
//        int Lgreen = (int) ((La.getGreen() + Ls.getGreen() + Ld.getGreen()) / 3 * 255);
//        int Lblue = (int) ((La.getBlue() + Ls.getBlue() + Ld.getBlue()) / 3 * 255);
        int Lred = (int) ((La.getRed() + Ls.getRed() + Ld.getRed()) * 255);
        int Lgreen = (int) ((La.getGreen() + Ls.getGreen() + Ld.getGreen())  * 255);
        int Lblue = (int) ((La.getBlue() + Ls.getBlue() + Ld.getBlue()) * 255);

        return Color.rgb(Lred, Lgreen, Lblue);
    }
}
