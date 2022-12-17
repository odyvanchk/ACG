package com.example.acg_labs.service;

import com.example.acg_labs.math.Calculation;
import com.example.acg_labs.model.Model3D;
import com.example.acg_labs.model.MtlInfo;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class LightingFong {
    private static final LightingFong INSTANCE = new LightingFong();
    private Calculation calc = Calculation.getInstance();
    private BufferedImage normalImage;
    private BufferedImage diffuseImage;
    private BufferedImage specularImage;

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
    private static double[] light = {-1.0, -1.0, -1.0, 0.0};
    private static double[] view = {0.0, 0.0, 5.0, 0.0};
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
        double[] inLight = new double[]{-light[0], -light[1], -light[2], 0.0};
        double temp = Math.max(calc.dotProduct(normal, inLight), 0.0);
        Ld = Color.rgb(Math.min((int) (temp * id[0] * 255 * kDiffuse[0]), 255),
                Math.min((int) (temp * id[1] * 255 * kDiffuse[1]), 255),
                Math.min((int) (temp * id[2] * 255 * kDiffuse[2]), 255));
    }

    private void specular(double[] normal) {
        double LN = calc.dotProduct(normal, light);
        double[] LNN =calc.multiplyVectorByScalar(normal, -2F* LN);
        double[] R = calc.normalizeVector( calc.addVector(light, LNN));

        double temp = Math.pow(Math.max(calc.dotProduct( R, viewDir), 0.0), a);
        Ls = Color.rgb(Math.min((int) (temp * is[0] * 255 * kSpecular[0]), 255),
                Math.min((int) (temp * is[1] * 255 * kSpecular[1]), 255),
                Math.min((int) (temp * is[2] * 255 * kSpecular[2]), 255));
    }

    public Color getColor(double[] vertex, double[] normal, double[] texture, MtlInfo mtl) {
        light = calc.normalizeVector(light);
        normal = calc.normalizeVector(normal);
        viewDir = calc.normalizeVector(calc.subtractVector(view, vertex));
        diffuseImage = mtl.getMapKd();
        specularImage = mtl.getMapKs();
        a = mtl.getNs();

        int x = (int) Math.round((texture[0]) * diffuseImage.getWidth());
        int y = (int) Math.round((1 - texture[1]) * diffuseImage.getHeight());
        if (x >= diffuseImage.getWidth()) {
            x -= diffuseImage.getWidth();
        } else if (x < 0) {
            x += diffuseImage.getWidth();
        }
        if (y >= diffuseImage.getHeight()) {
            y -= diffuseImage.getHeight();
        } else if (y < 0) {
            y += diffuseImage.getHeight();
        }

        var clr = diffuseImage.getRGB(x, y);
        kAmbient[0] = ((clr & 0x00ff0000) >> 16) / 255.0;
        kAmbient[1] = ((clr & 0x0000ff00) >> 8) / 255.0;
        kAmbient[2] = (clr & 0x000000ff) / 255.0;
        kDiffuse[0] = kAmbient[0];
        kDiffuse[1] = kAmbient[1];
        kDiffuse[2] = kAmbient[2];

        clr = specularImage.getRGB(x, y);
        kSpecular[0] = ((clr & 0x00ff0000) >> 16) / 255.0;
        kSpecular[1] = ((clr & 0x0000ff00) >> 8) / 255.0;
        kSpecular[2] = (clr & 0x000000ff) / 255.0;

        ambient();
        diffuse(normal);
        specular(normal);
        int Lred = Math.min((int) ((La.getRed() + Ld.getRed() + Ls.getRed()) * 255), 255);
        int Lgreen = Math.min((int) ((La.getGreen() + Ld.getGreen() + Ls.getGreen())  * 255), 255);
        int Lblue = Math.min((int) ((La.getBlue() + Ld.getBlue() + Ls.getBlue()) * 255), 255);
        return Color.rgb(Lred, Lgreen, Lblue);
    }
}
