package com.generator.statement.util;

import java.util.Properties;

public class PropertyReader {
	
	private static Properties prop = new Properties();
	 
	static {
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return prop.getProperty(key);
	}

}