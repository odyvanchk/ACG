package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.service.FaceRejection;
import com.example.acg_labs.service.Lighting;
import javafx.scene.effect.Light;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.List;

public class Object3DDrawer implements Drawer {


    public void draw(List<List<InfoComponent>> faces,
                     double[][] vertexes, double[][] rVertexes,
                     double[][] normalVertexes, double[][] normalRVertexes,
                     PixelWriter px) {

        for (var face : faces) {
            Color color = Color.rgb(face.get(0).getColor()[0],
                    face.get(0).getColor()[1],
                    face.get(0).getColor()[2]);
            int first = (int) face.get(0).getChildren().get(0);
            int last = (int) face.get(face.size() - 1).getChildren().get(0);
            for (int i = 0; i < face.size() - 1; i++) {
                int firstIndex = (int) face.get(i).getChildren().get(0);
                int secondIndex = (int) face.get(i + 1).getChildren().get(0);
                BrezenhamDrawer.getInstance().draw((int) rVertexes[firstIndex - 1][0],
                        (int) rVertexes[firstIndex - 1][1],
                        (int) rVertexes[secondIndex - 1][0],
                        (int) rVertexes[secondIndex - 1][1], px, color);
            }
            BrezenhamDrawer.getInstance().draw((int) rVertexes[first - 1][0],
                    (int) rVertexes[first - 1][1],
                    (int) rVertexes[last - 1][0],
                    (int) rVertexes[last - 1][1], px, color);
        }
    }

}
