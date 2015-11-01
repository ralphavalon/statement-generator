package com.generator.statement.config;

public enum SqlsEnum {
	
	INSERT("INSERT");
	
	private String name;
	
	private SqlsEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static SqlsEnum getByName(String name) {
		for (SqlsEnum sqlsEnum : SqlsEnum.values()) {
			if(sqlsEnum.getName().equalsIgnoreCase(name)) {
				return sqlsEnum;
			}
		}
		return null;
	}

}