package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.service.FaceRejection;
import com.example.acg_labs.service.Lighting;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Object3DDrawerFilled implements Drawer {
    private final FaceRejection faceRejection = FaceRejection.getInstance();
    private final Lighting lighting = Lighting.getInstance();
    private static final int WIN_HEIGHT = 800;
    private static final int WIN_WIDTH = 1300;

    @Override
    public void draw(List<List<InfoComponent>> faces,
                     double[][] vertexes,
                     double[][] normalVertexes,
                     PixelWriter px) {
        List<List<InfoComponent>> newFaces = faceRejection.rejectFacesFromCamera(faces, vertexes);
        lighting.modelLambert(newFaces, normalVertexes);

        double[][] zBuffer = new double[WIN_HEIGHT][WIN_WIDTH];//y x
        for (var line : zBuffer) {
            Arrays.fill(line, 100);
        }

        for (var face : newFaces) {
            Color color = Color.rgb(face.get(0).getColor()[0],
                    face.get(0).getColor()[1],
                    face.get(0).getColor()[2]);

            drawFilledTriangle(vertexes[(int) face.get(0).getChildren().get(0) - 1],
                    vertexes[(int) face.get(1).getChildren().get(0) - 1],
                    vertexes[(int) face.get(2).getChildren().get(0) - 1], color, px, zBuffer);
        }
    }

    public void drawFilledTriangle(double[] p1i, double[] p2i, double[] p3i, Color color, PixelWriter px, double[][] zBuffer) {
        int[] p1 = Arrays.stream(p1i).mapToInt(x -> (int) Math.round(x)).toArray();
        int[] p2 = Arrays.stream(p2i).mapToInt(x -> (int) Math.round(x)).toArray();
        int[] p3 = Arrays.stream(p3i).mapToInt(x -> (int) Math.round(x)).toArray();

        if (p2[1] < p1[1]) {
            int[] tmp = new int[]{p2[0], p2[1], p2[2]};
            p2[0] = p1[0];
            p2[1] = p1[1];
            p2[2] = p1[2];
            p1[0] = tmp[0];
            p1[1] = tmp[1];
            p1[2] = tmp[2];
        }
        if (p3[1] < p1[1]) {
            int[] tmp = new int[]{p3[0], p3[1], p3[2]};
            p3[0] = p1[0];
            p3[1] = p1[1];
            p3[2] = p1[2];
            p1[0] = tmp[0];
            p1[1] = tmp[1];
            p1[2] = tmp[2];
        }
        if (p2[1] > p3[1]) {
            int[] tmp = new int[]{p2[0], p2[1], p2[2]};
            p2[0] = p3[0];
            p2[1] = p3[1];
            p2[2] = p3[2];
            p3[0] = tmp[0];
            p3[1] = tmp[1];
            p3[2] = tmp[2];
        }

        double dx13 = 0, dx12 = 0, dx23 = 0;
        if (p3[1] != p1[1]) {
            dx13 = p3[0] - p1[0];
            dx13 /= p3[1] - p1[1];
        } else {
            dx13 = 0;
        }

        if (p2[1] != p1[1]) {
            dx12 = p2[0] - p1[0];
            dx12 /= (p2[1] - p1[1]);
        } else {
            dx12 = 0;
        }

        if (p3[1] != p2[1]) {
            dx23 = (p3[0] - p2[0]);
            dx23 /= (p3[1] - p2[1]);
        } else {
            dx23 = 0;
        }

        double wx1 = p1[0];
        double wx2 = wx1;
        double _dx13 = dx13;

        if (dx13 > dx12) {
            double tmp = dx13;
            dx13 = dx12;
            dx12 = tmp;
        }

        for (int i = p1[1]; i < p2[1]; i++) {
            for (int j = (int) Math.floor(wx1); j <= Math.ceil(wx2); j++) {
                double currZ = evaluateZ(j, i, p1i[0], p1i[1], p1i[2], p2i[0], p2i[1], p2i[2], p3i[0], p3i[1], p3i[2]);
                if (j >= 0 && j < WIN_WIDTH && i >= 0 && i < WIN_HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += dx13;
            wx2 += dx12;
        }

        if (p1[1] == p2[1] && p1[0] > p2[0]) {
            wx1 = p2[0];
            wx2 = p1[0];
        } else if (p1[0] < p2[0] && p1[1] == p2[1]) {
            wx2 = p2[0];
            wx1 = p1[0];
        }

        if (_dx13 < dx23) {
            double tmp = _dx13;
            _dx13 = dx23;
            dx23 = tmp;
        }

        for (int i = p2[1]; i <= p3[1]; i++) {
            for (int j = (int) Math.floor(wx1); j <= Math.ceil(wx2); j++) {
                double currZ = evaluateZ(j, i, p1i[0], p1i[1], p1i[2], p2i[0], p2i[1], p2i[2], p3i[0], p3i[1], p3i[2]);
                if (j >= 0 && j < WIN_WIDTH && i >= 0 && i < WIN_HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += _dx13;
            wx2 += dx23;
        }
    }

    private double edgeFunction(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1);
    }

    private double evaluateZ(int x, int y, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3) {
        x1 = Math.round(x1);
        y1 = Math.round(y1);
        x2 = Math.round(x2);
        y2 = Math.round(y2);
        x3 = Math.round(x3);
        y3 = Math.round(y3);
        double area = edgeFunction(x1, y1, x2, y2, x3, y3);
        double w1 = edgeFunction(x2, y2, x3, y3, x, y);
        double w2 = edgeFunction(x3, y3, x1, y1, x, y);
        double w3 = edgeFunction(x1, y1, x2, y2, x, y);
        w1 /= area;
        w2 /= area;
        w3 /= area;
        if (w1 >= 0.0 && w2 >= 0.0 && w3 >= 0.0) {
            return (z1 * w1 + z2 * w2 + z3 * w3);
        } else {
            return 100;
        }
    }

}
