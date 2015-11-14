package com.generator.statement.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.ClassField;
import com.generator.statement.service.DMLService;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class DMLServiceImpl implements DMLService {
	
	private String insertSQLStatement = "";
	private static final String COLUMN = "Column";
	private static final String TABLE = "Table";
	private static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	private static Set<String> toIgnoreSet;
	
	public DMLServiceImpl() {
		toIgnoreSet = Util.removeUselessData(FIELDS_TO_IGNORE);
	}
	
	public String getInsertSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
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

	private String getTableName(NamingStrategy namingStrategy, InterpretedClass interpretedClass) {
		return (interpretedClass.hasClassAnnotation(TABLE)) ? interpretedClass.getClassAnnotationAttribute(TABLE, "name") : namingStrategy.tableName(interpretedClass.getName());
	}
	
	private String getColumnName(NamingStrategy namingStrategy, ClassField classField) {
		return (classField.hasAnnotation(COLUMN)) ? classField.getAnnotationAttribute(COLUMN, "name") : namingStrategy.columnName(classField.getName());
	}
	
	private boolean isIgnored(ClassField field) {
		return toIgnoreSet.contains(field.getName());
	}

	private int fieldsToUse(List<ClassField> fields) {
		return fields.size() - toIgnoreSet.size();
	}
	
}