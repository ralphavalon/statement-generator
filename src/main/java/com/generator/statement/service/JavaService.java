package com.generator.statement.service;

import org.hibernate.cfg.NamingStrategy;

public interface JavaService {
	
	String getPreparedStatement(InterpretedClass interpretedClass);
	
	String getResultSetStatement(InterpretedClass interpretedClass, NamingStrategy namingStrategy);

}