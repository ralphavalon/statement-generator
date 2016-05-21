package com.generator.statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.generator.statement.util.PropertyReader;

@PrepareForTest(PropertyReader.class)
@RunWith(PowerMockRunner.class)
public class MainTest extends AbstractTest {
	
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
	public void testSuccessMainImprovedNamingStrategyWithJavaFileWithoutParameter() {
		try {
			Main.main(new String[]{});
			assertTrue(outContent.toString().contains(Results.preparedStatementString));
			assertTrueImprovedNamingStrategy();
			assertFalseEJB3NamingStrategy();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSuccessMainImprovedNamingStrategyWithJavaFileWithParameter() {
		try {
			Main.main(new String[]{"ExampleModel.java"});
			assertTrue(outContent.toString().contains(Results.preparedStatementString));
			assertTrueImprovedNamingStrategy();
			assertFalseEJB3NamingStrategy();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSuccessMainImprovedNamingStrategyWithClassFileWithoutParameter() {
		try {
			PowerMockito.doReturn("class").when(PropertyReader.class, "getProperty", "file_type");
			Main.main(new String[]{});
			assertTrue(outContent.toString().contains(Results.preparedStatementString));
			assertTrueImprovedNamingStrategy();
			assertFalseEJB3NamingStrategy();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSuccessMainImprovedNamingStrategyWithClassFileWithParameter() {
		try {
			Main.main(new String[]{"ExampleModel.class"});
			assertTrue(outContent.toString().contains(Results.preparedStatementString));
			assertTrueImprovedNamingStrategy();
			assertFalseEJB3NamingStrategy();
		} catch (Exception e) {
			fail();
		}
	}

	private void assertFalseEJB3NamingStrategy() {
		assertFalse(outContent.toString().contains(Results.resultSetStringEJB3NamingStrategy));
		assertFalse(outContent.toString().contains(Results.insertSQLStatementStringEJB3NamingStrategy));
		assertFalse(outContent.toString().contains(Results.updateSQLStatementStringEJB3NamingStrategy));
		assertFalse(outContent.toString().contains(Results.selectSQLStatementStringEJB3NamingStrategy));
	}

	private void assertTrueImprovedNamingStrategy() {
		assertTrue(outContent.toString().contains(Results.resultSetStringImprovedNamingStrategy));
		assertTrue(outContent.toString().contains(Results.insertSQLStatementStringImprovedNamingStrategy));
		assertTrue(outContent.toString().contains(Results.updateSQLStatementStringImprovedNamingStrategy));
		assertTrue(outContent.toString().contains(Results.selectSQLStatementStringImprovedNamingStrategy));
	}

}