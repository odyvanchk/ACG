package com.example.acg_labs.drawer.impl;

import com.example.acg_labs.drawer.Drawer;
import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.math.Calculation;
import com.example.acg_labs.service.FaceRejection;
import com.example.acg_labs.service.LightingFong;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Object3DDrawerFilled implements Drawer {
    private final FaceRejection faceRejection = FaceRejection.getInstance();
    private final LightingFong lightingFong = LightingFong.getInstance();
    private static final int WIN_HEIGHT = 800;
    private static final int WIN_WIDTH = 1300;

    @Override
    public void draw(List<List<InfoComponent>> faces,
                     double[][] viewVertexes,
                     double[][] viewNormalVertexes,
                     PixelWriter px) {
        List<List<InfoComponent>> newFaces = faceRejection.rejectFacesFromCamera(faces, viewVertexes);

        double[][] zBuffer = new double[WIN_HEIGHT][WIN_WIDTH];//y x
        for (var line : zBuffer) {
            Arrays.fill(line, 100);
        }

        for (var face : newFaces) {
            drawFilledTriangle(viewVertexes[(int) face.get(0).getChildren().get(0) - 1],
                    viewVertexes[(int) face.get(1).getChildren().get(0) - 1],
                    viewVertexes[(int) face.get(2).getChildren().get(0) - 1],
                    viewNormalVertexes[(int) face.get(0).getChildren().get(2) - 1],
                    viewNormalVertexes[(int) face.get(1).getChildren().get(2) - 1],
                    viewNormalVertexes[(int) face.get(2).getChildren().get(2) - 1], px, zBuffer);
        }
    }

    public void drawFilledTriangle(double[] vertex1i, double[] vertex2i, double[] vertex3i,
                                   double[] normalVertex1i, double[] normalVertex2i, double[] normalVertex3i,
                                   PixelWriter px, double[][] zBuffer) {
        int[] vertex1 = Arrays.stream(vertex1i).mapToInt(x -> (int) Math.round(x)).toArray();
        int[] vertex2 = Arrays.stream(vertex2i).mapToInt(x -> (int) Math.round(x)).toArray();
        int[] vertex3 = Arrays.stream(vertex3i).mapToInt(x -> (int) Math.round(x)).toArray();
        double[] w;

        if (vertex2[1] < vertex1[1]) {
            swap(vertex1, vertex2);
        }
        if (vertex3[1] < vertex1[1]) {
            swap(vertex1, vertex3);
        }
        if (vertex2[1] > vertex3[1]) {
            swap(vertex2, vertex3);
        }

        double dx13 = 0, dx12 = 0, dx23 = 0;
        if (vertex3[1] != vertex1[1]) {
            dx13 = vertex3[0] - vertex1[0];
            dx13 /= vertex3[1] - vertex1[1];
        } else {
            dx13 = 0;
        }

        if (vertex2[1] != vertex1[1]) {
            dx12 = vertex2[0] - vertex1[0];
            dx12 /= (vertex2[1] - vertex1[1]);
        } else {
            dx12 = 0;
        }

        if (vertex3[1] != vertex2[1]) {
            dx23 = (vertex3[0] - vertex2[0]);
            dx23 /= (vertex3[1] - vertex2[1]);
        } else {
            dx23 = 0;
        }

        double wx1 = vertex1[0];
        double wx2 = wx1;
        double _dx13 = dx13;

        if (dx13 > dx12) {
            double tmp = dx13;
            dx13 = dx12;
            dx12 = tmp;
        }

        for (int i = vertex1[1]; i < vertex2[1]; i++) {
            for (int j = (int) Math.floor(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIN_WIDTH && i >= 0 && i < WIN_HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        Color color = lightingFong.getColor(evaluateNormalVertex(w, normalVertex1i, normalVertex2i, normalVertex3i));
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += dx13;
            wx2 += dx12;
        }

        if (vertex1[1] == vertex2[1] && vertex1[0] > vertex2[0]) {
            wx1 = vertex2[0];
            wx2 = vertex1[0];
        } else if (vertex1[0] < vertex2[0] && vertex1[1] == vertex2[1]) {
            wx2 = vertex2[0];
            wx1 = vertex1[0];
        }

        if (_dx13 < dx23) {
            double tmp = _dx13;
            _dx13 = dx23;
            dx23 = tmp;
        }

        for (int i = vertex2[1]; i <= vertex3[1]; i++) {
            for (int j = (int) Math.floor(wx1) - 1; j <= Math.ceil(wx2) + 1; j++) {
                w = findBarycentricCoordinates(j, i,
                        vertex1i[0], vertex1i[1],
                        vertex2i[0], vertex2i[1],
                        vertex3i[0], vertex3i[1]);
                double currZ = evaluateZ(w, vertex1i[2], vertex2i[2], vertex3i[2]);
                if (j >= 0 && j < WIN_WIDTH && i >= 0 && i < WIN_HEIGHT) {
                    if (currZ < zBuffer[i][j]) {
                        Color color = lightingFong.getColor(evaluateNormalVertex(w, normalVertex1i, normalVertex2i, normalVertex3i));
                        px.setColor(j, i, color);
                        zBuffer[i][j] = currZ;
                    }
                }
            }
            wx1 += _dx13;
            wx2 += dx23;
        }
    }

    private void swap(int[] first, int[] second) {
        int[] tmp = new int[]{second[0], second[1], second[2]};
        second[0] = first[0];
        second[1] = first[1];
        second[2] = first[2];
        first[0] = tmp[0];
        first[1] = tmp[1];
        first[2] = tmp[2];
    }

    private double edgeFunction(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1);
    }

    private double[] findBarycentricCoordinates(int x, int y,
                                                double x1, double y1,
                                                double x2, double y2,
                                                double x3, double y3) {
        double res[] = new double[]{-1.0, -1.0, -1.0};
        x1 = Math.round(x1);
        y1 = Math.round(y1);
        x2 = Math.round(x2);
        y2 = Math.round(y2);
        x3 = Math.round(x3);
        y3 = Math.round(y3);
        double area = edgeFunction(x1, y1, x2, y2, x3, y3);
        res[0] = edgeFunction(x2, y2, x3, y3, x, y);
        res[1] = edgeFunction(x3, y3, x1, y1, x, y);
        res[2] = edgeFunction(x1, y1, x2, y2, x, y);
        res[0] /= area;
        res[1] /= area;
        res[2] /= area;
        return res;
    }

    private double evaluateZ(double[] w, double z1, double z2, double z3) {
        if (w[0] >= 0.0 && w[1] >= 0.0 && w[2] >= 0.0) {
            return (z1 * w[0] + z2 * w[1] + z3 * w[2]);
        } else {
            return 100;
        }
    }

    private double[] evaluateNormalVertex(double[] w, double[] normal1, double[] normal2, double[] normal3) {
        double[] res = new double[]{0.0, 0.0, 0.0, 0.0};
        res[0] = normal1[0] * w[0] + normal2[0] * w[1] + normal3[0] * w[2];
        res[1] = normal1[1] * w[0] + normal2[1] * w[1] + normal3[1] * w[2];
        res[2] = normal1[2] * w[0] + normal2[2] * w[1] + normal3[2] * w[2];
        res = Calculation.getInstance().normalizeVector(res);
        return res;
    }

}
