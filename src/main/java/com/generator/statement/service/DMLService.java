package com.generator.statement.service;

import org.hibernate.cfg.NamingStrategy;

public interface DMLService {
	
	<T> String getInsertSQLStatement(Class<T> klazz, NamingStrategy namingStrategy);
	
}