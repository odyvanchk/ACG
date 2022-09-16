package com.example.acg_labs.parser.impl;

import com.example.acg_labs.entity.impl.FaceComponent;
import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.parser.ObjParser;

import java.io.*;
import java.util.ArrayList;

public class FaceParser implements ObjParser {
    private static final FaceParser INSTANCE = new FaceParser();

    private FaceParser() {}

    public static FaceParser getInstance() {
        return INSTANCE;
    }

    @Override
    public ArrayList<ArrayList<InfoComponent>> parse(String path, String type) throws IOException {
        var res = new ArrayList<ArrayList<InfoComponent>>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(type + " ")) {
                    var vertex = new ArrayList<InfoComponent>();
                    res.add(vertex);

                    var splitLine = line.split(" ");
                    for (int i = 1; i < splitLine.length; i++) {
                        var vert = new FaceComponent();
                        var splitFace = splitLine[i].split("/");
                        for (String faceVertex: splitFace) {
                            vert.add(Integer.valueOf(faceVertex));
                        }
                        vertex.add(vert);
                    }
                }
            }
            return res;
        }
    }
}


