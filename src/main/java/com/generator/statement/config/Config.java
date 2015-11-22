package com.generator.statement.config;

import com.generator.statement.util.PropertyReader;

public class Config {
	
	public static final String FILE_TYPE = PropertyReader.getProperty("file_type");
	public static final String PREPARED_STATEMENT_VARIABLE = PropertyReader.getProperty("prepared_statement");
	public static final String RESULT_SET_VARIABLE = PropertyReader.getProperty("result_set");
	public static final String FIELDS_TO_IGNORE = PropertyReader.getProperty("ignore_fields");
	public static final String NAMING_STRATEGY = PropertyReader.getProperty("naming_strategy");
	public static final String STATEMENTS = PropertyReader.getProperty("statements");
	public static final String SQLS = PropertyReader.getProperty("sqls");
	public static final String OUTPUT = PropertyReader.getProperty("output");

}