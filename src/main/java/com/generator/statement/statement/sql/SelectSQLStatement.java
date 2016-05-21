package com.generator.statement.statement.sql;

import java.util.List;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.ClassField;
import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractSQLStatement;
import com.generator.statement.util.Util;

public class SelectSQLStatement extends AbstractSQLStatement {
	
	private String selectSQLStatement = "";
	
	public SelectSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}
	
	public String getStatement() {
		selectSQLStatement = "";
		List<ClassField> fields = interpretedClass.getClassFieldList();
		selectSQLStatement += "SELECT ";
		for (ClassField field : fields) {
			if(!isIgnored(field)) {
				selectSQLStatement += getColumnName(namingStrategy, field) + ",";
			}
		}
		selectSQLStatement = Util.removeLastComma(selectSQLStatement);
		selectSQLStatement += " FROM " + getTableName(namingStrategy, interpretedClass) + ";\n";
		return selectSQLStatement;
	}

}