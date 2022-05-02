package model;

import java.awt.*;

public class Equation {
    private String text;
    private Color color;

    public Equation(String text, Color color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }
}
