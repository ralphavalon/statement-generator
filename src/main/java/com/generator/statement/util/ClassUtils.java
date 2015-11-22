package com.generator.statement.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import com.generator.statement.enums.FileEnum;

public class ClassUtils {
	
	private static final String PACKAGE_REGEX = "package\\s+([a-zA_Z_][\\.\\w]*);";
	
	public static Object getClass(File file, FileEnum fileEnum) throws ClassFormatException, IOException {
		switch (fileEnum) {
		case JAVA:
			return loadClass(file, getClassPackage(file));
		case CLASS:
			ClassParser parser = new ClassParser(file.getName());
			JavaClass javaClass = parser.parse();
			return javaClass;
		default:
			return null;
		}
	}
	
	private static String getClassPackage(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		String classPackage = "";
		boolean packageNotFound = true;
		while (sc.hasNext() && packageNotFound) {
			String line = sc.nextLine();

			if (line.matches(PACKAGE_REGEX)) {
				classPackage = line.split("\\s+")[1].replace(";", ".");
				packageNotFound = false;
			}
			if (line.contains("public class")) {
				packageNotFound = false;
			}
		}
		sc.close();
		return classPackage;
	}
	
	@SuppressWarnings("resource")
	private static Class<?> loadClass(File file, String classPackage) throws MalformedURLException {
		Class<?> klazz = null;
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[]{ file.toURI().toURL() });
			String fullClassPath = classPackage + file.getName().replace(FileEnum.JAVA.getSuffix(), "");
			System.out.println("Compiling Class: " + fullClassPath);
			klazz = classLoader.loadClass(fullClassPath);
		} catch (ClassNotFoundException e) {
			System.out.println("Probably the class didn't compile. Try to use \"javac -cp statement-generator-{version}.jar yourClass.java\"");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return klazz;
	} 

}
