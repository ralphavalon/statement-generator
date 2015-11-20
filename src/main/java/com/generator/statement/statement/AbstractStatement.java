package com.generator.statement.statement;

import java.util.Set;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public abstract class AbstractStatement {
	
	private static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	private static Set<String> toIgnoreSet;
	protected InterpretedClass interpretedClass;
	protected NamingStrategy namingStrategy;
	
	public AbstractStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy) { //TODO: (InterpretedClass class, NamingStrategy strategy) 
		toIgnoreSet = Util.removeUselessData(FIELDS_TO_IGNORE);
		this.interpretedClass = interpretedClass;
		this.namingStrategy = namingStrategy;
	}
	
	public abstract String getStatement();
	
	protected boolean isIgnored(ClassField field) {
		return toIgnoreSet.contains(field.getName());
	}

}