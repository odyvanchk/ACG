package com.example.acg_labs.service;

import com.example.acg_labs.math.Calculation;
import javafx.scene.paint.Color;

public class LightingFong {
    private static final LightingFong INSTANCE = new LightingFong();
    private Calculation calc = Calculation.getInstance();

    private static double[] ia = new double[]{0.1, 0.1, 0.1};
    private static double[] id = new double[]{1.0, 1.0, 1.0};
    private static double[] is = new double[]{1.0, 1.0, 1.0};

    private static double a = 32;

    private static double[] kAmbient = new double[]{0.1, 0.1, 0.1};
    private static double[] kDiffuse = new double[]{0.5, 0.5, 0.5};
    private static double[] kSpecular = new double[]{1.0, 1.0, 1.0};

    public static Color La;
    public static Color Ld;
    public static Color Ls;
    private static double[] light = {30.0, 35.0, -8.0, 0.0};
    private static double[] lightDir;
    private static double[] view = {3.0, 3.0, -1.0, 0.0};
    private static double[] viewDir;

    private LightingFong() {
    }

    public static LightingFong getInstance() {
        return INSTANCE;
    }

    private void ambient() {
        La = Color.rgb((int) (ia[0] * 255 * kAmbient[0]),
                (int) (ia[1] * 255 * kAmbient[1]),
                (int) (ia[2] * 255 * kAmbient[2]));
    }

    private void diffuse(double[] normal) {
        double temp = 0.4 * Math.max(calc.dotProduct(normal, light), 0.0);
        Ld = Color.rgb(Math.min((int) (temp * id[0] * 255 * kDiffuse[0]), 255),
                Math.min((int) (temp * id[1] * 255 * kDiffuse[1]), 255),
                Math.min((int) (temp * id[2] * 255 * kDiffuse[2]), 255));
    }

    private void specular(double[] normal) {
        double LN = calc.dotProduct(lightDir, normal);
        double[] LNN = calc.multiplyVectorByScalar(normal, 2 * LN);
        double[] R = calc.normalizeVector(calc.subtractVector(lightDir, LNN));
        double temp = 0.5 * Math.pow(Math.max(calc.dotProduct(R, viewDir), 0.0), a);
        Ls = Color.rgb(Math.min((int) (temp * is[0] * 255 * kSpecular[0]), 255),
                Math.min((int) (temp * is[1] * 255 * kSpecular[1]), 255),
                Math.min((int) (temp * is[2] * 255 * kSpecular[2]), 255));
    }

    public Color getColor(double[] vertex, double[] normal) {
        //light = calc.normalizeVector(light);
        //view = calc.normalizeVector(view);
        //vertex = calc.normalizeVector(vertex);
        normal = calc.normalizeVector(normal);
        lightDir = calc.normalizeVector(calc.subtractVector(vertex, light));
        viewDir = calc.normalizeVector(calc.subtractVector(vertex, view));
        //lightDir = calc.normalizeVector(calc.subtractVector(light, vertex));
        //viewDir = calc.normalizeVector(calc.subtractVector(view, vertex));
        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = Math.min((int) ((La.getRed() + Ld.getRed()+ Ls.getRed()) * 255), 255);
        int Lgreen = Math.min((int) ((La.getGreen() +  Ld.getGreen() + Ls.getGreen())  * 255), 255);
        int Lblue = Math.min((int) ((La.getBlue() + Ld.getBlue() + Ls.getBlue()) * 255), 255);

        return Color.rgb(Lred, Lgreen, Lblue);
    }
}
