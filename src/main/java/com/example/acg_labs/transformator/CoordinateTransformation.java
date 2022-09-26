package com.example.acg_labs.transformator;

import com.example.acg_labs.math.Calculation;
import javafx.scene.input.KeyEvent;

import static java.lang.Math.*;

public class CoordinateTransformation {
    private static final CoordinateTransformation INSTANCE = new CoordinateTransformation();
    private Calculation calculator = Calculation.getInstance();
    private double[] eye = {0.0, 0.0, 3.0, 0.0};
    private double[] target = {0.0, 0.0, -1.0, 0.0};
    private double[] up = {0.0, 1.0, 0.0, 0.0};
    private double zNear = 400.0;
    private double zFar = 1000.0;
    private double width = 1300.0;
    private double height = 800.0;
    private double xMin = 0.0;
    private double yMin = 0.0;
    private static double angleX = 0.0;
    private static double angleY = 0.0;
    private static double distX = 0.0;
    private static double distY = 0.0;
    private static double scaleX = 1.0;
    private static double scaleY = 1.0;
    private static double scaleZ = 1.0;


    private CoordinateTransformation() {
    }

    public static CoordinateTransformation getInstance() {
        return INSTANCE;
    }

    public double[] fromModelToWorld(double[] vector) {
        double[] res;
        double[][] matrix = {{20.0, 0.0, 0.0, 3.0},
                            {0.0, 20.0, 0.0, 0.0},
                            {0.0, 0.0, 20.0, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public double[] fromWorldToCamera(double[] vector) {
        double[] res;
        double[] zAxis = calculator.normalizeVector(calculator.subtractVector(eye, target));
        double[] xAxis = calculator.normalizeVector(calculator.crossProduct(up, zAxis));
        double[] yAxis = calculator.crossProduct(zAxis, xAxis);
        double[][] matrix = {{xAxis[0], xAxis[1], xAxis[2], -calculator.dotProduct(xAxis, eye)},
                             {yAxis[0], yAxis[1], yAxis[2], -calculator.dotProduct(yAxis, eye)},
                             {zAxis[0], zAxis[1], zAxis[2], -calculator.dotProduct(zAxis, eye)},
                             {0.0,      0.0,      0.0,       1.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public double[] fromCameraToProjection(double[] vector) {
        double[] res;
        double[][] matrix = {{2.0 * zNear / width, 0.0,                  0.0,                   0.0},
                             {0.0,                 2.0 * zNear / height, 0.0,                   0.0},
                             {0.0,                 0.0,                  zFar / (zNear - zFar), zNear * zFar / (zNear - zFar)},
                             {0.0,                 0.0,                  1.0,                   0.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public double[] fromProjectionToViewport(double[] vector) {
        double[] res;
        double[][] matrix = {{width / 2.0, 0.0,            0.0, xMin + width / 2.0},
                            {0.0,          - height / 2.0, 0.0, yMin + height / 2.0},
                            {0.0,          0.0,            1.0, 0.0},
                            {0.0,          0.0,            0.0, 1.0}};
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public void updateTranslateCoordinate(KeyEvent keyEvent) {
        double translation = 0.4;
        switch (keyEvent.getCode()) {
            case UP -> {
                distY += translation;
            }
            case DOWN -> {
                distY -= translation;
            }
            case LEFT -> {
                distX -= translation;
            }
            case RIGHT -> {
                distX += translation;
            }
            case Q -> {
                scaleX *= 0.9;
                scaleY *= 0.9;
                scaleZ *= 0.9;
            }
            case W -> {
                scaleX *= 1.1;
                scaleY *= 1.1;
                scaleZ *= 1.1;
            }
        }
    }

    public double[] translateCoordinate(double[] vector) {
        double[] res = vector;
        double[][] matrix = {{1.0, 0.0, 0.0, 0.0},
                            {0.0, 1.0, 0.0, 0.0},
                            {0.0, 0.0, 1.0, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};

        matrix[0][3] = distX;
        matrix[1][3] = distY;
        matrix[0][0] = scaleX;
        matrix[1][1] = scaleY;
        matrix[2][2] = scaleZ;
        res = calculator.matrixVectorProduct(matrix, vector);
        return res;
    }

    public void updateRotateCoordinate(double transX, double transY) {
        angleX -= transX;
        angleY -= transY;
        double coef = 0.01;
        double angleYDegree = angleY / PI * 180 * coef;
        if (angleYDegree > 89)
            angleY = 89 * PI / 180 / coef;
        if (angleYDegree < -89)
            angleY = - 89 * PI / 180 / coef;
    }

    public double[] rotateCoordinate(double[] vector) {
        double[] res;
        double coef = 0.01;
        double tempX = coef * angleX;
        double tempY = coef * angleY;
        double[][] matrixX = {{1.0, 0.0,          0.0,         0.0},
                              {0.0, cos(tempY),  -sin(tempY), 0.0},
                              {0.0, sin(tempY), cos(tempY), 0.0},
                              {0.0, 0.0,          0.0,         1.0}};
        double[][] matrixY = {{cos(tempX), 0.0, sin(tempX), 0.0},
                              {0.0,         1.0, 0.0,          0.0},
                              {-sin(tempX), 0.0, cos(tempX),  0.0},
                              {0.0,         0.0, 0.0,          1.0}};
        double[] temp = calculator.matrixVectorProduct(matrixY, vector);
        res = calculator.matrixVectorProduct(matrixX, temp);
        return res;
    }
}
