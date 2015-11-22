package com.generator.statement.enums;


public enum OutputEnum {
	
	CONSOLE("console"),
	FILE("file");
	
	private String value;
	
	private OutputEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static OutputEnum getOutputEnumByValue(String value) {
		for (OutputEnum outputEnum : OutputEnum.values()) {
			if(outputEnum.getValue().equalsIgnoreCase(value)) {
				return outputEnum;
			}
		}
		return null;
	}

}