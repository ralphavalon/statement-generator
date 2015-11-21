package com.generator.statement.enums;

public enum JavaStatementEnum {
	
	PSTM("PSTM"),
	RESULTSET("RESULTSET");
	
	private String name;
	
	private JavaStatementEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static JavaStatementEnum getByName(String name) {
		for (JavaStatementEnum javaStatementEnum : JavaStatementEnum.values()) {
			if(javaStatementEnum.getName().equalsIgnoreCase(name)) {
				return javaStatementEnum;
			}
		}
		return null;
	}

}