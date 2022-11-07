package com.example.acg_labs.service;

import com.example.acg_labs.math.Calculation;
import javafx.scene.paint.Color;

public class LightingFong {
    private static final LightingFong INSTANCE = new LightingFong();

    private static Color ia = Color.rgb(85, 70, 253);
    private static Color id = Color.rgb(120, 170, 253);
    private static Color is = Color.rgb(220, 220, 220);

    private static double a = 0.3;

    private static double kAmbient = 0.15 ;
    private static double kDiffuse = 0.8 ;
    private static double kSpecular = 0.9 ;

    public static Color La;
    public static Color Ld;
    public static Color Ls;
    private static double[] light = new double[] {0.0, 0.5, -0.5, 0};

    private LightingFong() {
    }

    public static LightingFong getInstance() {
        return INSTANCE;
    }

    private void ambient() {
        La = Color.rgb((int)(ia.getRed() * 255 * kAmbient), (int)(ia.getGreen() * 255 * kAmbient),(int)(ia.getBlue() * 255 * kAmbient));
    }

    private void diffuse(double[] normal) {
        double temp = kDiffuse * Calculation.getInstance().dotProduct(normal, light);
        Ld = Color.rgb((int)(temp * id.getRed() * 255), (int)(temp * id.getGreen() * 255), (int)(temp * id.getBlue() * 255));
    }

    private void specular(double[] normal) {
        double temp = kSpecular * Math.pow(Calculation.getInstance().dotProduct(normal, light), a);
        Ls = Color.rgb((int)(temp * is.getRed() * 255), (int)(temp * is.getGreen() * 255), (int)(temp * is.getBlue() * 255));
    }

    public Color getColor(double[] normal) {
        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = (int) ((La.getRed() + Ls.getRed() + Ld.getRed()) / 3 * 255);
        int Lgreen = (int) ((La.getGreen() + Ls.getGreen() + Ld.getGreen()) / 3 * 255);
        int Lblue = (int) ((La.getBlue() + Ls.getBlue() + Ld.getBlue()) / 3 * 255);

        return Color.rgb(Lred, Lgreen, Lblue);
    }
}
