package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Object3DDrawer {

    public void draw(List<List<InfoComponent>> faces, double[][] vertexes, PixelWriter px) {
        for (var face: faces) {
            int first = (int)face.get(0).getChildren().get(0);
            int last = (int)face.get(face.size() - 1).getChildren().get(0);
            for (int i = 0; i < face.size() - 1; i++) {
               int firstIndex = (int)face.get(i).getChildren().get(0);
               int secondIndex = (int)face.get(i + 1).getChildren().get(0);
               BrezenhamDrawer.getInstance().draw((int) vertexes[firstIndex - 1][0],
                       (int) vertexes[firstIndex - 1][1],
                       (int) vertexes[secondIndex - 1][0],
                       (int) vertexes[secondIndex - 1][1], px);
            }
            BrezenhamDrawer.getInstance().draw((int) vertexes[first - 1][0],
                    (int) vertexes[first - 1][1],
                    (int) vertexes[last - 1][0],
                    (int) vertexes[last - 1][1], px
            );
        }
    }
}
