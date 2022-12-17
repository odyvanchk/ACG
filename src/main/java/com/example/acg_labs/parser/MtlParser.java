package com.example.acg_labs.parser;

import com.example.acg_labs.model.Color;
import com.example.acg_labs.model.MtlInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MtlParser {
    private static final MtlParser INSTANCE = new MtlParser();

    private MtlParser() {
    }

    public static MtlParser getInstance() {
        return INSTANCE;
    }

    public ArrayList<MtlInfo> parse(String objPath) throws IOException {
        String path = null;
        try (BufferedReader br = new BufferedReader(new FileReader(objPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("mtllib")) {
                    path = line.split(" ")[1];
                    break;
                }
            }
        }


        var mtlInfoList = new ArrayList<MtlInfo>();
        Path parentPathMtl = Paths.get(objPath).getParent();
        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(parentPathMtl.toString(), path).toString()))) {
            String line;
            MtlInfo mtlInfo = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                var tokens = line.split(" ");
                switch (tokens[0]) {
                    case "newmtl" -> {
                        if (mtlInfo != null) {
                            mtlInfoList.add(mtlInfo);
                        }
                        mtlInfo = new MtlInfo();
                    }
                    case "Ka" -> mtlInfo.setKa(getDefaultColor(tokens));
                    case "Kd" -> mtlInfo.setKd(getDefaultColor(tokens));
                    case "Ks" -> mtlInfo.setKs(getDefaultColor(tokens));

                    case "Ns" -> mtlInfo.setNs(Float.parseFloat(tokens[1]));

                    case "map_Kd" -> mtlInfo.setMapKd(tokens[1]);
                    case "map_Ka" -> mtlInfo.setMapKa(tokens[1]);
                    case "map_Ks" -> mtlInfo.setMapKs(tokens[1]);
                }
            }
            mtlInfoList.add(mtlInfo);

        } catch (NullPointerException e) {
            System.err.println("wrong file structure");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mtlInfoList;
    }

    Color getDefaultColor(String[] tokens) {
        return new Color(Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2]),
                        Float.parseFloat(tokens[3]));
    }
}
