package com.example.acg_labs.entity.impl;

import com.example.acg_labs.entity.InfoComponent;

import java.util.List;

public record VertexComponent(Double element) implements InfoComponent<Double> {

    @Override
    public void add(Double element) {
        throw new UnsupportedOperationException("not add");
    }

    @Override
    public List<Double> getChildren() {
        throw new UnsupportedOperationException("not children");
    }

    @Override
    public String toString() {
        return element.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexComponent that = (VertexComponent) o;
        return element == that.element;
    }

    @Override
    public int hashCode() {
        return 31 + Double.hashCode(element);
    }
}
