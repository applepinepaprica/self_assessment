package com.company;

import com.company.shapes.*;

public class App {

    public static void main( String[] args ) {
        Dot dot = new Dot();
        System.out.println(dot);

        Line line1 = new Line(7, LineType.HORIZONTAL);
        System.out.println(line1);

        Line line2 = new Line(7, LineType.VERTICAL);
        System.out.println(line2);

        Square square = new Square(6);
        System.out.println(square);

        Rectangle rectangle = new Rectangle(3, 6);
        System.out.println(rectangle);
    }
}
