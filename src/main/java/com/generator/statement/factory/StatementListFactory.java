package com.generator.statement.factory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.NamingStrategyEnum;
import com.generator.statement.config.StatementsEnum;
import com.generator.statement.service.InterpretedClass;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.statement.impl.InsertPreparedStatementImpl;
import com.generator.statement.statement.impl.ResultSetStatementImpl;
import com.generator.statement.util.PropertyReader;
import com.generator.statement.util.Util;

public class StatementListFactory {
	
	private static NamingStrategy namingStrategy = NamingStrategyEnum.getNamingStrategyByString(PropertyReader.getProperty("naming_strategy"));
	
	public static List<AbstractStatement> factory(InterpretedClass interpretedClass) {
		List<AbstractStatement> statementList = new ArrayList<AbstractStatement>();
		for (StatementsEnum statementsEnum : Util.getStatements()) {
			switch (statementsEnum) {
			case PSTM:
				statementList.add(new InsertPreparedStatementImpl(interpretedClass, namingStrategy));
				break;
			case RESULTSET:
				statementList.add(new ResultSetStatementImpl(interpretedClass, namingStrategy));
				break;
			default:
				break;
			}
		}
		return statementList;
	}

}