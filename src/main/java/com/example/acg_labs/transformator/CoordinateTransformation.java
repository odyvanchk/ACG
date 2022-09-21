package com.example.acg_labs.transformator;

import com.example.acg_labs.math.Calculation;

public class CoordinateTransformation {
    private static final CoordinateTransformation INSTANCE = new CoordinateTransformation();
    private Calculation calculator = Calculation.getInstance();
    private double[] eye = {0.0, 0.0, 0.0, 0.0};
    private double[] target = {0.0, 0.0, -1.0, 0.0};
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
        double[][] matrix = {{2, 0.0, 0.0, 0.0},
                            {0.0, 2, 0.0, -10.0},
                            {0.0, 0.0, 2, -100.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public double[] fromWorldToCamera(double[] vector) {
        double[] res;
        double[] zAxis = calculator.normalizeVector(calculator.subtractVector(eye, target));
        double[] xAxis = calculator.normalizeVector(calculator.crossProduct(up, zAxis));
        double[] yAxis = up;
        double[][] matrix = {{xAxis[0], xAxis[1], xAxis[2], -calculator.dotProduct(xAxis, eye)},
                            {yAxis[0], yAxis[1], yAxis[2], -calculator.dotProduct(yAxis, eye)},
                            {zAxis[0], zAxis[1], zAxis[2], -calculator.dotProduct(zAxis, eye)},
                            {0.0, 0.0, 0.0, 1.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public double[] fromCameraToProjection(double[] vector) {
        double[] res;
        double[][] matrix = {{2.0 * zNear / width, 0.0, 0.0, 0.0},
                            {0.0, 2.0 * zNear / height, 0.0, 0.0},
                            {0.0, 0.0, zFar / (zNear - zFar), zNear * zFar / (zNear - zFar)},
                            {0.0, 0.0, -1.0, 0.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public double[] fromProjectionToViewport(double[] vector) {
        double[] res;
        double[][] matrix = {{width / 2.0, 0.0, 0.0, xMin + width / 2.0},
                            {0.0, - height / 2.0, 0.0, yMin + height / 2.0},
                            {0.0, 0.0, 1.0, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }
}
