package com.generator.statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.NamingStrategyEnum;
import com.generator.statement.config.SqlsEnum;
import com.generator.statement.config.StatementsEnum;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.service.DMLService;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.service.JavaService;
import com.generator.statement.service.impl.DMLServiceImpl;
import com.generator.statement.service.impl.JavaServiceImpl;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class Main {

	private static final String PACKAGE_REGEX = "package\\s+([a-zA_Z_][\\.\\w]*);";
	private static JavaService javaService;
	private static DMLService dmlService;
	private static NamingStrategy namingStrategy;
	private static Class<?> klazz;
	private static InterpretedClass interpretedClass;
	private static Set<File> fileSet;

	static {
		namingStrategy = NamingStrategyEnum.getNamingStrategyByString(PropertyReader.getProperty("naming_strategy"));
		dmlService = new DMLServiceImpl();
		javaService = new JavaServiceImpl();
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length > 0) {
			for (File file : getFileSet(args)) {
				FileEnum fileEnum = FileEnum.getFileEnumByFilename(file.getName());
				execute(fileEnum, file);
			}
		} else {
			FileEnum fileEnum = FileEnum.getFileEnumByType(PropertyReader.getProperty("file_type"));
			if(fileEnum != null) {
				compileIfNeeded(fileEnum);
				fileSet = getFilesForCurrentFolder(fileEnum);
				for (File file : fileSet) {
					execute(fileEnum, file);
				}
			}
		}
	}

	private static void compileIfNeeded(FileEnum fileEnum) throws IOException {
		if(fileEnum.equals(FileEnum.JAVA)) {
			Runtime.getRuntime().exec("javac -cp \"./*\" *.java");
		}
	}

	private static void execute(FileEnum fileEnum, File file) throws IOException {
		System.out.println("File: " + file.getName());
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getClass(file, fileEnum));
		generateSqls();
		generateStatements();
	}

	private static Set<File> getFileSet(String[] args) {
		Set<File> fileSet = new HashSet<File>();
		for (String filename : args) {
			if(FileEnum.getFileEnumByFilename(filename) != null) {
				fileSet.add(new File(filename));
			}
		}
		return fileSet;
	}
	
	private static Object getClass(File file, FileEnum fileEnum) throws ClassFormatException, IOException {
		switch (fileEnum) {
		case JAVA:
			loadClass(file, getClassPackage(file));
			return klazz;
		case CLASS:
			ClassParser parser = new ClassParser(file.getName());
			JavaClass javaClass = parser.parse();
			return javaClass;
		default:
			return null;
		}
	}

	private static Set<File> getFilesForCurrentFolder(FileEnum fileEnum) {
		Set<File> fileSet = new HashSet<File>();
		for (File file : new File(".").listFiles()) {
			if (file.getName().endsWith(fileEnum.getSuffix())) {
				fileSet.add(file);
			}
		}
		return fileSet;
	}

	@SuppressWarnings("resource")
	private static void loadClass(File file, String classPackage) throws MalformedURLException {
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[]{ file.toURI().toURL() });
			System.out.println("Class: " + classPackage + file.getName().replace(FileEnum.JAVA.getSuffix(), ""));
			klazz = classLoader.loadClass(classPackage + file.getName().replace(FileEnum.JAVA.getSuffix(), ""));
		} catch (ClassNotFoundException e) {
			System.out.println("Probably the class didn't compile. Try to use \"javac -cp StatementGenerator-1.0.jar yourClass.java\"");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	private static void generateSqls() {
		for (SqlsEnum sqlsEnum : Util.getSqls()) {
			switch (sqlsEnum) {
			case INSERT:
				print(dmlService.getInsertSQLStatement(interpretedClass, namingStrategy));
				break;
			default:
				break;
			}
		}
	}

	private static void generateStatements() {
		for (StatementsEnum statementsEnum : Util.getStatements()) {
			switch (statementsEnum) {
			case PSTM:
				print(javaService.getPreparedStatement(interpretedClass));
				break;
			case RESULTSET:
				print(javaService.getResultSetStatement(interpretedClass, namingStrategy));
				break;
			default:
				break;
			}
		}
	}

	static void print(String string) {
		System.out.println(string);
	}
	
}