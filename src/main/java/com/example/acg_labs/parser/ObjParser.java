package com.example.acg_labs.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjParser {

    public static final String VERTEX = "v";
    public static final String TEXTURE_VERTEX = "vt";
    public static final String NORMAL_VECTOR = "vn";
    public static final String FACE = "f";



    public static List<List<Double>> getVertexValues(String path, String exp) throws IOException {
        List<List<Double>> res = new ArrayList<>();
        int j = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(exp + " ")) {
                    var vertex = new ArrayList<Double>();
                    res.add(vertex);

                    var splitLine = line.split(" ");
                    for (int i = 1; i < splitLine.length; i++) {
                        vertex.add(Double.parseDouble(splitLine[i]));
                    }
                }
            }
            return res;
        }
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> getFacesValues(String path, String exp) throws IOException {
        var res = new ArrayList<ArrayList<ArrayList<Integer>>>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(exp + " ")) {
                    var vertex = new ArrayList<ArrayList<Integer>>();
                    res.add(vertex);

                    var splitLine = line.split(" ");
                    for (int i = 1; i < splitLine.length; i++) {
                        var vert = new ArrayList<Integer>();
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


