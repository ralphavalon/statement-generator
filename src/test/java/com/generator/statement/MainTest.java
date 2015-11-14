package com.generator.statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends Results {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

	@Test
	public void testSuccessMainImprovedNamingStrategyWithJavaFile() {
		try {
			Main.main(null);
			assertTrue(outContent.toString().contains(Results.preparedStatementString));
			assertTrue(outContent.toString().contains(Results.resultSetStringImprovedNamingStrategy));
			assertTrue(outContent.toString().contains(Results.insertSQLStatementStringImprovedNamingStrategy));
			assertFalse(outContent.toString().contains(Results.resultSetStringEJB3NamingStrategy));
			assertFalse(outContent.toString().contains(Results.insertSQLStatementStringEJB3NamingStrategy));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSuccessMainImprovedNamingStrategyWithClassFile() {
		try {
			Main.main(new String[]{"ExampleModel.class"});
			assertTrue(outContent.toString().contains(Results.preparedStatementString));
			assertTrue(outContent.toString().contains(Results.resultSetStringImprovedNamingStrategy));
			assertTrue(outContent.toString().contains(Results.insertSQLStatementStringImprovedNamingStrategy));
			assertFalse(outContent.toString().contains(Results.resultSetStringEJB3NamingStrategy));
			assertFalse(outContent.toString().contains(Results.insertSQLStatementStringEJB3NamingStrategy));
		} catch (Exception e) {
			fail();
		}
	}

}