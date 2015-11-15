package com.generator.statement.service.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.TypeEnum;
import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.service.JavaService;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class JavaServiceImpl implements JavaService {
	
	private String insertPreparedStatement = "";
	private String resultSetStatement = "";
	private static final String COLUMN = "Column";
	private static final String PREPARED_STATEMENT_VARIABLE = PropertyReader.getProperty("prepared_statement");
	private static final String RESULT_SET_VARIABLE = PropertyReader.getProperty("result_set");
	private static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	private static Set<String> toIgnoreSet;
	
	public JavaServiceImpl() {
		toIgnoreSet = Util.removeUselessData(FIELDS_TO_IGNORE);
	}

	private String getColumnName(NamingStrategy namingStrategy, ClassField classField) {
		return (classField.hasAnnotation(COLUMN)) ? classField.getAnnotationAttribute(COLUMN, "name") : namingStrategy.columnName(classField.getName());
	}

	private void appendInsertPreparedStatement(String string, int index, TypeEnum typeEnum) {
		insertPreparedStatement += String.format(string, 
				PREPARED_STATEMENT_VARIABLE,
				typeEnum.getValue(),
				index);
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

	private void appendInsertPreparedStatement(String string, InterpretedClass interpretedClass, ClassField field) {
		insertPreparedStatement += String.format(string + ";\n", 
			     StringUtils.uncapitalize(interpretedClass.getName()), 
			     StringUtils.capitalize(field.getName()));
	}

	@Override
	public String getPreparedStatement(InterpretedClass interpretedClass) {
		int i = 0;
		insertPreparedStatement = "";
		for (ClassField field : interpretedClass.getClassFieldList()) {
			if(!isIgnored(field)) {
				TypeEnum typeEnum = TypeEnum.getTypeEnumByType(field.getType());
				appendInsertPreparedStatement("%s.set%s(%d, ", ++i, typeEnum);
				
				switch (typeEnum) {
				case DATE:
					appendInsertPreparedStatement("new Date(%s.get%s().getTime()))", interpretedClass, field);
					break;
				case BOOLEAN:
					appendInsertPreparedStatement("%s.is%s())", interpretedClass, field);
					break;
				default:
					appendInsertPreparedStatement("%s.get%s())", interpretedClass, field);
					break;
				}
			}
		}
		return insertPreparedStatement;
	}
	
	private boolean isIgnored(ClassField field) {
		return toIgnoreSet.contains(field.getName());
	}

	@Override
	public String getResultSetStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
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
	
}