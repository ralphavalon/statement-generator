package com.generator.statement.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.generator.statement.AbstractTest;
import com.generator.statement.Results;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.statement.AbstractStatement;
import com.generator.statement.statement.impl.InsertPreparedStatementImpl;

public class InsertPreparedStatementTest extends AbstractTest {

	private AbstractStatement insertPreparedStatement;
	private InterpretedClass interpretedClass;

	@Test
	public void testSuccessgetInsertPreparedStatementJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		insertPreparedStatement = new InsertPreparedStatementImpl(interpretedClass, null);
		assertEquals(Results.preparedStatementString, insertPreparedStatement.getStatement());
	}
	
	@Test
	public void testSuccessgetInsertPreparedStatementClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		insertPreparedStatement = new InsertPreparedStatementImpl(interpretedClass, null);
		assertEquals(Results.preparedStatementString, insertPreparedStatement.getStatement());
	}
	
}