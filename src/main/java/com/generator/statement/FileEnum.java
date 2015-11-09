package com.generator.statement;


public enum FileEnum {
	
	JAVA(".java"),
	CLASS(".class");
	
	private String suffix;
	
	private FileEnum(String suffix) {
		this.suffix = suffix;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public static FileEnum getFileEnumByFilename(String filename) {
		for (FileEnum fileEnum : FileEnum.values()) {
			if(filename.endsWith(fileEnum.getSuffix())) {
				return fileEnum;
			}
		}
		return null;
	}

}