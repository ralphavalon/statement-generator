package com.generator.statement.service;

public interface MainService {
	
	<T> String getInsertSQLStatement(Class<T> klazz, String tableName);
	
	<T> String getPreparedStatement(Class<T> klazz);

}