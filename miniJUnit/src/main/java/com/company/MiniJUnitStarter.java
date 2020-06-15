package com.company;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.*;

public class MiniJUnitStarter {

    private static List<String> failedMethodsNames;

    public static void main( String[] args ) throws InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        if (args.length == 0)
            throw new RuntimeException("Not enough arguments");

        failedMethodsNames = new ArrayList<>();

        if (args.length == 1) {

            // set -ea flag to enable assertions
            ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

            // add settings to scan test classes
            URL testClassesURL = Paths.get("target/test-classes").toUri().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{testClassesURL},
                    ClasspathHelper.staticClassLoader());

            List<ClassLoader> classLoadersList = new LinkedList<>();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());
            classLoadersList.add(classLoader);

            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(args[0]))));
            Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

            for (Class clazz : allClasses) {
                testClasses(clazz);
            }

        } else if (args.length == 2) {
            testClasses(Class.forName(args[0] + "." + args[1]));
        }

        if (!failedMethodsNames.isEmpty()) {
            System.out.println("\nFailed tests:");
            for (String s : failedMethodsNames)
                System.out.println(s);
        }
    }

    private static void testClasses(Class clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        int failedTests = 0;
        int successfulTest = 0;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(Test.class) != null) {
                try {
                    method.invoke(clazz.newInstance());
                    successfulTest++;
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        failedTests++;
                        failedMethodsNames.add(clazz.getSimpleName() + "." + method.getName());
                    }
                    else
                        throw e;
                }
            }
        }

        if (successfulTest != 0 || failedTests != 0)
            System.out.println(ConsoleColors.GREEN + "Successful: " + successfulTest + ConsoleColors.RED + " Failed: " + failedTests);
    }
}
