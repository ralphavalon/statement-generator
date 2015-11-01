package com.generator.statement;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.NamingStrategyEnum;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.service.DMLService;
import com.generator.statement.service.DMLServiceImpl;
import com.generator.statement.service.MainService;
import com.generator.statement.service.MainServiceImpl;
import com.generator.statement.util.PropertyReader;

public class Main {
	
	public static void main(String[] args) {
		MainService mainService = new MainServiceImpl();
		DMLService dmlService = new DMLServiceImpl();
		NamingStrategy namingStrategy = NamingStrategyEnum.getNamingStrategyByString(PropertyReader.getProperty("naming_strategy"));
		print(dmlService.getInsertSQLStatement(ExampleModel.class, namingStrategy));
		print(mainService.getPreparedStatement( ExampleModel.class));
		print(mainService.getResultSetStatement(ExampleModel.class, namingStrategy));
	}
	
	static void print(String string) {
		System.out.println(string);
	}
}