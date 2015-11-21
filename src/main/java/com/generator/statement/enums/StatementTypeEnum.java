package com.generator.statement.enums;

public enum StatementTypeEnum {
	
	JAVA("Java"),
	SQL("SQL");
	
	private String name;
	
	private StatementTypeEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static StatementTypeEnum getByName(String name) {
		for (StatementTypeEnum statementTypeEnum : StatementTypeEnum.values()) {
			if(statementTypeEnum.getName().equalsIgnoreCase(name)) {
				return statementTypeEnum;
			}
		}
		return null;
	}

}