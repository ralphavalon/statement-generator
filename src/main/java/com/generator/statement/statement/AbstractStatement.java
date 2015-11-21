package com.generator.statement.statement;

import java.util.List;
import java.util.Set;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.Config;
import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.util.Util;

public abstract class AbstractStatement {
	
	protected static Set<String> toIgnoreSet = Util.removeUselessData(Config.FIELDS_TO_IGNORE);
	protected InterpretedClass interpretedClass;
	protected NamingStrategy namingStrategy;
	
	public AbstractStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) {  
		this.interpretedClass = interpretedClass;
		this.namingStrategy = namingStrategy;
	}
	
	public abstract String getStatement();
	
	protected boolean isIgnored(ClassField field) {
		return toIgnoreSet.contains(field.getName());
	}
	
	protected int fieldsToUse(List<ClassField> fields) {
		return fields.size() - toIgnoreSet.size();
	}

}