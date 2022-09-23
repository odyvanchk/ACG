package com.example.acg_labs.transformator;

import com.example.acg_labs.math.Calculation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CoordinateTransformation {
    private static final CoordinateTransformation INSTANCE = new CoordinateTransformation();
    private Calculation calculator = Calculation.getInstance();
    private double[] eye = {0.0, 0.0, 0.0, 0.0};
    private double[] target = {0.0, 0.0, -1.0, 0.0};
    private double[] up = {0.0, 1.0, 0.0, 0.0};
    private double zNear = 1.0;
    private double zFar = 100.0;
    private double width = 1300.0;
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
        double[][] matrix = {{20, 0.0, 0.0, 0.0},
                            {0.0, 20, 0.0, -30.0},
                            {0.0, 0.0, 20, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};
        double[] temp = rotateCoordinate(vector, -40, 40);
        res = calculator.matrixVectorProduct(matrix, temp);
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

    public double[] translateCoordinate(double[] vector, KeyEvent keyEvent) {
        double[] res = vector;
        double translation = 10.0;
        double[][] matrix = {{1.0, 0.0, 0.0, 0.0},
                            {0.0, 1.0, 0.0, 0.0},
                            {0.0, 0.0, 1.0, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};
        switch (keyEvent.getCode()) {
            case UP -> {
                matrix[1][3] = translation;
                res = calculator.matrixVectorProduct(matrix, vector);
            }
            case DOWN -> {
                matrix[1][3] = -translation;
                res = calculator.matrixVectorProduct(matrix, vector);
            }
            case LEFT -> {
                matrix[0][3] = translation;
                res = calculator.matrixVectorProduct(matrix, vector);
            }
            case RIGHT -> {
                matrix[0][3] = -translation;
                res = calculator.matrixVectorProduct(matrix, vector);
            }
            case Q -> {
                matrix[0][0] *= 0.9;
                matrix[1][1] *= 0.9;
                matrix[2][2] *= 0.9;
                res = calculator.matrixVectorProduct(matrix, vector);
            }
            case W -> {
                matrix[0][0] *= 1.1;
                matrix[1][1] *= 1.1;
                matrix[2][2] *= 1.1;
                res = calculator.matrixVectorProduct(matrix, vector);
            }
        }
        return res;
    }

    public double[] rotateCoordinate(double[] vector, double transX, double transY) {
        double[] res;
        double coef = 0.005;
        transX *= coef;
        transY *= coef;
        double[][] matrixX = {{1.0, 0.0, 0.0, 0.0},
                             {0.0, Math.cos(transY), -Math.sin(transY), 0.0},
                             {0.0, Math.sin(transY), Math.cos(transY), 0.0},
                             {0.0, 0.0, 0.0, 1.0}};
        double[][] matrixY = {{Math.cos(transX), 0.0, -Math.sin(transX), 0.0},
                             {0.0, 1.0, 0.0, 0.0},
                             {Math.sin(transX), 0.0, Math.cos(transX), 0.0},
                             {0.0, 0.0, 0.0, 1.0}};
        double[] temp = calculator.matrixVectorProduct(matrixY, vector);
        res = calculator.matrixVectorProduct(matrixX, temp);
        return res;
    }
}
