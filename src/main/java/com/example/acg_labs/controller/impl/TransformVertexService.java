package com.example.acg_labs.controller.impl;

import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.model.Model3D;
import com.example.acg_labs.transformator.CoordinateTransformation;
import javafx.scene.input.KeyEvent;

public class TransformVertexService implements TransformService {
    private CoordinateTransformation coordTrans = CoordinateTransformation.getInstance();

    @Override
    public double[][] fromModelToView(double[][] vertexes) {
        var res = new double[vertexes.length][4];
        for (int i = 0; i < vertexes.length; i++) {
            var world = coordTrans.fromModelToWorld(vertexes[i]);
            var camera = coordTrans.fromWorldToCamera(world);
            var projection = coordTrans.fromCameraToProjection(camera);
            var viewport = coordTrans.fromProjectionToViewport(projection);

            res[i] = viewport;
        }
        return res;
    }

    @Override
    public double[][] translateModel(Model3D model3D, KeyEvent keyEvent) {
        var vertexes = model3D.getVertexesD();
        double[][] res = new double[vertexes.length][4];
        int i = 0;
        for (var vertex : vertexes) {
            var trans = coordTrans.translateCoordinate(vertex, keyEvent);
            res[i++] = trans;
        }
        model3D.updateVertexesD(res);
        return fromModelToView(res);
    }

    @Override
    public double[][] rotateModel(Model3D model3D, double transX, double transY) {
        var vertexes = model3D.getVertexesD();
        double[][] res = new double[vertexes.length][4];
        int i = 0;
        for (var vertex : vertexes) {
            var rotation = coordTrans.rotateCoordinate(vertex, transX, transY);
            res[i++] = rotation;
        }
        model3D.updateVertexesD(res);
        return fromModelToView(res);
    }

}
