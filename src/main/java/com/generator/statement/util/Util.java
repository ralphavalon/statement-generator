package com.generator.statement.util;

import org.apache.commons.lang3.StringUtils;

public class Util {
	
	public static String removeLastComma(String string) {
		if (StringUtils.isNotBlank(string)) {
			return string.substring(0, string.lastIndexOf(","));
		}
		return string;
	}
}
