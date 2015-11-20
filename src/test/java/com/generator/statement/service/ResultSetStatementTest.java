package com.generator.statement.service;

import static org.junit.Assert.assertEquals;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.Test;

import com.generator.statement.AbstractTest;
import com.generator.statement.Results;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.statement.impl.ResultSetStatementImpl;

public class ResultSetStatementTest extends AbstractTest {

	private AbstractStatement resultSetStatement;
	private InterpretedClass interpretedClass;

	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		resultSetStatement = new ResultSetStatementImpl(interpretedClass, new EJB3NamingStrategy());
		assertEquals(Results.resultSetStringEJB3NamingStrategy, resultSetStatement.getStatement());
	}
	
	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		resultSetStatement = new ResultSetStatementImpl(interpretedClass, new EJB3NamingStrategy());
		assertEquals(Results.resultSetStringEJB3NamingStrategy, resultSetStatement.getStatement());
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		resultSetStatement = new ResultSetStatementImpl(interpretedClass, new ImprovedNamingStrategy());
		assertEquals(Results.resultSetStringImprovedNamingStrategy, resultSetStatement.getStatement());
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		resultSetStatement = new ResultSetStatementImpl(interpretedClass, new ImprovedNamingStrategy());
		assertEquals(Results.resultSetStringImprovedNamingStrategy, resultSetStatement.getStatement());
	}

}