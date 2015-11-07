package com.generator.statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.NamingStrategyEnum;
import com.generator.statement.config.SqlsEnum;
import com.generator.statement.config.StatementsEnum;
import com.generator.statement.service.DMLService;
import com.generator.statement.service.JavaService;
import com.generator.statement.service.impl.DMLServiceImpl;
import com.generator.statement.service.impl.JavaServiceImpl;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class Main {

	private static final String PACKAGE_REGEX = "package\\s+([a-zA_Z_][\\.\\w]*);";
	private static final String JAVA_SUFFIX = ".java";
	private static JavaService javaService;
	private static DMLService dmlService;
	private static NamingStrategy namingStrategy;
	private static Class<?> klazz;

	static {
		namingStrategy = NamingStrategyEnum.getNamingStrategyByString(PropertyReader.getProperty("naming_strategy"));
		dmlService = new DMLServiceImpl();
		javaService = new JavaServiceImpl();
	}

	public static void main(String[] args) throws Exception {
		Runtime.getRuntime().exec("javac -cp \"./*\" *.java");
		List<File> javaFiles = getJavaFilesForCurrentFolder();
		for (File file : javaFiles) {
			String classPackage = getClassPackage(file);
			loadClass(file, classPackage);
			generateSqls();
			generateStatements();
		}
	}

	private static List<File> getJavaFilesForCurrentFolder() {
		List<File> fileList = new ArrayList<File>();
		for (File file : new File(".").listFiles()) {
			if (file.getName().endsWith(JAVA_SUFFIX)) {
				fileList.add(file);
			}
		}
		return fileList;
	}

	@SuppressWarnings("resource")
	private static void loadClass(File file, String classPackage) throws MalformedURLException {
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[]{ file.toURI().toURL() });
			klazz = classLoader.loadClass(classPackage + file.getName().replace(JAVA_SUFFIX, ""));
			System.out.println(classPackage + klazz.getSimpleName());
		} catch (ClassNotFoundException e) {
			System.out.println("Probably the class didn't compile.");
			System.out.println(e.getMessage());
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
				print(dmlService.getInsertSQLStatement(klazz, namingStrategy));
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
				print(javaService.getPreparedStatement(klazz));
				break;
			case RESULTSET:
				print(javaService.getResultSetStatement(klazz, namingStrategy));
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