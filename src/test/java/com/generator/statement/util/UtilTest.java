package com.generator.statement.util;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.generator.statement.AbstractTest;
import com.generator.statement.enums.JavaStatementEnum;
import com.generator.statement.enums.SqlStatementEnum;

public class UtilTest extends AbstractTest {
	
	private String stringWithLastComma = "?,?,?,?,?,?,?,?,";
	private String stringWithoutLastComma = "?,?,?,?,?,?,?,?";

	@Test
	public void testSuccessRemoveLastComma() {
		assertEquals(stringWithoutLastComma, Util.removeLastComma(stringWithLastComma));
	}
	
	@Test
	public void testSuccessGetStatements() {
		Set<JavaStatementEnum> statements = Util.getStatements();
		assertEquals(2, statements.size());
		assertTrue(statements.contains(JavaStatementEnum.PSTM));
		assertTrue(statements.contains(JavaStatementEnum.RESULTSET));
	}
	
	@Test
	public void testSuccessRemoveUselessData() {
		Set<String> usefulData = Util.removeUselessData(PropertyReader.getProperty("ignore_fields"));
		assertEquals(2, usefulData.size());
		assertTrue(usefulData.contains("stringToIgnore"));
		assertTrue(usefulData.contains("dateToIgnore"));
	}
	
	@Test
	public void testSuccessGetSqls() {
		Set<SqlStatementEnum> sqlsSet = Util.getSqls();
		assertEquals(4, sqlsSet.size());
		assertTrue(sqlsSet.contains(SqlStatementEnum.INSERT));
		assertTrue(sqlsSet.contains(SqlStatementEnum.UPDATE));
		assertTrue(sqlsSet.contains(SqlStatementEnum.SELECT));
		assertTrue(sqlsSet.contains(SqlStatementEnum.DELETE));
	}

}