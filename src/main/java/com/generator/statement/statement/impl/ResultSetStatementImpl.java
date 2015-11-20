package com.generator.statement.statement.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.TypeEnum;
import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.util.PropertyReader;

public class ResultSetStatementImpl extends AbstractStatement {
	
	private String resultSetStatement = "";
	private static final String COLUMN = "Column";
	private static final String RESULT_SET_VARIABLE = PropertyReader.getProperty("result_set");
	
	public ResultSetStatementImpl(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}
	
	@Override
	public String getStatement() {
		resultSetStatement = "";
		for (ClassField field : interpretedClass.getClassFieldList()) {
			if(!isIgnored(field)) {
				TypeEnum typeEnum = TypeEnum.getTypeEnumByType(field.getType());
				appendResultSetStatement("%s.set%s(%s", interpretedClass, field);
				appendResultSetStatement(".get%s(\"%s\"))", typeEnum, getColumnName(namingStrategy, field));
			}
		}
		
		return resultSetStatement;
	}

	private void appendResultSetStatement(String string, InterpretedClass interpretedClass, ClassField field) {
		resultSetStatement += String.format(string, 
				StringUtils.uncapitalize(interpretedClass.getName()),
				StringUtils.capitalize(field.getName()),
				RESULT_SET_VARIABLE);
	}
	
	private <T> void appendResultSetStatement(String string, TypeEnum typeEnum, String columnName) {
		resultSetStatement += String.format(string + ";\n", 
				StringUtils.capitalize(typeEnum.getValue()),
			    columnName); 
	}
	
	private String getColumnName(NamingStrategy namingStrategy, ClassField classField) {
		return (classField.hasAnnotation(COLUMN)) ? classField.getAnnotationAttribute(COLUMN, "name") : namingStrategy.columnName(classField.getName());
	}

}