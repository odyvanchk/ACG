package com.example.acg_labs.entity;

import java.util.List;

public interface InfoComponent<T extends Number> {
    void add(T element);

    List<T> getChildren();

    int[] getColor();

    void updateColor(int[] newColor);

    //ComponentType getType();
}
