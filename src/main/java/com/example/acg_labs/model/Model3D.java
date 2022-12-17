package com.example.acg_labs.model;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.parser.MtlParser;
import com.example.acg_labs.parser.ObjParser;
import com.example.acg_labs.parser.impl.FaceParser;
import com.example.acg_labs.parser.impl.VertexParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Model3D {

    private double[][] vertexesD;
    private double[][] texturesD;
    private double[][] normalVertexesD;
    private final List<List<InfoComponent>> faces;
    private final List<MtlInfo> mtlInfo;



    public Model3D(String path) throws IOException {
        faces = FaceParser.getInstance().parse(path, ObjParser.FACE);
        var vertex = VertexParser.getInstance().parse(path, ObjParser.VERTEX);
        vertexesD = VertexParser.getInstance().infoComponentToDouble(vertex);
        var normalVertex = VertexParser.getInstance().parse(path, ObjParser.NORMAL_VECTOR);
        normalVertexesD = VertexParser.getInstance().infoComponentToDouble(normalVertex);
        var texture = VertexParser.getInstance().parse(path, ObjParser.TEXTURE_VERTEX);
        mtlInfo = MtlParser.getInstance().parse(path);
        texturesD = VertexParser.getInstance().infoComponentToDouble(texture);

    }


    public List<List<InfoComponent>> getFaces() {
        return faces;
    }

    public double[][] getVertexesD() {
        return vertexesD;
    }

    public double[][] getTexturesD() {
        return texturesD;
    }

    public List<MtlInfo> getMtlInfo() {
        return mtlInfo;
    }

    public double[][] getNormalVertexesD() {
        return normalVertexesD;
    }
}
