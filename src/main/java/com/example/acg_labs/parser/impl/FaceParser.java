package com.example.acg_labs.parser.impl;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.entity.impl.FaceComponent;
import com.example.acg_labs.parser.ObjParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FaceParser implements ObjParser {
    private static final FaceParser INSTANCE = new FaceParser();

    private FaceParser() {
    }

    public static FaceParser getInstance() {
        return INSTANCE;
    }

    @Override
    public List<List<InfoComponent>> parse(String path, String type) throws IOException {
        List<List<InfoComponent>> res = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(type + " ")) {
                    var vertex = new ArrayList<InfoComponent>();
//                    res.add(vertex);

                    var splitLine = line.split(" ");
                    for (int i = 1; i < splitLine.length; i++) {
                        var vert = new FaceComponent();
                        var splitFace = splitLine[i].split("/");
                        for (String faceVertex : splitFace) {
                            vert.add(Integer.valueOf(faceVertex));
                        }

                        vertex.add(vert);
                    }
                    for (int i = 1; i < vertex.size() - 1; i++) {
                        var triange = new ArrayList<InfoComponent>();
                        triange.add(vertex.get(0));
                        triange.add(vertex.get(i));
                        triange.add(vertex.get(i + 1));

                        res.add(triange);
                    }
                }
            }
            return res;
        }
    }
}


