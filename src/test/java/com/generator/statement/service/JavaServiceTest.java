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

public class JavaServiceTest extends Results {

	private JavaService javaService = new JavaServiceImpl();
	private InterpretedClass interpretedClass;

	@Test
	public void testSuccessGetPreparedStatementJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(preparedStatementString, javaService.getPreparedStatement(ExampleModel.class));
	}

	@Test
	public void testSuccessGetResultSetStatementEJB3NamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(resultSetStringEJB3NamingStrategy, javaService.getResultSetStatement(ExampleModel.class, new EJB3NamingStrategy()));
	}
	
	@Test
	public void testSuccessGetResultSetStatementImprovedNamingStrategyJava() {
		interpretedClass = InterpretedClassFactory.getInterpretedClass(ExampleModel.class);
		assertEquals(resultSetStringImprovedNamingStrategy, javaService.getResultSetStatement(ExampleModel.class, new ImprovedNamingStrategy()));
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