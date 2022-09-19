package com.example.acg_labs.model;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.parser.ObjParser;
import com.example.acg_labs.parser.impl.FaceParser;
import com.example.acg_labs.parser.impl.VertexParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model3D {
    private final List<List<InfoComponent>> vertexes;

    public List<List<InfoComponent>> getVertexes() {
        return vertexes;
    }

    public List<List<InfoComponent>> getFaces() {
        return faces;
    }

    private final List<List<InfoComponent>> faces;

    public Model3D(String path) throws IOException {
        faces = FaceParser.getInstance().parse(path, ObjParser.FACE);
        vertexes = VertexParser.getInstance().parse(path, ObjParser.VERTEX);
    }
}
