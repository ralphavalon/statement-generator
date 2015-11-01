package com.generator.statement.service;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class DMLServiceImpl implements DMLService {
	
	private String insertSQLStatement = "";
	private static final String TABLE_NAME = PropertyReader.getProperty("table_name");
	private static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	private static Set<String> toIgnoreSet;
	
	public DMLServiceImpl() {
		toIgnoreSet = Util.removeUselessData(FIELDS_TO_IGNORE);
	}

	@Override
	public <T> String getInsertSQLStatement(Class<T> klazz, NamingStrategy namingStrategy) {
		Field[] fields = klazz.getDeclaredFields();
		insertSQLStatement += "INSERT INTO " + TABLE_NAME + "(";
		for (Field field : fields) {
			if(!isIgnored(field)) {
				insertSQLStatement += getColumnName(namingStrategy, field) + ",";
			}
		}
		insertSQLStatement = Util.removeLastComma(insertSQLStatement);
		insertSQLStatement += String.format(") VALUES (%s);\n", Util.removeLastComma(StringUtils.repeat("?,", fieldsToUse(fields))));
		return insertSQLStatement;
	}

	private String getColumnName(NamingStrategy namingStrategy, Field field) {
		Column columnAnnotation = field.getAnnotation(Column.class);
		return (columnAnnotation != null) ? columnAnnotation.name() : namingStrategy.columnName(field.getName());
	}

	private boolean isIgnored(Field field) {
		return toIgnoreSet.contains(field.getName());
	}
	
	private int fieldsToUse(Field[] fields) {
		return fields.length - toIgnoreSet.size();
	}
	
}