package com.company;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class App {
    public static void main( String[] args ) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.company"))));
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        List<String> failedMethodsNames = new ArrayList<>();

        for (Class clazz : allClasses) {
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

        if (!failedMethodsNames.isEmpty()) {
            System.out.println("\nFailed tests:");
            for (String s: failedMethodsNames)
                System.out.println(s);
        }
    }
}
