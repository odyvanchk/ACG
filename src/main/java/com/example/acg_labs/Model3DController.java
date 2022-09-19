package com.example.acg_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Model3DController implements Initializable {
    @FXML private Canvas img ;

    private GraphicsContext gc ;

    @FXML private void drawCanvas(ActionEvent event) {
        gc.setFill(Color.AQUA);
        gc.fillRect(10,10,100,100);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        gc = img.getGraphicsContext2D();
//        gc.setFill(Color.BLACK);
//        System.out.println("color set to black");
//        gc.fillRect(50, 50, 100, 100);
//        System.out.println("draw rectangle");
    }

    public void onMouseDraggedOnCanvas(MouseEvent mouseEvent) {
        PixelWriter px = img.getGraphicsContext2D().getPixelWriter();
        px.setColor((int) mouseEvent.getSceneX(),(int) mouseEvent.getSceneY(), Color.BLUE);
    }
}