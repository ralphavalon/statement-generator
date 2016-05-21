package com.generator.statement.factory;

import org.hibernate.cfg.NamingStrategy;

import com.generator.statement.config.Config;
import com.generator.statement.enums.NamingStrategyEnum;
import com.generator.statement.enums.SqlStatementEnum;
import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractSQLStatement;
import com.generator.statement.statement.sql.DeleteSQLStatement;
import com.generator.statement.statement.sql.InsertSQLStatement;
import com.generator.statement.statement.sql.SelectSQLStatement;
import com.generator.statement.statement.sql.UpdateSQLStatement;

public enum SqlStatementFactory {
	
	INSERT_STATEMENT(SqlStatementEnum.INSERT, InsertSQLStatement.class),
	UPDATE_STATEMENT(SqlStatementEnum.UPDATE, UpdateSQLStatement.class),
	SELECT_STATEMENT(SqlStatementEnum.SELECT, SelectSQLStatement.class),
	DELETE_STATEMENT(SqlStatementEnum.DELETE, DeleteSQLStatement.class);
	
	private SqlStatementEnum sqlStatementEnum;
	private Class<? extends AbstractSQLStatement> sqlStatement;
	private static NamingStrategy namingStrategy = NamingStrategyEnum.getNamingStrategyByString(Config.NAMING_STRATEGY);
	
	private SqlStatementFactory(SqlStatementEnum sqlStatementEnum, Class<? extends AbstractSQLStatement> sqlStatement) {
		this.sqlStatementEnum = sqlStatementEnum;
		this.sqlStatement = sqlStatement;
	}
	
	public static AbstractSQLStatement getSqlStatement(SqlStatementEnum sqlStatementEnum, InterpretedClass interpretedClass) throws Exception {
		for (SqlStatementFactory sqlStatementValue : SqlStatementFactory.values()) {
			if(sqlStatementValue.sqlStatementEnum.equals(sqlStatementEnum)) {
				return sqlStatementValue.sqlStatement.getConstructor(InterpretedClass.class, NamingStrategy.class).newInstance(interpretedClass, namingStrategy);
			}
		}
		return null;
	}

}