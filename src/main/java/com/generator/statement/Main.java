package com.generator.statement;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.generator.statement.config.Config;
import com.generator.statement.enums.FileEnum;
import com.generator.statement.enums.StatementTypeEnum;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.factory.StatementListFactory;
import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.util.ClassUtils;
import com.generator.statement.util.FileUtils;
import com.generator.statement.util.JavaUtils;

public class Main {

	private static InterpretedClass interpretedClass;
	
	public static void main(String[] args) throws Exception {
		if(hasArgs(args)) {
			generateStatementsForSpecificFiles(args);
		} else {
			generateStatementsForAllFilesIfFileTypeIsRight();
		}
	}
	
	private static boolean hasArgs(String[] args) {
		return args.length > 0;
	}
	
	private static void generateStatementsForSpecificFiles(String[] args) throws IOException {
		for (File file : FileUtils.getFileSet(args)) {
			FileEnum fileEnum = FileEnum.getFileEnumByFilename(file.getName());
			generateStatementsForFile(fileEnum, file);
		}
	}
	
	private static void generateStatementsForFile(FileEnum fileEnum, File file) throws IOException {
		System.out.println("File: " + file.getName());
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ClassUtils.getClass(file, fileEnum));
		generateStatements(StatementTypeEnum.SQL);
		generateStatements(StatementTypeEnum.JAVA);
	}


	private static void generateStatements(StatementTypeEnum statementType) {
		List<AbstractStatement> statementList = StatementListFactory.factory(interpretedClass, statementType);
		for (AbstractStatement statement : statementList) {
			print(statement.getStatement());
		}
	}

	private static void generateStatementsForAllFilesIfFileTypeIsRight() throws IOException {
		FileEnum fileEnum = FileEnum.getFileEnumByType(Config.FILE_TYPE);
		if(fileEnum != null) {
			JavaUtils.compileIfNeeded(fileEnum);
			generateStatementsForAllFiles(fileEnum);
		}
	}

	private static void generateStatementsForAllFiles(FileEnum fileEnum) throws IOException {
		for (File file : FileUtils.getFilesForCurrentFolder(fileEnum)) {
			generateStatementsForFile(fileEnum, file);
		}
	}

	static void print(String string) {
		System.out.println(string);
	}
	
}