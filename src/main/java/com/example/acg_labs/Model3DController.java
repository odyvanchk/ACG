package com.example.acg_labs;

import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.controller.impl.TransformVertexService;
import com.example.acg_labs.drawer.Object3DDrawer;
import com.example.acg_labs.model.Model3D;
import com.example.acg_labs.util.ListUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Model3DController implements Initializable{
    @FXML private Canvas canvas;
    Model3D model3D;

    public void onMouseDraggedOnCanvas(MouseEvent mouseEvent) {
        PixelWriter px = canvas.getGraphicsContext2D().getPixelWriter();
        px.setColor((int) mouseEvent.getSceneX(),(int) mouseEvent.getSceneY(), Color.BLUE);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            model3D = new Model3D("src/main/resources/models/model.obj");
           // ListUtils.printList(model3D.getVertexes());

            TransformService transformService = new TransformVertexService();
            double[][] resultVertexes = transformService.fromModelToView(model3D.getVertexes());
            Object3DDrawer drawer = new Object3DDrawer();
            drawer.draw(model3D.getFaces(), resultVertexes, canvas.getGraphicsContext2D().getPixelWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}