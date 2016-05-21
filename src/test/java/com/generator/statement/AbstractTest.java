package com.generator.statement;

import java.io.IOException;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.doReturn;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.generator.statement.util.PropertyReader;

@PrepareForTest(PropertyReader.class)
@RunWith(PowerMockRunner.class)
public abstract class AbstractTest {
	
	@Before
	public void mockDefaultPropertyReader() throws Exception {
		mockStatic(PropertyReader.class);
		doReturn("console").when(PropertyReader.class, "getProperty", "output");
		doReturn("pstm").when(PropertyReader.class, "getProperty", "prepared_statement");
		doReturn("resultSet").when(PropertyReader.class, "getProperty", "result_set");
		doReturn("stringToIgnore, dateToIgnore").when(PropertyReader.class, "getProperty", "ignore_fields");
		doReturn("INSERT,UPDATE, SELECT").when(PropertyReader.class, "getProperty", "sqls");
		doReturn("PSTM, RESULTSET").when(PropertyReader.class, "getProperty", "statements");
		doReturn("improved").when(PropertyReader.class, "getProperty", "naming_strategy");
		doReturn("java").when(PropertyReader.class, "getProperty", "file_type");
	}
	
	protected JavaClass getJavaClass() {
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