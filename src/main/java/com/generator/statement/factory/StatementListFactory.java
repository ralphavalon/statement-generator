package com.generator.statement.factory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.Config;
import com.generator.statement.enums.JavaStatementEnum;
import com.generator.statement.enums.NamingStrategyEnum;
import com.generator.statement.enums.SqlStatementEnum;
import com.generator.statement.enums.StatementTypeEnum;
import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.statement.java.InsertPreparedStatement;
import com.generator.statement.statement.java.ResultSetStatement;
import com.generator.statement.statement.sql.InsertSQLStatement;
import com.generator.statement.util.Util;

public class StatementListFactory {
	
	private static NamingStrategy namingStrategy = NamingStrategyEnum.getNamingStrategyByString(Config.NAMING_STRATEGY);
	
	public static List<AbstractStatement> factory(InterpretedClass interpretedClass, StatementTypeEnum statementTypeEnum) {
		List<AbstractStatement> statementList = new ArrayList<AbstractStatement>();
		switch (statementTypeEnum) {
		case JAVA:
			statementList.addAll(getJavaStatementList(interpretedClass));
			break;
		case SQL:	
			statementList.addAll(getSQLStatementList(interpretedClass));
			break;
		default:
			break;
		}
		return statementList;
	}
	
	private static List<AbstractStatement> getJavaStatementList(InterpretedClass interpretedClass) {
		List<AbstractStatement> statementList = new ArrayList<AbstractStatement>();
		for (JavaStatementEnum statementsEnum : Util.getStatements()) {
			switch (statementsEnum) {
			case PSTM:
				statementList.add(new InsertPreparedStatement(interpretedClass, namingStrategy));
				break;
			case RESULTSET:
				statementList.add(new ResultSetStatement(interpretedClass, namingStrategy));
				break;
			default:
				break;
			}
		}
		return statementList;
	}

	private static List<AbstractStatement> getSQLStatementList(InterpretedClass interpretedClass) {
		List<AbstractStatement> statementList = new ArrayList<AbstractStatement>();
		for (SqlStatementEnum sqlsEnum : Util.getSqls()) {
			switch (sqlsEnum) {
			case INSERT:
				statementList.add(new InsertSQLStatement(interpretedClass, namingStrategy));
				break;
			default:
				break;
			}
		}
		return statementList;
	}

}