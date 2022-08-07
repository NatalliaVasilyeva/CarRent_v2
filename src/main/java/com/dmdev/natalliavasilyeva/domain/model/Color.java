package com.dmdev.natalliavasilyeva.domain.model;

public enum Color {
    RED("red"),
    BLUE("blue"),
    WHITE("white"),
    BLACK("black"),
    GREEN("green"),
    YELLOW("yellow");

    Color(String color) {
        this.color = color;
    }

    String color;

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.getColor();
    }

    public static Color getEnum(String value) {
        for(Color v : values())
            if(v.getColor().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}