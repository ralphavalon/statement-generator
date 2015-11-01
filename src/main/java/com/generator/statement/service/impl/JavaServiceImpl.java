package com.generator.statement.service.impl;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.TypeEnum;
import com.generator.statement.service.JavaService;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class JavaServiceImpl implements JavaService {
	
	private String insertPreparedStatement = "";
	private String resultSetStatement = "";
	private static final String PREPARED_STATEMENT_VARIABLE = PropertyReader.getProperty("prepared_statement");
	private static final String RESULT_SET_VARIABLE = PropertyReader.getProperty("result_set");
	private static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	private static Set<String> toIgnoreSet;
	
	public JavaServiceImpl() {
		toIgnoreSet = Util.removeUselessData(FIELDS_TO_IGNORE);
	}

	@Override
	public <T> String getPreparedStatement(Class<T> klazz) {
		int i = 0;
		for (Field field : klazz.getDeclaredFields()) {
			if(!isIgnored(field)) {
				TypeEnum typeEnum = TypeEnum.getTypeEnumByType(field.getType().getSimpleName());
				appendInsertPreparedStatement("%s.set%s(%d, ", ++i, typeEnum);
				
				switch (typeEnum) {
				case DATE:
					appendInsertPreparedStatement("new Date(%s.get%s().getTime()))", klazz, field);
					break;
				case BOOLEAN:
					appendInsertPreparedStatement("%s.is%s())", klazz, field);
					break;
				default:
					appendInsertPreparedStatement("%s.get%s())", klazz, field);
					break;
				}
			}
		}
		return insertPreparedStatement;
	}
	

	@Override
	public <T> String getResultSetStatement(Class<T> klazz, NamingStrategy namingStrategy) {
		for (Field field : klazz.getDeclaredFields()) {
			if(!isIgnored(field)) {
				TypeEnum typeEnum = TypeEnum.getTypeEnumByType(field.getType().getSimpleName());
				appendResultSetStatement("%s.set%s(%s", klazz, field);
				appendResultSetStatement(".get%s(\"%s\"))", typeEnum, getColumnName(namingStrategy, field));
			}
		}
		
		return resultSetStatement;
	}

	private String getColumnName(NamingStrategy namingStrategy, Field field) {
		Column columnAnnotation = field.getAnnotation(Column.class);
		return (columnAnnotation != null) ? columnAnnotation.name() : namingStrategy.columnName(field.getName());
	}

	private void appendInsertPreparedStatement(String string, int index, TypeEnum typeEnum) {
		insertPreparedStatement += String.format(string, 
				PREPARED_STATEMENT_VARIABLE,
				typeEnum.getValue(),
				index);
	}
	
	private <T> void appendResultSetStatement(String string, Class<T> klazz, Field field) {
		resultSetStatement += String.format(string, 
				StringUtils.uncapitalize(klazz.getSimpleName()),
				StringUtils.capitalize(field.getName()),
				RESULT_SET_VARIABLE);
	}
	
	private <T> void appendResultSetStatement(String string, TypeEnum typeEnum, String columnName) {
		resultSetStatement += String.format(string + ";\n", 
				StringUtils.capitalize(typeEnum.getValue()),
			    columnName); 
	}

	private <T> void appendInsertPreparedStatement(String string, Class<T> klazz, Field field) {
		insertPreparedStatement += String.format(string + ";\n", 
			     StringUtils.uncapitalize(klazz.getSimpleName()), 
			     StringUtils.capitalize(field.getName()));
	}
	
	private boolean isIgnored(Field field) {
		return toIgnoreSet.contains(field.getName());
	}
	
}