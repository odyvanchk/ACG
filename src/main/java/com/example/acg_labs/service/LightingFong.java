package com.example.acg_labs.service;

import com.example.acg_labs.math.Calculation;
import javafx.scene.paint.Color;

public class LightingFong {
    private static final LightingFong INSTANCE = new LightingFong();
    private Calculation calc = Calculation.getInstance();

    private static Color ia = Color.rgb(255, 0, 255);
    private static Color id = Color.rgb(255, 200, 255);
    private static Color is = Color.rgb(255, 255, 255);

    private static double a = 5;

    private static double kAmbient = 0.1;
    private static double kDiffuse = 0.5;
    private static double kSpecular = 0.5;

    public static Color La;
    public static Color Ld;
    public static Color Ls;
    private static double[] light = {10.0, 1.0, -0.5, 0.0};
    private static double[] lightDir;
    private static double[] view = {0.0, 0.0, 0.0, 0.0};
    private static double[] viewDir;

    private LightingFong() {
    }

    public static LightingFong getInstance() {
        return INSTANCE;
    }

    private void ambient() {
        La = Color.rgb((int) (ia.getRed() * 255 * kAmbient),
                (int) (ia.getGreen() * 255 * kAmbient),
                (int) (ia.getBlue() * 255 * kAmbient));
    }

    private void diffuse(double[] normal) {
        double temp = kDiffuse * Math.max(calc.dotProduct(normal, lightDir), 0.0);
        Ld = Color.rgb(Math.min((int) (temp * id.getRed() * 255), 255),
                Math.min((int) (temp * id.getGreen() * 255), 255),
                Math.min((int) (temp * id.getBlue() * 255), 255));
    }

    private void specular(double[] normal) {
        double LN = calc.dotProduct(lightDir, normal);
        double[] LNN = calc.multiplyVectorByScalar(normal, 2 * LN);
        double[] R = calc.normalizeVector(calc.subtractVector(lightDir, LNN));
        double temp = kSpecular * Math.pow(Math.max(calc.findCosBetweenVectors(R, viewDir), 0.0), a);
        Ls = Color.rgb(Math.min((int) (temp * is.getRed() * 255), 255),
                Math.min((int) (temp * is.getGreen() * 255), 255),
                Math.min((int) (temp * is.getBlue() * 255), 255));
    }

    public Color getColor(double[] vertex, double[] normal) {
        light = calc.normalizeVector(light);
        //view = calc.normalizeVector(view);
        vertex = calc.normalizeVector(vertex);
        normal = calc.normalizeVector(normal);
        lightDir = calc.normalizeVector(calc.subtractVector(light, vertex));
        viewDir = calc.normalizeVector(calc.subtractVector(view, vertex));
        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = Math.min((int) ((La.getRed() + Ld.getRed() + Ls.getRed()) * 255), 255);
        int Lgreen = Math.min((int) ((La.getGreen() + Ld.getGreen() + Ls.getGreen())  * 255), 255);
        int Lblue = Math.min((int) ((La.getBlue() + Ld.getBlue() + Ls.getBlue()) * 255), 255);

        return Color.rgb(Lred, Lgreen, Lblue);
    }
}
