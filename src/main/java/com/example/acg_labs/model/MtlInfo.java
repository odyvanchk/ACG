package com.example.acg_labs.model;

public class MtlInfo {
    private Color Ka;
    private Color Kd;
    private Color Ks;
    private int Ns;

    private String mapKa;
    private String mapKd;
    private String mapKs;

    public int getNs() {
        return Ns;
    }

    public void setNs(int ns) {
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

    public String getMapKa() {
        return mapKa;
    }

    public void setMapKa(String mapKa) {
        this.mapKa = mapKa;
    }

    public String getMapKd() {
        return mapKd;
    }

    public void setMapKd(String mapKd) {
        this.mapKd = mapKd;
    }

    public String getMapKs() {
        return mapKs;
    }

    public void setMapKs(String mapKs) {
        this.mapKs = mapKs;
    }
}
