package com.generator.statement.util;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.generator.statement.config.Statements;

public class UtilTest {
	
	private String stringWithLastComma = "?,?,?,?,?,?,?,?,";
	private String stringWithoutLastComma = "?,?,?,?,?,?,?,?";

	@Test
	public void testSuccessRemoveLastComma() {
		assertEquals(stringWithoutLastComma, Util.removeLastComma(stringWithLastComma));
	}
	
	@Test
	public void testSuccessGetStatements() {
		Set<Statements> statements = Util.getStatements();
		assertEquals(2, statements.size());
		assertTrue(statements.contains(Statements.PSTM));
		assertTrue(statements.contains(Statements.RESULTSET));
	}
	
	@Test
	public void testSuccessRemoveUselessData() {
		Set<String> usefulData = Util.removeUselessData(PropertyReader.getProperty("ignore_fields"));
		assertEquals(2, usefulData.size());
		assertTrue(usefulData.contains("stringToIgnore"));
		assertTrue(usefulData.contains("dateToIgnore"));
	}

}