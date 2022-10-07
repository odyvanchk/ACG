package com.example.acg_labs.model;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.parser.ObjParser;
import com.example.acg_labs.parser.impl.FaceParser;
import com.example.acg_labs.parser.impl.VertexParser;

import java.io.IOException;
import java.util.List;

public class Model3D {

    private double[][] vertexesD;
    private double[][] normalVertexesD;
    private final List<List<InfoComponent>> faces;


    public Model3D(String path) throws IOException {
        faces = FaceParser.getInstance().parse(path, ObjParser.FACE);
        var vertex = VertexParser.getInstance().parse(path, ObjParser.VERTEX);
        vertexesD = VertexParser.getInstance().infoComponentToDouble(vertex);
        var normalVertex = VertexParser.getInstance().parse(path, ObjParser.NORMAL_VECTOR);
        normalVertexesD = VertexParser.getInstance().infoComponentToDouble(normalVertex);
    }


    public List<List<InfoComponent>> getFaces() {
        return faces;
    }

    public double[][] getVertexesD() {
        return vertexesD;
    }

    public double[][] getNormalVertexesD() {
        return normalVertexesD;
    }

    public void updateVertexesD(double[][] newVertexesD) {
        vertexesD = newVertexesD;
    }
}
