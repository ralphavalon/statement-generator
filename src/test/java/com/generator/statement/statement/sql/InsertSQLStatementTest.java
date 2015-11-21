package com.generator.statement.statement.sql;

import static org.junit.Assert.assertEquals;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.Test;

import com.generator.statement.AbstractTest;
import com.generator.statement.Results;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.model.InterpretedClass;
import com.generator.statement.statement.AbstractSQLStatement;
import com.generator.statement.statement.sql.InsertSQLStatement;

public class InsertSQLStatementTest extends AbstractTest {

	private AbstractSQLStatement statement;
	private InterpretedClass interpretedClass;

	@Test
	public void testSuccessGetStatementEJB3NamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		statement = new InsertSQLStatement(interpretedClass, new EJB3NamingStrategy());
		assertEquals(Results.insertSQLStatementStringEJB3NamingStrategy, statement.getStatement());
	}
	
	@Test
	public void testSuccessGetStatementImprovedNamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		statement = new InsertSQLStatement(interpretedClass, new ImprovedNamingStrategy());
		assertEquals(Results.insertSQLStatementStringImprovedNamingStrategy, statement.getStatement());
	}
	
	@Test
	public void testSuccessGetStatementEJB3NamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		statement = new InsertSQLStatement(interpretedClass, new EJB3NamingStrategy());
		assertEquals(Results.insertSQLStatementStringEJB3NamingStrategy, statement.getStatement());
	}
	
	@Test
	public void testSuccessGetStatementImprovedNamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		statement = new InsertSQLStatement(interpretedClass, new ImprovedNamingStrategy());
		assertEquals(Results.insertSQLStatementStringImprovedNamingStrategy, statement.getStatement());
	}
	
}