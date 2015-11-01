package com.generator.statement.config;

public enum StatementsEnum {
	
	PSTM("PSTM"),
	RESULTSET("RESULTSET");
	
	private String name;
	
	private StatementsEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static StatementsEnum getByName(String name) {
		for (StatementsEnum statement : StatementsEnum.values()) {
			if(statement.getName().equalsIgnoreCase(name)) {
				return statement;
			}
		}
		return null;
	}

}