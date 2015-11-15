package com.generator.statement.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.Test;

import com.generator.statement.Results;
import com.generator.statement.factory.InterpretedClassFactory;
import com.generator.statement.model.ExampleModel;
import com.generator.statement.service.impl.DMLServiceImpl;

public class DMLServiceTest {

	private DMLService dmlService = new DMLServiceImpl();
	private InterpretedClass interpretedClass;

	@Test
	public void testSuccessGetInsertSQLStatementEJB3NamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(Results.insertSQLStatementStringEJB3NamingStrategy, dmlService.getInsertSQLStatement(interpretedClass, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetInsertSQLStatementImprovedNamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(Results.insertSQLStatementStringImprovedNamingStrategy, dmlService.getInsertSQLStatement(interpretedClass, new ImprovedNamingStrategy()));
	}
	
	@Test
	public void testSuccessGetInsertSQLStatementEJB3NamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		assertEquals(Results.insertSQLStatementStringEJB3NamingStrategy, dmlService.getInsertSQLStatement(interpretedClass, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetInsertSQLStatementImprovedNamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		assertEquals(Results.insertSQLStatementStringImprovedNamingStrategy, dmlService.getInsertSQLStatement(interpretedClass, new ImprovedNamingStrategy()));
	}
	
	private JavaClass getJavaClass() {
		ClassParser parser = new ClassParser("ExampleModel.class");
		try {
			return parser.parse();
		} catch (ClassFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}