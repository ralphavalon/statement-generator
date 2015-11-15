package com.generator.statement;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.generator.statement.util.PropertyReader;

@PrepareForTest(PropertyReader.class)
@RunWith(PowerMockRunner.class)
public abstract class AbstractTest {
	
	@Before
	public void mockDefaultPropertyReader() throws Exception {
		PowerMockito.mockStatic(PropertyReader.class);
		PowerMockito.doReturn("pstm").when(PropertyReader.class, "getProperty", "prepared_statement");
		PowerMockito.doReturn("resultSet").when(PropertyReader.class, "getProperty", "result_set");
		PowerMockito.doReturn("stringToIgnore, dateToIgnore").when(PropertyReader.class, "getProperty", "ignore_fields");
		PowerMockito.doReturn("INSERT").when(PropertyReader.class, "getProperty", "sqls");
		PowerMockito.doReturn("PSTM, RESULTSET").when(PropertyReader.class, "getProperty", "statements");
		PowerMockito.doReturn("improved").when(PropertyReader.class, "getProperty", "naming_strategy");
		PowerMockito.doReturn("java").when(PropertyReader.class, "getProperty", "file_type");
	}

}