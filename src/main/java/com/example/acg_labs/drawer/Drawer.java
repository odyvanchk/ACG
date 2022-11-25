package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import com.example.acg_labs.model.Model3D;
import javafx.scene.image.PixelWriter;

import java.util.List;

public interface Drawer {
    void draw(List<List<InfoComponent>> faces,
              double[][] worldVertexes,
              double[][] viewVertexes,
              double[][] viewNormalVertexes,
              double[][] textures,
              Model3D model3D,
              PixelWriter px);
}
