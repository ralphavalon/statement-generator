package com.generator.statement.service;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.generator.statement.TypeEnum;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class MainServiceImpl implements MainService {
	
	private String insertSQLStatement = "";
	private String insertPreparedStatement = "";
	private String resultSetStatement = "";
	private static final String PREPARED_STATEMENT_VARIABLE = PropertyReader.getProperty("prepared_statement");
	private static final String RESULT_SET_VARIABLE = PropertyReader.getProperty("result_set");
	private static final String TABLE_NAME = PropertyReader.getProperty("table_name");
	private static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	private static Set<String> toIgnoreSet;
	
	public MainServiceImpl() {
		toIgnoreSet = Util.removeUselessData(FIELDS_TO_IGNORE);
	}

	public <T> String getInsertSQLStatement(Class<T> klazz) {
		Field[] fields = klazz.getDeclaredFields();
		insertSQLStatement += "INSERT INTO " + TABLE_NAME + "(";
		for (Field field : fields) {
			if(!isIgnored(field)) {
				insertSQLStatement += field.getName() + ",";
			}
		}
		insertSQLStatement = Util.removeLastComma(insertSQLStatement);
		insertSQLStatement += String.format(") VALUES (%s);\n", Util.removeLastComma(StringUtils.repeat("?,", fieldsToUse(fields))));
		return insertSQLStatement;
	}

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
	public <T> String getResultSetStatement(Class<T> klazz) {
		for (Field field : klazz.getDeclaredFields()) {
			if(!isIgnored(field)) {
				TypeEnum typeEnum = TypeEnum.getTypeEnumByType(field.getType().getSimpleName());
				appendResultSetStatement("%s.set%s(%s", klazz, field);
				appendResultSetStatement(".get%s(\"%s\"))", typeEnum, field);
			}
		}
		
		return resultSetStatement;
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
	
	private <T> void appendResultSetStatement(String string, TypeEnum typeEnum, Field field) {
		resultSetStatement += String.format(string + ";\n", 
				StringUtils.capitalize(typeEnum.getValue()),
			    field.getName()); 
	}

	private <T> void appendInsertPreparedStatement(String string, Class<T> klazz, Field field) {
		insertPreparedStatement += String.format(string + ";\n", 
			     StringUtils.uncapitalize(klazz.getSimpleName()), 
			     StringUtils.capitalize(field.getName()));
	}
	
	private boolean isIgnored(Field field) {
		return toIgnoreSet.contains(field.getName());
	}
	
	private int fieldsToUse(Field[] fields) {
		return fields.length - toIgnoreSet.size();
	}
	
}