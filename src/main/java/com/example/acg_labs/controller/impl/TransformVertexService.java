package com.example.acg_labs.controller.impl;

import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.service.CoordinateTransformation;
import com.example.acg_labs.service.Lighting;
import javafx.scene.input.KeyEvent;

public class TransformVertexService implements TransformService {
    private CoordinateTransformation coordTrans = CoordinateTransformation.getInstance();
    private Lighting lighting = Lighting.getInstance();

    @Override
    public double[][] fromModelToView(double[][] vertexes) {
        var res = new double[vertexes.length][4];
        for (int i = 0; i < vertexes.length; i++) {
            var rotation = coordTrans.rotateCoordinate(vertexes[i]);
            var trans = coordTrans.translateCoordinate(rotation);
            var viewport = coordTrans.transform3DTo2DVector(trans);

            res[i] = viewport;
        }
        return res;
    }

    @Override
    public double[][] translateModel(double[][] vertexes, KeyEvent keyEvent) {
        coordTrans.updateTranslateCoordinate(keyEvent);
        return fromModelToView(vertexes);
    }

    @Override
    public double[][] rotateModel(double[][] vertexes, double transX, double transY) {
        coordTrans.updateRotateCoordinate(transX, transY);
        return fromModelToView(vertexes);
    }
}
