package com.company;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static junit.framework.TestCase.assertEquals;

public class AppTest 
{
    @Test
    public void testMiniJUnit() throws IllegalAccessException, InstantiationException, InvocationTargetException, MalformedURLException {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        MiniJUnitStarter.main(null);
        assertEquals("\u001B[0;32mSuccessful: 1\u001B[0;31m Failed: 2\n" +
                "\u001B[0;32mSuccessful: 2\u001B[0;31m Failed: 1\n" +
                "\n" +
                "Failed tests:\n" +
                "UnitTests2.unitTest2\n" +
                "UnitTests2.unitTest3\n" +
                "UnitTests.unitTest2\n", outContent.toString());
    }
}
