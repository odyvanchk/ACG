package com.example.acg_labs.model;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.parser.ObjParser;
import com.example.acg_labs.parser.impl.FaceParser;
import com.example.acg_labs.parser.impl.VertexParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Model3D {

    private double[][] vertexesD;
    private double[][] normalVertexesD;
    private double[][] texturesD;
    private final List<List<InfoComponent>> faces;
    private BufferedImage diffuse;


    public Model3D(String path, String diffusePath) throws IOException {
        faces = FaceParser.getInstance().parse(path, ObjParser.FACE);
        var vertex = VertexParser.getInstance().parse(path, ObjParser.VERTEX);
        vertexesD = VertexParser.getInstance().infoComponentToDouble(vertex);
        var normalVertex = VertexParser.getInstance().parse(path, ObjParser.NORMAL_VECTOR);
        normalVertexesD = VertexParser.getInstance().infoComponentToDouble(normalVertex);
        var texture = VertexParser.getInstance().parse(path, ObjParser.TEXTURE_VERTEX);
        texturesD = VertexParser.getInstance().infoComponentToDouble(texture);
        var diffuse = ImageIO.read(new File(diffusePath));
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

    public double[][] getTexturesD() {
        return texturesD;
    }

    public BufferedImage getDiffuse() {return diffuse; }
}
