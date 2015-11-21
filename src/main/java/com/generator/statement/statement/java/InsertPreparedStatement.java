package com.generator.statement.statement.java;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.Config;
import com.generator.statement.enums.TypeEnum;
import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.statement.AbstractStatement;

public class InsertPreparedStatement extends AbstractStatement {
	
	public InsertPreparedStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {
		super(interpretedClass, namingStrategy);
	}

	private String insertPreparedStatement = "";
	
	@Override
	public String getStatement() {
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

	private void appendInsertPreparedStatement(String string, int index, TypeEnum typeEnum) {
		insertPreparedStatement += String.format(string, 
				Config.PREPARED_STATEMENT_VARIABLE,
				typeEnum.getValue(),
				index);
	}
	
	private void appendInsertPreparedStatement(String string, InterpretedClass interpretedClass, ClassField field) {
		insertPreparedStatement += String.format(string + ";\n", 
			     StringUtils.uncapitalize(interpretedClass.getName()), 
			     StringUtils.capitalize(field.getName()));
	}

}