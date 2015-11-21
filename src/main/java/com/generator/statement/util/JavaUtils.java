package com.generator.statement.util;

import java.io.IOException;

import com.generator.statement.enums.FileEnum;

public class JavaUtils {
	
	public static void compileIfNeeded(FileEnum fileEnum) throws IOException {
		if(fileEnum.equals(FileEnum.JAVA)) {
			Runtime.getRuntime().exec("javac -cp \"./*\" *.java");
		}
	}

}