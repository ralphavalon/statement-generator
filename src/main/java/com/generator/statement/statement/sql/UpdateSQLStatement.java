package com.generator.statement.statement.sql;

import java.util.List;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.ClassField;
import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractSQLStatement;
import com.generator.statement.util.Util;

public class UpdateSQLStatement extends AbstractSQLStatement {
	
	private String updateSQLStatement = "";
	
	public UpdateSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}
	
	public String getStatement() {
		updateSQLStatement = "";
		List<ClassField> fields = interpretedClass.getClassFieldList();
		updateSQLStatement += "UPDATE " + getTableName(namingStrategy, interpretedClass) + " SET ";
		for (ClassField field : fields) {
			if(!isIgnored(field)) {
				updateSQLStatement += getColumnName(namingStrategy, field) + " = ?,";
			}
		}
		updateSQLStatement = Util.removeLastComma(updateSQLStatement);
		updateSQLStatement += ";\n";
		return updateSQLStatement;
	}

}