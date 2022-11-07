package com.example.acg_labs.service;

import com.example.acg_labs.math.Calculation;
import javafx.scene.paint.Color;

public class LightingFong {
    private static final LightingFong INSTANCE = new LightingFong();

    private static Color ia = Color.rgb(185, 170, 253);
    private static Color id = Color.rgb(120, 170, 253);
    private static Color is = Color.rgb(120, 120, 120);

    private static double a = 0.3;

    private static double kAmbient = 0.5 ;
    private static double kDiffuse = 0.5 ;
    private static double kSpecular = 0.5 ;

    public static Color La;
    public static Color Ld;
    public static Color Ls;
    private static double[] light = new double[] {0, 0, 0, 0};

    private LightingFong() {
    }

    public static LightingFong getInstance() {
        return INSTANCE;
    }

    private void ambient() {
        La = Color.rgb((int)(ia.getRed() * kAmbient), (int)(ia.getGreen() * kAmbient),(int)(ia.getBlue() * kAmbient));
    }

    private void diffuse(double[] normal) {
        double temp = kDiffuse * Calculation.getInstance().dotProduct(normal, light);
        Ld = Color.rgb((int)(temp * id.getRed()), (int)(temp * id.getGreen()), (int)(temp * id.getBlue()));
    }

    private void specular(double[] normal) {
        double temp = kSpecular * Math.pow(Calculation.getInstance().dotProduct(normal, new double[]{0, 0, 0}), a);
        Ls = Color.rgb((int)(temp * is.getRed()), (int)(temp * is.getGreen()), (int)(temp * is.getBlue()));
    }

    public Color getColor(double[] normal) {
        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = (int) ((La.getRed() + Ls.getRed() + Ld.getRed()) / 3);
        int Lgreen = (int) ((La.getGreen() + Ls.getGreen() + Ld.getGreen()) / 3);
        int Lblue = (int) ((La.getBlue() + Ls.getBlue() + Ld.getBlue()) / 3);

        return Color.rgb(Lred, Lgreen, Lblue);
    }
}
