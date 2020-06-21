package com.company.shapes;

import com.company.Shape;
import org.apache.commons.lang3.StringUtils;

public class Line extends Shape {

    private int length;
    private LineType type;

    public Line(int length, LineType type) {
        this.length = length;
        this.type = type;
    }

    @Override
    public String toString() {
        switch (type) {
            case HORIZONTAL: return StringUtils.repeat("——", length);
            case VERTICAL: return StringUtils.repeat("|\n", length);
            default: throw new RuntimeException("This will never happen");
        }
    }
}
