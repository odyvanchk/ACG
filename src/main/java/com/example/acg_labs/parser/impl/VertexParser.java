package com.example.acg_labs.parser.impl;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.entity.impl.VertexComponent;
import com.example.acg_labs.parser.ObjParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VertexParser implements ObjParser {
    private static final VertexParser INSTANCE = new VertexParser();

    private VertexParser() {}

    public static VertexParser getInstance() {
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
                        res.add(vertex);

                        var splitLine = line.split(" ");
                        for (int i = 1; i < splitLine.length; i++) {
                            vertex.add(new VertexComponent(Double.parseDouble(splitLine[i])));
                        }
                    }
                }
                return res;
            }
    }
}
