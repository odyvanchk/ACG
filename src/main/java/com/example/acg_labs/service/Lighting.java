package com.example.acg_labs.service;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.math.Calculation;

import java.util.List;

public class Lighting {
    private static final Lighting INSTANCE = new Lighting();
    private Calculation calculator = Calculation.getInstance();
    private double[] light = {0, 100, 100};

    private Lighting() {
    }

    public static Lighting getInstance() {
        return INSTANCE;
    }

    public void modelLambert(List<List<InfoComponent>> faces, double[][] normalVertex) {
        for (var face: faces) {
            double[] color = new double[3];
            for (int i = 0; i < 3; i++) {
                double[] vertex = normalVertex[(int) face.get(i).getChildren().get(2) - 1];
                double cos = Math.abs(calculator.findCosDegreeBetweenVectors(light, vertex));
                color[i] = (1 - cos) * 255;
            }
            int mean = (int) (color[0] + color[1] + color[2]) / 3;
            int[] meanColor = {mean, mean, mean};
            face.get(0).updateColor(meanColor);
        }
    }
}
