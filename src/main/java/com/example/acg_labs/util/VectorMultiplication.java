package com.example.acg_labs.util;

public class VectorMultiplication {

    private VectorMultiplication() {
    }

    public static double[] multiply(double[] vector1, double[] vector2) {
        double[] res = new double[4];
        res[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        res[1] = -(vector1[0] * vector2[2] - vector1[2] * vector2[0]);
        res[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        res[3] = 1.0;
        return res;
    }
}
