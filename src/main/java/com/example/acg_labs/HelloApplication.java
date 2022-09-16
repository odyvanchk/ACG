package com.example.acg_labs;

import com.example.acg_labs.parser.impl.FaceParser;
import com.example.acg_labs.parser.ObjParser;
import com.example.acg_labs.parser.impl.VertexParser;
import com.example.acg_labs.util.ListUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        var vertexes = VertexParser.getInstance().parse("src/main/resources/models/model.obj", ObjParser.VERTEX);
//        var textureVertexes = FaceParser.getVertexValues("src/main/resources/models/model.obj", ObjParser.TEXTURE_VERTEX);
//        var normalVectors = FaceParser.getVertexValues("src/main/resources/models/model.obj", ObjParser.NORMAL_VECTOR);
        var faces = FaceParser.getInstance().parse("src/main/resources/models/model.obj", ObjParser.FACE);

        ListUtils.printList(Collections.singletonList(vertexes));
//        ListUtils.printList(Collections.singletonList(textureVertexes));
//        ListUtils.printList(Collections.singletonList(normalVectors));
        ListUtils.printFacesList(faces);
//        launch();
    }
}