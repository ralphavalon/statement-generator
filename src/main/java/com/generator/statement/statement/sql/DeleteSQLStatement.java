package com.generator.statement.statement.sql;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractSQLStatement;

public class DeleteSQLStatement extends AbstractSQLStatement {
	
	private String deleteSQLStatement = "";
	
	public DeleteSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}
	
	public String getStatement() {
		deleteSQLStatement = "DELETE FROM " + getTableName(namingStrategy, interpretedClass) + ";\n";
		return deleteSQLStatement;
	}

}