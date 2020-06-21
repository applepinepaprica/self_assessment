package com.company.shapes;

import com.company.Shape;
import org.apache.commons.lang3.StringUtils;

public class Rectangle extends Shape {

    private int length;
    private int width;

    public Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(new Line(width, LineType.HORIZONTAL)).append("\n");

        for (int i = 0; i < length; i++) {
            result.append("|").append(StringUtils.repeat("  ", width - 1)).append("|\n");
        }

        result.append(new Line(width, LineType.HORIZONTAL)).append("\n");
        return result.toString();
    }
}
