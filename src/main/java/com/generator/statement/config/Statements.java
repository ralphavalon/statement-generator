package com.generator.statement.config;

public enum Statements {
	
	PSTM("PSTM"),
	RESULTSET("RESULTSET");
	
	private String name;
	
	private Statements(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static Statements getByName(String name) {
		for (Statements statement : Statements.values()) {
			if(statement.getName().equalsIgnoreCase(name)) {
				return statement;
			}
		}
		return null;
	}

}