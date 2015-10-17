package com.generator.statement.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {
	
	private String stringWithLastComma = "?,?,?,?,?,?,?,?,";
	private String stringWithoutLastComma = "?,?,?,?,?,?,?,?";

	@Test
	public void testSuccessRemoveLastComma() {
		assertEquals(stringWithoutLastComma, Util.removeLastComma(stringWithLastComma));
	}

}