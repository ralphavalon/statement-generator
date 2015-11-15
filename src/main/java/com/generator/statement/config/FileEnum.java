package com.generator.statement.config;


public enum FileEnum {
	
	JAVA(".java", "java"),
	CLASS(".class", "class");
	
	private String suffix;
	private String type;
	
	private FileEnum(String suffix, String type) {
		this.suffix = suffix;
		this.type = type;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public String getType() {
		return type;
	}
	
	public static FileEnum getFileEnumByFilename(String filename) {
		for (FileEnum fileEnum : FileEnum.values()) {
			if(filename.endsWith(fileEnum.getSuffix())) {
				return fileEnum;
			}
		}
		return null;
	}
	
	public static FileEnum getFileEnumByType(String type) {
		for (FileEnum fileEnum : FileEnum.values()) {
			if(fileEnum.getType().equalsIgnoreCase(type)) {
				return fileEnum;
			}
		}
		return null;
	}

}