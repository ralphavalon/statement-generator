package com.generator.statement.service;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.generator.statement.TypeEnum;
import com.generator.statement.util.Util;

public class MainServiceImpl implements MainService {
	
	private String insertSQLStatement = "";
	private String insertPreparedStatement = "";
	private static final String PREPARED_STATEMENT_VARIABLE = "pstm";

	public <T> String getInsertSQLStatement(Class<T> klazz, String tableName) {
		Field[] fields = klazz.getDeclaredFields();
		insertSQLStatement += "INSERT INTO " + tableName + "(";
		for (Field field : fields) {
			insertSQLStatement += field.getName() + ",";
		}
		insertSQLStatement = Util.removeLastComma(insertSQLStatement);
		insertSQLStatement += String.format(") VALUES (%s);", Util.removeLastComma(StringUtils.repeat("?,", fields.length)));
		return insertSQLStatement;
	}
	
	public <T> String getPreparedStatement(Class<T> klazz) {
		int i = 0;
		for (Field field : klazz.getDeclaredFields()) {
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
		return insertPreparedStatement;
	}

	private void appendInsertPreparedStatement(String string, int index, TypeEnum typeEnum) {
		insertPreparedStatement += String.format(string, 
				PREPARED_STATEMENT_VARIABLE,
				typeEnum.getValue(),
				index);
	}

	private <T> void appendInsertPreparedStatement(String string, Class<T> klazz, Field field) {
		insertPreparedStatement += String.format(string + ";\n", 
			     StringUtils.uncapitalize(klazz.getSimpleName()), 
			     StringUtils.capitalize(field.getName()));
	}
	
}