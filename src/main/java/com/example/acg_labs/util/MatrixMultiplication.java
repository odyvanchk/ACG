package com.example.acg_labs.util;

import com.example.acg_labs.entity.InfoComponent;


public class MatrixMultiplication {

    private MatrixMultiplication() {
    }

    public static Double[] multiply(Double[] vector, Double[][] matrix) {
        Double[] res = new Double[4];
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < matrix[i].length - 1; j++) {
                res[i] += vector[j] * matrix[i][j];
            }
            res[i] += matrix[i][matrix.length];
        }
        return res;
    }
}
