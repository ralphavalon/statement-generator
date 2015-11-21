package com.generator.statement.statement.sql;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.statement.AbstractSQLStatement;
import com.generator.statement.util.Util;

public class InsertSQLStatement extends AbstractSQLStatement {
	
	private String insertSQLStatement = "";
	
	public InsertSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}
	
	public String getStatement() {
		insertSQLStatement = "";
		List<ClassField> fields = interpretedClass.getClassFieldList();
		insertSQLStatement += "INSERT INTO " + getTableName(namingStrategy, interpretedClass) + "(";
		for (ClassField field : fields) {
			if(!isIgnored(field)) {
				insertSQLStatement += getColumnName(namingStrategy, field) + ",";
			}
		}
		insertSQLStatement = Util.removeLastComma(insertSQLStatement);
		insertSQLStatement += String.format(") VALUES (%s);\n", Util.removeLastComma(StringUtils.repeat("?,", fieldsToUse(fields))));
		return insertSQLStatement;
	}

}