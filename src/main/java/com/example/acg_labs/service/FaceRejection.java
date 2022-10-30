package com.example.acg_labs.service;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.math.Calculation;

import java.util.ArrayList;
import java.util.List;

public class FaceRejection {
    private static final FaceRejection INSTANCE = new FaceRejection();
    private Calculation calculator = Calculation.getInstance();
    private double[] eye = {0.0, 0.0, 1.0, 0.0};

    private FaceRejection() {
    }

    public static FaceRejection getInstance() {
        return INSTANCE;
    }

    public List<List<InfoComponent>> rejectFacesFromCamera(List<List<InfoComponent>> faces, double[][] vertexes) {
        List<List<InfoComponent>> res = new ArrayList<>();
        for (var face: faces) {
            double[][] triangle = new double[5][4];
            triangle[1] = vertexes[(int) face.get(0).getChildren().get(0) - 1];
            triangle[2] = vertexes[(int) face.get(1).getChildren().get(0) - 1];
            triangle[3] = vertexes[(int) face.get(2).getChildren().get(0) - 1];
            triangle[0] = triangle[3];
            triangle[4] = triangle[1];
            int i = 0;
            double cos = -1.0;
            double[] vertexAB = new double[4];
            double[] vertexCB = new double[4];
            while ((cos >= 1 || cos <= 0) && i < 3) {
                i++;
                vertexAB = calculator.subtractVector(triangle[i - 1], triangle[i]);
                vertexCB = calculator.subtractVector(triangle[i + 1], triangle[i]);
                cos = calculator.findCosBetweenVectors(vertexAB, vertexCB);
            }
//            double[] normal = calculator.crossProduct(vertexAB, vertexCB);
//            double[] vector = calculator.subtractVector(triangle[i], eye);
//            cos = calculator.findCosBetweenVectors(normal, vector);
//            if (cos >= 0 && cos < 1) {
//                res.add(face);
//            }
//            double dot = calculator.dotProduct(normal, vector);
            double dot = vertexAB[1] * vertexCB[0] - vertexAB[0] * vertexCB[1];
            if (dot < 0.0) {
                res.add(face);
            }
        }
        return res;
    }
}
