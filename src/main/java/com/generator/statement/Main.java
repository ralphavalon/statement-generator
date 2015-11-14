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
		//TODO: Read and use "type" attribute on config.properties
		if(args != null && args.length > 0) {
			for (File file : getFileSet(args)) {
				FileEnum fileEnum = FileEnum.getFileEnumByFilename(file.getName());
				interpretedClass = InterpretedClassFactory.getInterpretedClass(getClass(file, fileEnum));
				generateSqls();
				generateStatements();
			}
		} else {
			FileEnum fileEnum = FileEnum.getFileEnumByType(PropertyReader.getProperty("file_type"));
			if(fileEnum != null) {
				switch (fileEnum) {
				case JAVA:
					Runtime.getRuntime().exec("javac -cp \"./*\" *.java");
					fileSet = getJavaFilesForCurrentFolder();
					for (File file : fileSet) {
						interpretedClass = InterpretedClassFactory.getInterpretedClass(getClass(file, fileEnum));
						generateSqls();
						generateStatements();
					}
					break;
				case CLASS:
					break;
				default:
					break;
				}
			}
		}
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

	private static Set<File> getJavaFilesForCurrentFolder() {
		Set<File> fileSet = new HashSet<File>();
		for (File file : new File(".").listFiles()) {
			if (file.getName().endsWith(FileEnum.JAVA.getSuffix())) {
				fileSet.add(file);
			}
		}
		return fileSet;
	}

	@SuppressWarnings("resource")
	private static void loadClass(File file, String classPackage) throws MalformedURLException {
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[]{ file.toURI().toURL() });
			System.out.println(classPackage + file.getName().replace(FileEnum.JAVA.getSuffix(), ""));
			klazz = classLoader.loadClass(classPackage + file.getName().replace(FileEnum.JAVA.getSuffix(), ""));
			System.out.println(classPackage + klazz.getSimpleName());
			System.out.println("Sucesso");
		} catch (ClassNotFoundException e) {
			System.out.println("Probably the class didn't compile.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getClassPackage(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		String classPackage = "";
		boolean notFound = true;
		while (sc.hasNext() && notFound) {
			String line = sc.nextLine();

			if (line.matches(PACKAGE_REGEX)) {
				classPackage = line.split("\\s+")[1].replace(";", ".");
//				System.out.println(classPackage);
				notFound = false;
			}
			if (line.contains("public class")) {
				notFound = false;
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