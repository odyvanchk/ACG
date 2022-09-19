package com.example.acg_labs.transformator;

import com.example.acg_labs.util.MatrixMultiplication;
import com.example.acg_labs.util.VectorMultiplication;

public class CoordinateTransformation {
    public static final CoordinateTransformation INSTANCE = new CoordinateTransformation();
    private Double[] eye = {0.0, 0.0, -30.0, 0.0};
    private Double[] up = {0.0, 1.0, 0.0, 1.0};
    private Double zNear = 0.1;
    private Double zFar = 100.0;
    private Double width = 800.0;
    private Double height = 600.0;
    private Double xMin = 0.0;
    private Double yMin = 0.0;


    private CoordinateTransformation() {
    }

    public static CoordinateTransformation getInstance() {
        return INSTANCE;
    }

    public Double[] fromModelToWorld(Double[] vector) {
        Double[] res;
        Double[][] matrix = {{1.0, 0.0, 0.0, 30.0},
                            {0.0, 1.0, 0.0, 30.0},
                            {0.0, 0.0, 1.0, 30.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    public Double[] fromWorldToCamera(Double[] vector) {
        Double[] res;
        Double[] zAxis = normalize(minusVectors(vector, eye));
        Double[] xAxis = normalize(VectorMultiplication.multiply(up, zAxis));
        Double[] yAxis = up;
        Double[][] matrix = {{xAxis[0], xAxis[1], xAxis[2], -productScalarVectors(xAxis, eye)},
                            {yAxis[0], yAxis[1], yAxis[2], -productScalarVectors(yAxis, eye)},
                            {zAxis[0], zAxis[1], zAxis[2], -productScalarVectors(zAxis, eye)},
                            {0.0, 0.0, 0.0, 1.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    public Double[] fromCameraToProjection(Double[] vector) {
        Double[] res;
        Double[][] matrix = {{2.0 * zNear / width, 0.0, 0.0, 0.0},
                            {0.0, 2.0 * zNear / height, 0.0, 0.0},
                            {0.0, 0.0, zFar / (zNear - zFar), zNear * zFar / (zNear - zFar)},
                            {0.0, 0.0, -1.0, 0.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    public Double[] fromProjectionToViewport(Double[] vector) {
        Double[] res;
        Double[][] matrix = {{width / 2.0, 0.0, 0.0, xMin + width / 2.0},
                            {0.0, - height / 2.0, 0.0, yMin + height / 2.0},
                            {0.0, 0.0, 1.0, 0.0},
                            {0.0, 0.0, 0.0, 1.0}};
        res = MatrixMultiplication.multiply(vector, matrix);
        return res;
    }

    private Double[] minusVectors(Double[] minuend, Double[] subtrahend) {
        Double[] res = new Double[4];
        for (int i = 0; i < minuend.length; i++) {
            res[i] = minuend[i] - subtrahend[i];
        }
        return res;
    }

    private Double[] normalize(Double[] vector) {
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

    private Double productScalarVectors(Double[] vector1, Double[] vector2) {
        Double res = 0.0;
        for (int i = 0; i < vector1.length - 1; i++) {
            res += vector1[i] * vector2[i];
        }
        return res;
    }
}
