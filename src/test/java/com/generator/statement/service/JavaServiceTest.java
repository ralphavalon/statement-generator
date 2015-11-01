package com.generator.statement.service;

import static org.junit.Assert.assertEquals;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.Test;

import com.generator.statement.Results;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.service.impl.JavaServiceImpl;

public class JavaServiceTest extends Results {

	private JavaService javaService = new JavaServiceImpl();

	@Test
	public void testSuccessGetPreparedStatement() {
		assertEquals(preparedStatementString, javaService.getPreparedStatement(ExampleModel.class));
	}

	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategy() {
		assertEquals(resultSetStringEJB3NamingStrategy, javaService.getResultSetStatement(ExampleModel.class, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategy() {
		assertEquals(resultSetStringImprovedNamingStrategy, javaService.getResultSetStatement(ExampleModel.class, new ImprovedNamingStrategy()));
	}

}