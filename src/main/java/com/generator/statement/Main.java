package com.generator.statement;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.ClassConfig;
import com.generator.statement.config.FileEnum;
import com.generator.statement.config.NamingStrategyEnum;
import com.generator.statement.config.SqlsEnum;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.factory.StatementListFactory;
import com.generator.statement.service.DMLService;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.service.impl.DMLServiceImpl;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class Main {

	private static DMLService dmlService;
	private static NamingStrategy namingStrategy;
	private static InterpretedClass interpretedClass;
	private static Set<File> fileSet;

	static {
		namingStrategy = NamingStrategyEnum.getNamingStrategyByString(PropertyReader.getProperty("naming_strategy"));
		dmlService = new DMLServiceImpl();
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
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ClassConfig.getClass(file, fileEnum));
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
	
	private static Set<File> getFilesForCurrentFolder(FileEnum fileEnum) {
		Set<File> fileSet = new HashSet<File>();
		for (File file : new File(".").listFiles()) {
			if (file.getName().endsWith(fileEnum.getSuffix())) {
				fileSet.add(file);
			}
		}
		return fileSet;
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
		List<AbstractStatement> statementList = StatementListFactory.factory(interpretedClass);
		for (AbstractStatement statement : statementList) {
			print(statement.getStatement());
		}
	}

	static void print(String string) {
		System.out.println(string);
	}
	
}