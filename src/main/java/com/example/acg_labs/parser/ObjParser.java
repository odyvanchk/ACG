package com.example.acg_labs.parser;

import com.example.acg_labs.entity.InfoComponent;

import java.io.IOException;
import java.util.ArrayList;

public interface ObjParser {

    String VERTEX = "v";
    String TEXTURE_VERTEX = "vt";
    String NORMAL_VECTOR = "vn";
    String FACE = "f";


    ArrayList<ArrayList<InfoComponent>> parse(String path, String type) throws IOException;
    
}
