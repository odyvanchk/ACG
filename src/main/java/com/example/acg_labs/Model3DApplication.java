package com.example.acg_labs;

import com.example.acg_labs.parser.impl.FaceParser;
import com.example.acg_labs.parser.ObjParser;
import com.example.acg_labs.parser.impl.VertexParser;
import com.example.acg_labs.transformator.CoordinateTransformation;
import com.example.acg_labs.util.ListUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

public class Model3DApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Model3DApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("ACG");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        var vertexes = VertexParser.getInstance().parse("src/main/resources/models/model.obj", ObjParser.VERTEX);
//        var textureVertexes = FaceParser.getVertexValues("src/main/resources/models/model.obj", ObjParser.TEXTURE_VERTEX);
//        var normalVectors = FaceParser.getVertexValues("src/main/resources/models/model.obj", ObjParser.NORMAL_VECTOR);
//        var faces = FaceParser.getInstance().parse("src/main/resources/models/model.obj", ObjParser.FACE);

        Double[] vector = new Double[4];
        int i = 0;
        for (var coordinate: vertexes.get(0)) {
            vector[i] = Double.parseDouble(String.valueOf(coordinate));
            i++;
        }
        vector[3] = 1.0;
        printVector(vector);
        var coordTrans = CoordinateTransformation.getInstance();
        var world = coordTrans.fromModelToWorld(vector);
        printVector(world);
        var camera = coordTrans.fromWorldToCamera(world);
        printVector(camera);
        var projection = coordTrans.fromCameraToProjection(camera);
        printVector(projection);
        var viewport = coordTrans.fromProjectionToViewport(projection);
        printVector(viewport);

//        ListUtils.printList(Collections.singletonList(vertexes));
//        ListUtils.printList(Collections.singletonList(textureVertexes));
//        ListUtils.printList(Collections.singletonList(normalVectors));
//        ListUtils.printFacesList(faces);
        launch();
    }

    private static void printVector(Double[] vector) {
        for (var coordinate: vector) {
            System.out.print(coordinate + " ");
        }
        System.out.println();
    }
}