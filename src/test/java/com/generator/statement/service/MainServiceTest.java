package com.generator.statement.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.generator.statement.model.ExampleModel;

public class MainServiceTest {

	private MainService mainService = new MainServiceImpl();
	private String preparedStatementString = ""
			+ "pstm.setInt(1, exampleModel.getIntValue());\n"
			+ "pstm.setString(2, exampleModel.getStringValue());\n"
			+ "pstm.setDate(3, new Date(exampleModel.getDateValue().getTime()));\n"
			+ "pstm.setBoolean(4, exampleModel.isBooleanValue());\n"
			+ "pstm.setLong(5, exampleModel.getLongValue());\n"
			+ "pstm.setFloat(6, exampleModel.getFloatValue());\n"
			+ "pstm.setDouble(7, exampleModel.getDoubleValue());\n"
			+ "pstm.setObject(8, exampleModel.getExampleObject());\n";
	private String insertSQLStatementString = "INSERT INTO tableName(intValue,stringValue,dateValue,booleanValue,longValue,floatValue,doubleValue,exampleObject) VALUES (?,?,?,?,?,?,?,?);";

	@Test
	public void testSuccessGetPreparedStatement() {
		assertEquals(preparedStatementString, mainService.getPreparedStatement(ExampleModel.class));
	}

	@Test
	public void testSuccessGetInsertSQLStatement() {
		assertEquals(insertSQLStatementString, mainService.getInsertSQLStatement(ExampleModel.class,
																				"tableName"));
	}

}