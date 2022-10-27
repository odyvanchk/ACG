package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import javafx.scene.image.PixelWriter;

import java.util.List;

public interface Drawer {
    void draw(List<List<InfoComponent>> faces,
              double[][] vertexes, double[][] rVertexes,
              double[][] normalVertexes, double[][] normalRVertexes,
              PixelWriter px);
}
