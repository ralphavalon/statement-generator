package com.generator.statement.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	
	private static Properties defaultProperties = new Properties();
	private static Properties customProperties = new Properties();
	 
	static {
		try {
			defaultProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			customProperties.load(new FileInputStream(new File("config.properties")));
		}catch(Exception e){}
	}
	
	public static String getProperty(String key){
		if(customProperties.getProperty(key) == null) {
			return defaultProperties.getProperty(key);
		}
		return customProperties.getProperty(key);
	}

}