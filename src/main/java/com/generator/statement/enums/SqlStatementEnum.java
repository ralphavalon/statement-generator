package com.generator.statement.enums;

public enum SqlStatementEnum {
	
	INSERT("INSERT"),
	UPDATE("UPDATE");
	
	private String name;
	
	private SqlStatementEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static SqlStatementEnum getByName(String name) {
		for (SqlStatementEnum sqlStatementEnum : SqlStatementEnum.values()) {
			if(sqlStatementEnum.getName().equalsIgnoreCase(name)) {
				return sqlStatementEnum;
			}
		}
		return null;
	}

}