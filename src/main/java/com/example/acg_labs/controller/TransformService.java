package com.example.acg_labs.controller;

import com.example.acg_labs.entity.InfoComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public interface TransformService {

    double[][] fromModelToView(List<List<InfoComponent>> vertexes);

    double[][] translateModel(double[][] vertexes, KeyEvent keyEvent);

    double[][] rotateModel(double[][] vertexes, double transX, double transY);
}
