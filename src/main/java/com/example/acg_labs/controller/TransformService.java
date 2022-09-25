package com.example.acg_labs.controller;

import com.example.acg_labs.model.Model3D;
import javafx.scene.input.KeyEvent;


public interface TransformService {

    double[][] fromModelToView(double[][] vertexes);

    double[][] translateModel(Model3D model3D, KeyEvent keyEvent);

    double[][] rotateModel(Model3D model3D, double transX, double transY);
}
