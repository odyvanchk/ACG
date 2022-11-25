package com.example.acg_labs;

import com.example.acg_labs.controller.TransformService;
import com.example.acg_labs.controller.impl.TransformVertexService;
import com.example.acg_labs.drawer.Drawer;
import com.example.acg_labs.drawer.impl.Object3DDrawerFilled;
import com.example.acg_labs.model.Model3D;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Model3DController implements Initializable {
    @FXML
    private Canvas canvas;
    private Model3D model3D;
    private final Drawer drawer = new Object3DDrawerFilled();
    private final TransformService transformService = new TransformVertexService();
    private double oldX;
    private double oldY;

    public void onMouseDraggedOnCanvas(MouseEvent mouseEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double[][] resultVertexes = transformService.rotateModel(model3D.getVertexesD(),
                oldX - mouseEvent.getSceneX(), oldY - mouseEvent.getSceneY());
        double[][] resultWorldVertexes = transformService.vertexFromModeltoWorld(model3D.getVertexesD());
        double[][] resultNormalVertexes = transformService.normalFromModeltoWorld(model3D.getNormalVertexesD());
        oldX = mouseEvent.getSceneX();
        oldY = mouseEvent.getSceneY();
        drawer.draw(model3D.getFaces(), resultWorldVertexes,
                resultVertexes, resultNormalVertexes,
                model3D.getTexturesD(), model3D,
                canvas.getGraphicsContext2D().getPixelWriter());
    }

    public void onMousePressedOnCanvas(MouseEvent mouseEvent) {
        oldX = mouseEvent.getSceneX();
        oldY = mouseEvent.getSceneY();
    }

    public void onKeyPressedOnCanvas(KeyEvent keyEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double[][] resultVertexes = transformService.translateModel(model3D.getVertexesD(), keyEvent);
        double[][] resultWorldVertexes = transformService.vertexFromModeltoWorld(model3D.getVertexesD());
        double[][] resultNormalVertexes = transformService.normalFromModeltoWorld(model3D.getNormalVertexesD());
        drawer.draw(model3D.getFaces(), resultWorldVertexes,
                resultVertexes, resultNormalVertexes,
                model3D.getTexturesD(), model3D,
                canvas.getGraphicsContext2D().getPixelWriter());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            canvas.setFocusTraversable(true);
            model3D = new Model3D("src/main/resources/models/model4.obj",
                    "src/main/resources/models/diffuse4.jpeg"
                    );

            double[][] resultVertexes = transformService.fromModelToView(model3D.getVertexesD());
            double[][] resultWorldVertexes = transformService.vertexFromModeltoWorld(model3D.getVertexesD());
            double[][] resultNormalVertexes = transformService.normalFromModeltoWorld(model3D.getNormalVertexesD());
            drawer.draw(model3D.getFaces(), resultWorldVertexes,
                    resultVertexes, resultNormalVertexes,
                    model3D.getTexturesD(), model3D,
                    canvas.getGraphicsContext2D().getPixelWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}