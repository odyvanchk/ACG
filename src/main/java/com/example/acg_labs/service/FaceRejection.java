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
            double[][] triange = new double[5][4];
            triange[1] = vertexes[(int) face.get(0).getChildren().get(0) - 1];
            triange[2] = vertexes[(int) face.get(1).getChildren().get(0) - 1];
            triange[3] = vertexes[(int) face.get(2).getChildren().get(0) - 1];
            triange[0] = triange[3];
            triange[4] = triange[1];
            int i = 0;
            double cos = -1.0;
            double[] vertexAB = new double[4];
            double[] vertexCB = new double[4];
            while ((cos >= 1 || cos < 0) && i < 3) {
                i++;
                vertexAB = calculator.subtractVector(triange[i - 1], triange[i]);
                vertexCB = calculator.subtractVector(triange[i + 1], triange[i]);
                cos = calculator.findCosDegreeBetweenVectors(vertexAB, vertexCB);
            }
            double[] normal = calculator.crossProduct(vertexCB, vertexAB);
            double dot = calculator.dotProduct(normal, eye);
            if (dot < 0) {
                res.add(face);
            }
        }
        return res;
    }
}
