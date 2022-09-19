package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Object3DDrawer {

    public void draw(List<List<InfoComponent>> faces, double[][] vertexes, PixelWriter px) {
        int k =0;
//        System.out.println(vertexes.length);
//        for (int i = 0; i < vertexes.length; i++) {
//            px.setColor((int)vertexes[i][0], (int)vertexes[i][1], Color.BLUE);
//        }
int f = 0;
        System.out.println(Arrays.toString(vertexes[683]));
        System.out.println(Arrays.toString(vertexes[685]));
        System.out.println(Arrays.toString(vertexes[660]));
        for (var face: faces) {
            f++;
            System.out.println(f);
            if (f == 956) {
                f = 0;
                System.out.println(face.get(0).getChildren().get(0) + " " + face.get(1).getChildren().get(0) + " " + face.get(2).getChildren().get(0));
            }

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
