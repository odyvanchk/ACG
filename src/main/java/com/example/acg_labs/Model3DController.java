package com.example.acg_labs;

import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.controller.impl.TransformVertexService;
import com.example.acg_labs.drawer.Object3DDrawer;
import com.example.acg_labs.model.Model3D;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Model3DController implements Initializable{
    @FXML private Canvas canvas;
    private Model3D model3D;
    private Object3DDrawer drawer = new Object3DDrawer();
    private TransformService transformService = new TransformVertexService();
    private double oldX;
    private double oldY;

    public void onMouseDraggedOnCanvas(MouseEvent mouseEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double[][] resultVertexes = transformService.rotateModel(model3D.getVertexesD(),
                mouseEvent.getSceneX() - oldX, mouseEvent.getSceneY() - oldY);
        oldX = mouseEvent.getSceneX();
        oldY = mouseEvent.getSceneY();
        model3D.updateVertexesD(resultVertexes);
        drawer.draw(model3D.getFaces(), resultVertexes, canvas.getGraphicsContext2D().getPixelWriter());
    }

    public void onMousePressedOnCanvas(MouseEvent mouseEvent) {
        oldX = mouseEvent.getSceneX();
        oldY = mouseEvent.getSceneY();
    }

    public void onKeyPressedOnCanvas(KeyEvent keyEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double[][] resultVertexes = transformService.translateModel(model3D.getVertexesD(), keyEvent);
        model3D.updateVertexesD(resultVertexes);
        drawer.draw(model3D.getFaces(), resultVertexes, canvas.getGraphicsContext2D().getPixelWriter());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            canvas.setFocusTraversable(true);
            model3D = new Model3D("src/main/resources/models/model.obj");

            double[][] resultVertexes = transformService.fromModelToView(model3D.getVertexes());
            model3D.updateVertexesD(resultVertexes);
            drawer.draw(model3D.getFaces(), resultVertexes, canvas.getGraphicsContext2D().getPixelWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}