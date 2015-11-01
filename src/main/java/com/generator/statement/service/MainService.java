package com.generator.statement.service;

import org.hibernate.cfg.NamingStrategy;

public interface MainService {
	
	<T> String getPreparedStatement(Class<T> klazz);

	<T> String getResultSetStatement(Class<T> klazz, NamingStrategy namingStrategy);

}