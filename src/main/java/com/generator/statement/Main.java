package com.generator.statement;

import com.generator.statement.model.ExampleModel;
import com.generator.statement.service.MainService;
import com.generator.statement.service.MainServiceImpl;

public class Main {

	public static void main(String[] args) {
		MainService mainService = new MainServiceImpl();
		print(mainService.getInsertSQLStatement(ExampleModel.class, "tableName"));
		print(mainService.getPreparedStatement(ExampleModel.class));
		print(mainService.getResultSetStatement(ExampleModel.class));
	}
	
	static void print(String string) {
		System.out.println(string);
	}
}