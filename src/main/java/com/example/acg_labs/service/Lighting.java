package com.example.acg_labs.service;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.math.Calculation;

import java.util.List;

public class Lighting {
    private static final Lighting INSTANCE = new Lighting();
    private Calculation calculator = Calculation.getInstance();
    private double[] light = {100.0, 100.0, 50.0, 0};

    private Lighting() {
    }

    public static Lighting getInstance() {
        return INSTANCE;
    }

    public void modelLambert(List<List<InfoComponent>> faces, double[][] normalVertexes) {
        for (var face: faces) {
            double[] color = new double[3];
            for (int i = 0; i < 3; i++) {
                double[] normalVertex = normalVertexes[(int) face.get(i).getChildren().get(2) - 1];
                double cos = calculator.findCosBetweenVectors(light, normalVertex);
                cos++;
                color[i] = cos * 255 / 2;
            }
            int mean = (int) Math.round((color[0] + color[1] + color[2]) / 3);
            int[] meanColor = {mean, mean, mean};
            face.get(0).updateColor(meanColor);
        }
    }
}
