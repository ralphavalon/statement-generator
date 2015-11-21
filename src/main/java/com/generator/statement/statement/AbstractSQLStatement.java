package com.generator.statement.statement;

import java.util.List;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;

public abstract class AbstractSQLStatement extends AbstractStatement {
	
	private static final String COLUMN = "Column";
	private static final String TABLE = "Table";
	
	public AbstractSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}
	
	protected int fieldsToUse(List<ClassField> fields) {
		return fields.size() - toIgnoreSet.size();
	}
	
	protected String getTableName(NamingStrategy namingStrategy, InterpretedClass interpretedClass) {
		return (interpretedClass.hasClassAnnotation(TABLE)) ? interpretedClass.getClassAnnotationAttribute(TABLE, "name") : namingStrategy.tableName(interpretedClass.getName());
	}
	
	protected String getColumnName(NamingStrategy namingStrategy, ClassField classField) {
		return (classField.hasAnnotation(COLUMN)) ? classField.getAnnotationAttribute(COLUMN, "name") : namingStrategy.columnName(classField.getName());
	}

}