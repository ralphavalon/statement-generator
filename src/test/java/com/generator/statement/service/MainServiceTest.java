package com.generator.statement.service;

import static org.junit.Assert.assertEquals;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.Test;

import com.generator.statement.Results;
import com.generator.statement.model.ExampleModel;

public class MainServiceTest extends Results {

	private MainService mainService = new MainServiceImpl();

	@Test
	public void testSuccessGetPreparedStatement() {
		assertEquals(preparedStatementString, mainService.getPreparedStatement(ExampleModel.class));
	}

	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategy() {
		assertEquals(resultSetStringEJB3NamingStrategy, mainService.getResultSetStatement(ExampleModel.class, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategy() {
		assertEquals(resultSetStringImprovedNamingStrategy, mainService.getResultSetStatement(ExampleModel.class, new ImprovedNamingStrategy()));
	}

}