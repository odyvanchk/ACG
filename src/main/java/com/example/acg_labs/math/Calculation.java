package com.example.acg_labs.math;

public class Calculation {
    private final static Calculation INSTANCE = new Calculation();

    private Calculation() {
    }

    public static Calculation getInstance() {
        return INSTANCE;
    }

    public double dotProduct(double[] vector1, double[] vector2) {
        double res = 0.0;
        for (int i = 0; i < vector1.length - 1; i++) {
            res += vector1[i] * vector2[i];
        }
        return res;
    }

    public double[] crossProduct(double[] vector1, double[] vector2) {
        double[] res = new double[4];
        res[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        res[1] = -(vector1[0] * vector2[2] - vector1[2] * vector2[0]);
        res[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        res[3] = 1.0;
        return res;
    }

    public double[] matrixVectorProduct(double[][] matrix, double[] vector) {
        double[] res = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                res[i] += vector[j] * matrix[i][j];
            }
        }
        return res;
    }

    public double[][] matrixesProduct(double[][] matrix1, double[][] matrix2) {
        double[][] res = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2.length; j++) {
                res[i][j] = matrix1[i][0] * matrix2[0][j]
                        + matrix1[i][1] * matrix2[1][j]
                        + matrix1[i][2] * matrix2[2][j]
                        + matrix1[i][3] * matrix2[3][j];
            }
        }
        return res;
    }

    public double[] normalizeVector(double[] vector) {
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

    public double[] subtractVector(double[] minuend, double[] subtrahend) {
        double[] res = new double[4];
        for (int i = 0; i < minuend.length - 1; i++) {
            res[i] = minuend[i] - subtrahend[i];
        }
        return res;
    }

    public double findVertexLength(double[] vector) {
        double res = 0.0;
        for (int i = 0; i < vector.length - 1; i++) {
            res += Math.pow(vector[i], 2.0);
        }
        res = Math.sqrt(res);
        return res;
    }

    public double findCosDegreeBetweenVectors(double[] vector1, double[] vector2) {
        double res;
        double numerator = dotProduct(vector1, vector2);
        double denumerator = findVertexLength(vector1) * findVertexLength(vector2);
        res = numerator / denumerator;
        return res;
    }
}
