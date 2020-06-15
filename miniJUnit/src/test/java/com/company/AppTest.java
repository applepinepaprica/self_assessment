package com.company;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static junit.framework.TestCase.assertEquals;

public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMiniJUnit() throws IllegalAccessException, InstantiationException, InvocationTargetException, MalformedURLException, ClassNotFoundException {
        MiniJUnitStarter.main(new String[] {"com.company"});
        assertEquals("\u001B[0;32mSuccessful: 1\u001B[0;31m Failed: 2\n" +
                "\u001B[0;32mSuccessful: 2\u001B[0;31m Failed: 1\n" +
                "\n" +
                "Failed tests:\n" +
                "UnitTests2.unitTest2\n" +
                "UnitTests2.unitTest3\n" +
                "UnitTests.unitTest2\n", outContent.toString());
    }

    @Test
    public void testMiniJUnit1() throws IllegalAccessException, InstantiationException, InvocationTargetException, MalformedURLException, ClassNotFoundException {
        MiniJUnitStarter.main(new String[] {"com.company", "UnitTests"});
        assertEquals("\u001B[0;32mSuccessful: 2\u001B[0;31m Failed: 1\n" +
                "\n" +
                "Failed tests:\n" +
                "UnitTests.unitTest2\n", outContent.toString());
    }

    @Test
    public void testMiniJUnit2() throws IllegalAccessException, InstantiationException, InvocationTargetException, MalformedURLException, ClassNotFoundException {
        MiniJUnitStarter.main(new String[] {"com.company", "UnitTests2"});
        assertEquals("\u001B[0;32mSuccessful: 1\u001B[0;31m Failed: 2\n" +
                "\n" +
                "Failed tests:\n" +
                "UnitTests2.unitTest2\n" +
                "UnitTests2.unitTest3\n", outContent.toString());
    }
}
