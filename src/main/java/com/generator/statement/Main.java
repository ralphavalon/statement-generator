package com.generator.statement;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.NamingStrategyEnum;
import com.generator.statement.config.SqlsEnum;
import com.generator.statement.config.StatementsEnum;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.service.DMLService;
import com.generator.statement.service.JavaService;
import com.generator.statement.service.impl.DMLServiceImpl;
import com.generator.statement.service.impl.JavaServiceImpl;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class Main {
	
	private static JavaService javaService;
	private static DMLService dmlService;
	private static NamingStrategy namingStrategy;
	
	static {
		javaService = new JavaServiceImpl();
		dmlService = new DMLServiceImpl();
		namingStrategy = NamingStrategyEnum.getNamingStrategyByString(PropertyReader.getProperty("naming_strategy"));
	}
	
	public static void main(String[] args) {
		generateSqls();
		generateStatements();
	}

	private static void generateSqls() {
		for (SqlsEnum sqlsEnum : Util.getSqls()) {
			switch (sqlsEnum) {
			case INSERT:
				print(dmlService.getInsertSQLStatement(ExampleModel.class, namingStrategy));
				break;
			default:
				break;
			}
		}
	}

	private static void generateStatements() {
		for (StatementsEnum statementsEnum : Util.getStatements()) {
			switch (statementsEnum) {
			case PSTM:
				print(javaService.getPreparedStatement( ExampleModel.class));
				break;
			case RESULTSET:
				print(javaService.getResultSetStatement(ExampleModel.class, namingStrategy));
				break;
			default:
				break;
			}
		}
	}
	
	static void print(String string) {
		System.out.println(string);
	}
}