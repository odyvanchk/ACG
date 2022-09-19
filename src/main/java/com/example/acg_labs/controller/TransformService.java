package com.example.acg_labs.controller;

import com.example.acg_labs.entity.InfoComponent;

import java.util.List;

public interface TransformService {

    double[][] fromModelToView(List<List<InfoComponent>> vertexes);
}
