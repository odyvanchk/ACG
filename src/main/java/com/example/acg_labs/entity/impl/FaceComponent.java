package com.example.acg_labs.entity.impl;

import com.example.acg_labs.entity.InfoComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FaceComponent implements InfoComponent<Integer> {

    private final List<Integer> infoComponents = new ArrayList<>();

    @Override
    public void add(Integer element) {
        infoComponents.add(element);
    }

    @Override
    public List<Integer> getChildren() {
        return infoComponents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaceComponent that = (FaceComponent) o;
        return Objects.equals(infoComponents, that.infoComponents);
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (var e : infoComponents)
            result = 31 * result- + (e==null ? 0 : e.hashCode());
        return result;
    }
}
