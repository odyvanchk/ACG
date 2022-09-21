package com.example.acg_labs.controller.impl;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.transformator.CoordinateTransformation;

import java.util.List;

public class TransformVertexService implements TransformService {

    @Override
    public double[][] fromModelToView(List<List<InfoComponent>> vertexes) {
        double [][] res = new double[vertexes.size()][4];
        int i = 0;
        for (var vertex: vertexes ) {
            var vector = infoComponentToDouble(vertex);

            var coordTrans = CoordinateTransformation.getInstance();
            var world = coordTrans.fromModelToWorld(vector);
            var camera = coordTrans.fromWorldToCamera(world);
            var projection = coordTrans.fromCameraToProjection(camera);
            var viewport = coordTrans.fromProjectionToViewport(projection);

            res[i++] = viewport;
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
