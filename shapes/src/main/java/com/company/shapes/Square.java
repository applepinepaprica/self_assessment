package com.company.shapes;

import com.company.Shape;
import org.apache.commons.lang3.StringUtils;

public class Square extends Shape {

    private int length;

    public Square(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(new Line(length, LineType.HORIZONTAL)).append("\n");

        for (int i = 0; i < length; i++) {
            result.append("|").append(StringUtils.repeat("  ", length - 1)).append("|\n");
        }

        result.append(new Line(length, LineType.HORIZONTAL)).append("\n");
        return result.toString();
    }
}
