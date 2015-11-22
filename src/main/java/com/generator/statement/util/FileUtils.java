package com.generator.statement.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.generator.statement.enums.FileEnum;

public class FileUtils {
	
	public static void writeToFile(String text, String directory, String filename) {
		try {
			createDirectoryIfNeeded(directory);
			FileWriter fileWriter = new FileWriter(getFullFilePath(directory, filename));
			fileWriter.write(text);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createDirectoryIfNeeded(String directoryPath) {
		File directory = new File(directoryPath);
		if(!directory.exists()) {
			directory.mkdirs();
		}
	}

	private static String getFullFilePath(String directory, String filename) {
		return directory + "/" + filename;
	}
	
	public static Set<File> getFileSet(String[] args) {
		Set<File> fileSet = new HashSet<File>();
		for (String filename : args) {
			if(FileEnum.getFileEnumByFilename(filename) != null) {
				fileSet.add(new File(filename));
			}
		}
		return fileSet;
	}
	
	public static Set<File> getFilesForCurrentFolder(FileEnum fileEnum) {
		Set<File> fileSet = new HashSet<File>();
		for (File file : new File(".").listFiles()) {
			if (file.getName().endsWith(fileEnum.getSuffix())) {
				fileSet.add(file);
			}
		}
		return fileSet;
	}

}