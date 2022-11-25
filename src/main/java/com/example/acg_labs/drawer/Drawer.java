package com.example.acg_labs.drawer;

import com.example.acg_labs.entity.InfoComponent;
import javafx.scene.image.PixelWriter;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Drawer {
    void draw(List<List<InfoComponent>> faces,
              double[][] worldVertexes,
              double[][] viewVertexes,
              double[][] viewNormalVertexes,
              double[][] textures,
              BufferedImage diffuse,
              PixelWriter px);
}
