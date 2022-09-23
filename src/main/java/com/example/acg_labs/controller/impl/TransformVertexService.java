package com.example.acg_labs.controller.impl;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.math.Calculation;
import com.example.acg_labs.transformator.CoordinateTransformation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TransformVertexService implements TransformService {
    private CoordinateTransformation coordTrans = CoordinateTransformation.getInstance();

    @Override
    public double[][] fromModelToView(List<List<InfoComponent>> vertexes) {
        double [][] res = new double[vertexes.size()][4];
        int i = 0;
        for (var vertex: vertexes ) {
            var vector = infoComponentToDouble(vertex);

            var world = coordTrans.fromModelToWorld(vector);
            var camera = coordTrans.fromWorldToCamera(world);
            var projection = coordTrans.fromCameraToProjection(camera);
            var viewport = coordTrans.fromProjectionToViewport(projection);

            res[i++] = viewport;
        }
        return res;
    }

    @Override
    public double[][] translateModel(double[][] vertexes, KeyEvent keyEvent) {
        double [][] res = new double[vertexes.length][4];
        int i = 0;
        for (var vertex: vertexes ) {
            var trans = coordTrans.translateCoordinate(vertex, keyEvent);
            res[i++] = trans;
        }
        return res;
    }

    @Override
    public double[][] rotateModel(double[][] vertexes, double transX, double transY) {
        double [][] res = new double[vertexes.length][4];
        int i = 0;
        for (var vertex: vertexes ) {
            var rotation = coordTrans.rotateCoordinate(vertex, transX, transY);
            res[i++] = rotation;
        }
        return res;
    }

    private double[] infoComponentToDouble (List<InfoComponent> vertex) {
        double[] vector = new double[4];
        int i = 0;
        for (var coordinate : vertex) {
            vector[i] = Double.parseDouble(String.valueOf(coordinate));
            i++;
        }
        vector[3] = 1.0;
        return vector;
    }
}
