package com.example.acg_labs.transformator;

import com.example.acg_labs.util.MatrixMultiplication;
import com.example.acg_labs.util.VectorMultiplication;

public class CoordinateTransformation {
    public static final CoordinateTransformation INSTANCE = new CoordinateTransformation();
    private double[] eye = {0.0, -10.0, 50.0, 0.0};
    private double[] up = {0.0, 1.0, 0.0, 1.0};
    private double zNear = 10.0;
    private double zFar = 100.0;
    private double width = 1000.0;
    private double height = 800.0;
    private double xMin = 0.0;
    private double yMin = 0.0;


    private CoordinateTransformation() {
    }

    public static CoordinateTransformation getInstance() {
        return INSTANCE;
    }

    public double[] fromModelToWorld(double[] vector) {
        double[] res;
        double[][] matrix = {{2, 0.0, 0.5, 0.0},
                            {0.0, 2, 0.0, -10.0},
                            {-0.5, 0.0, 2, -100.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    public double[] fromWorldToCamera(double[] vector) {
        double[] res;
        double[] zAxis = normalize(minusVectors(eye, new double[]{0, 0, -1, 0}));
        double[] xAxis = normalize(VectorMultiplication.multiply(up, zAxis));
        double[] yAxis = up;
        double[][] matrix = {{xAxis[0], xAxis[1], xAxis[2], -productScalarVectors(xAxis, eye)},
                            {yAxis[0], yAxis[1], yAxis[2], -productScalarVectors(yAxis, eye)},
                            {zAxis[0], zAxis[1], zAxis[2], -productScalarVectors(zAxis, eye)},
                            {0.0, 0.0, 0.0, 1.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    public double[] fromCameraToProjection(double[] vector) {
        double[] res;
        double[][] matrix = {{2.0 * zNear / width, 0.0, 0.0, 0.0},
                            {0.0, 2.0 * zNear / height, 0.0, 0.0},
                            {0.0, 0.0, zFar / (zNear - zFar), zNear * zFar / (zNear - zFar)},
                            {0.0, 0.0, -1.0, 0.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    public double[] fromProjectionToViewport(double[] vector) {
        double[] res;
        double[][] matrix = {{width / 2.0, 0.0, 0.0, xMin + width / 2.0},
                            {0.0, - height / 2.0, 0.0, yMin + height / 2.0},
                            {0.0, 0.0, 1.0, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    private double[] minusVectors(double[] minuend, double[] subtrahend) {
        double[] res = new double[4];
        for (int i = 0; i < minuend.length; i++) {
            res[i] = minuend[i] - subtrahend[i];
        }
        return res;
    }

    private double[] normalize(double[] vector) {
        double normal = 0.0;
        for (int i = 0; i < vector.length - 1; i++) {
            normal += Math.pow(vector[i], 2.0);
        }
        normal = Math.sqrt(normal);
        for (int i = 0; i < vector.length - 1; i++) {
            vector[i] /= normal;
        }
        return vector;
    }

    private double productScalarVectors(double[] vector1, double[] vector2) {
        double res = 0.0;
        for (int i = 0; i < vector1.length - 1; i++) {
            res += vector1[i] * vector2[i];
        }
        return res;
    }
}
