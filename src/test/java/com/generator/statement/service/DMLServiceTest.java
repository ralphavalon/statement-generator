package com.generator.statement.service;

import static org.junit.Assert.assertEquals;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.Test;

import com.generator.statement.Results;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.service.impl.DMLServiceImpl;

public class DMLServiceTest extends Results {

	private DMLService dmlService = new DMLServiceImpl();

	@Test
	public void testSuccessGetInsertSQLStatementEJB3NamingStrategy() {
		assertEquals(insertSQLStatementStringEJB3NamingStrategy, dmlService.getInsertSQLStatement(ExampleModel.class, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetInsertSQLStatementImprovedNamingStrategy() {
		assertEquals(insertSQLStatementStringImprovedNamingStrategy, dmlService.getInsertSQLStatement(ExampleModel.class, new ImprovedNamingStrategy()));
	}

}