package com.example.acg_labs.controller.impl;

import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.model.Model3D;
import com.example.acg_labs.service.CoordinateTransformation;
import javafx.scene.input.KeyEvent;

public class TransformVertexService implements TransformService {
    private CoordinateTransformation coordTrans = CoordinateTransformation.getInstance();

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
    public double[][] translateModel(Model3D model3D, KeyEvent keyEvent) {
        coordTrans.updateTranslateCoordinate(keyEvent);
        var vertexes = model3D.getVertexesD();
        return fromModelToView(vertexes);
    }

    @Override
    public double[][] rotateModel(Model3D model3D, double transX, double transY) {
        coordTrans.updateRotateCoordinate(transX, transY);
        var vertexes = model3D.getVertexesD();
        return fromModelToView(vertexes);
    }

}
