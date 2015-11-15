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
import com.generator.statement.service.impl.JavaServiceImpl;

public class JavaServiceTest {

	private JavaService javaService = new JavaServiceImpl();
	private InterpretedClass interpretedClass;

	@Test
	public void testSuccessGetPreparedStatementJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(Results.preparedStatementString, javaService.getPreparedStatement(interpretedClass));
	}
	
	@Test
	public void testSuccessGetPreparedStatementClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		assertEquals(Results.preparedStatementString, javaService.getPreparedStatement(interpretedClass));
	}

	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(Results.resultSetStringEJB3NamingStrategy, javaService.getResultSetStatement(interpretedClass, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(getJavaClass());
		assertEquals(Results.resultSetStringEJB3NamingStrategy, javaService.getResultSetStatement(interpretedClass, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(Results.resultSetStringImprovedNamingStrategy, javaService.getResultSetStatement(interpretedClass, new ImprovedNamingStrategy()));
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategyClass() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(Results.resultSetStringImprovedNamingStrategy, javaService.getResultSetStatement(interpretedClass, new ImprovedNamingStrategy()));
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