package com.example.acg_labs;

import com.example.acg_labs.model.Model3D;
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


public class Model3DApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Model3DApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("ACG");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }

    public static void printVector(double[] vector) {
        for (var coordinate: vector) {
            System.out.print(coordinate + " ");
        }
        System.out.println();
    }
}