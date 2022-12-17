package com.example.acg_labs.parser;

import com.example.acg_labs.model.MtlInfo;
import com.example.acg_labs.parser.impl.VertexParser;

import java.util.ArrayList;

public class MtlParser {
    private static final MtlParser INSTANCE = new MtlParser();

    private MtlParser() {
    }

    public static MtlParser getInstance() {
        return INSTANCE;
    }

    public ArrayList<MtlInfo> parse(String path) {
        return null;
    }
}
