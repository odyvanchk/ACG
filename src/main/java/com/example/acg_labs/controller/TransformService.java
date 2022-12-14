package com.example.acg_labs.controller;

import javafx.scene.input.KeyEvent;


public interface TransformService {

    double[][] fromModelToView(double[][] vertexes);

    double[][] translateModel(double[][] vertexes, KeyEvent keyEvent);

    double[][] rotateModel(double[][] vertexes, double transX, double transY);

    double[][] changeLight(double[][] vertexes);
}
