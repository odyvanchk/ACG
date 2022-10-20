package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.service.FaceRejection;
import com.example.acg_labs.service.Lighting;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.List;

public class Object3DDrawerFilled implements Drawer{
    private FaceRejection faceRejection = FaceRejection.getInstance();
    private Lighting lighting = Lighting.getInstance();
    @Override
    public void draw(List<List<InfoComponent>> faces, double[][] vertexes, double[][] normalVertexes, PixelWriter px) {
        List<List<InfoComponent>> newFaces = faceRejection.rejectFacesFromCamera(faces, vertexes);
        lighting.modelLambert(newFaces, vertexes, normalVertexes);

        for (var face: newFaces) {
//             Color color = Color.DARKBLUE;
//             int first = (int) face.get(0).getChildren().get(0);
//             int last = (int) face.get(face.size() - 1).getChildren().get(0);
//             for (int i = 0; i < face.size() - 1; i++) {
//                 int firstIndex = (int) face.get(i).getChildren().get(0);
//                 int secondIndex = (int) face.get(i + 1).getChildren().get(0);
//                 BrezenhamDrawer.getInstance().draw((int) vertexes[firstIndex - 1][0],
//                         (int) vertexes[firstIndex - 1][1],
//                         (int) vertexes[secondIndex - 1][0],
//                         (int) vertexes[secondIndex - 1][1], px, color);
//             }
//             BrezenhamDrawer.getInstance().draw((int) vertexes[first - 1][0],
//                     (int) vertexes[first - 1][1],
//                     (int) vertexes[last - 1][0],
//                     (int) vertexes[last - 1][1], px, color);

             Color color = Color.rgb(face.get(0).getColor()[0],
                    face.get(0).getColor()[1],
                    face.get(0).getColor()[2]);

             drawFilledTriangle(new int[]{(int) vertexes[(int) face.get(0).getChildren().get(0) - 1][0], (int) vertexes[(int) face.get(0).getChildren().get(0) - 1][1]},
                     new int[]{(int) vertexes[(int) face.get(1).getChildren().get(0) - 1][0], (int) vertexes[(int) face.get(1).getChildren().get(0) - 1][1]},
                     new int[]{(int) vertexes[(int) face.get(2).getChildren().get(0) - 1][0], (int) vertexes[(int) face.get(2).getChildren().get(0) - 1][1]}, color, px);
         }

    }

    public void drawFilledTriangle(int[] p1, int[] p2, int[] p3, Color color, PixelWriter px) {
        if (p2[1] < p1[1]) { //y2 < y1
            swap(p2, p1);
        }
        if (p3[1] < p1[1]) {
            swap(p3, p1);
        }
        if (p2[1] > p3[1]) {
            swap(p2, p3);
        }

       double dx13 = 0, dx12 = 0, dx23 = 0;
        // вычисляем приращения
        // в случае, если ординаты двух точек
        // совпадают, приращения
        // полагаются равными нулю
        if (p3[1] != p1[1]) {
            dx13 = p3[0] - p1[0];
            dx13 /= p3[1] - p1[1];
        }
        else
        {
            dx13 = 0;
        }

        if (p2[1] != p1[1]) {
            dx12 = p2[0] - p1[0];
            dx12 /= (p2[1] - p1[1]);
        }
        else
        {
            dx12 = 0;
        }

        if (p3[1] != p2[1]) {
            dx23 = (p3[0] - p2[0]);
            dx23 /= (p3[1] - p2[1]);
        }
        else
        {
            dx23 = 0;
        }
        // "рабочие точки"
        // изначально они находятся в верхней  точке
        double wx1 = p1[0];
        double wx2 = wx1;
        // сохраняем приращение dx13 в другой переменной
        double _dx13 = dx13;

        // упорядочиваем приращения таким образом, чтобы
        // в процессе работы алгоритмы
        // точка wx1 была всегда левее wx2
        if (dx13 > dx12)
        {
            double tmp = dx13;
            dx13 = dx12;
            dx12 = tmp;
        }
        // растеризуем верхний полутреугольник
        for (int i = p1[1]; i < p2[1]; i++){
            // рисуем горизонтальную линию между рабочими
            // точками
            for (int j =(int) Math.round(wx1); j <= wx2; j++){
                px.setColor(j, i, color);
            }
            wx1 += dx13;
            wx2 += dx12;
        }
        // вырожденный случай, когда верхнего полутреугольника нет
        // надо разнести рабочие точки по оси x, т.к. изначально они совпадают
        if (p1[1] == p2[1] && p1[0] > p2[0]){
            wx1 = p2[0];
            wx2 = p1[0];
        }
        else if (p1[0] < p2[0] && p1[1] == p2[1]) {
            wx2 = p2[0];
            wx1 = p1[0];
        }
        // упорядочиваем приращения
        // (используем сохраненное приращение)
        if (_dx13 < dx23)
        {
            double tmp = _dx13;
            _dx13 = dx23;
            dx23 = tmp;
        }
        // растеризуем нижний полутреугольник
        for (int i = p2[1]; i <= p3[1]; i++){
            // рисуем горизонтальную линию между рабочими
            // точками
            for (int j = (int) Math.round(wx1); j <= wx2; j++){
                px.setColor( j, i, color);
            }
            wx1 += _dx13;
            wx2 += dx23;
        }
    }

    private void swap(int[] first, int[] second) {
        int[] tmp = new int[]{ second[0], second[1]};

        second[0] = first[0];
        second[1] = first[1];

        first[0] = tmp[0];
        first[1] = tmp[1];
    }
}
