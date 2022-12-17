package com.example.acg_labs.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MtlInfo {
    private Color normals;
    private Color Ka;
    private Color Kd;
    private Color Ks;
    private double Ns;

    private BufferedImage mapNormals;
    private BufferedImage mapKd;
    private BufferedImage mapKs;

    public double getNs() {
        return Ns;
    }

    public void setNs(double ns) {
        Ns = ns;
    }

    public Color getKa() {
        return Ka;
    }

    public void setKa(Color ka) {
        Ka = ka;
    }

    public Color getKd() {
        return Kd;
    }

    public void setKd(Color kd) {
        Kd = kd;
    }

    public Color getKs() {
        return Ks;
    }

    public void setKs(Color ks) {
        Ks = ks;
    }

    public BufferedImage getMapNormals() {
        return mapNormals;
    }

    public void setMapNormals(String mapNormals) throws IOException {
        this.mapNormals = ImageIO.read(new File(mapNormals));
    }

    public BufferedImage getMapKd() {
        return mapKd;
    }

    public void setMapKd(String mapKd) throws IOException {
        this.mapKd = ImageIO.read(new File(mapKd));
    }

    public BufferedImage getMapKs() {
        return mapKs;
    }

    public void setMapKs(String mapKs) throws IOException {
        this.mapKs = ImageIO.read(new File(mapKs));
    }
}
