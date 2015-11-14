package com.generator.statement.service;

import org.hibernate.cfg.NamingStrategy;

public interface DMLService {
	
	String getInsertSQLStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy);
	
}