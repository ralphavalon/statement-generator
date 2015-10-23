package com.generator.statement.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.generator.statement.config.Statements;

public class Util {
	
	private static Set<Statements> statementsSet = new HashSet<Statements>();
	
	public static String removeLastComma(String string) {
		if (StringUtils.isNotBlank(string)) {
			return string.substring(0, string.lastIndexOf(","));
		}
		return string;
	}
	
	public static Set<Statements> getStatements() {
		String[] statementsString = PropertyReader.getProperty("statements").replace(" ", "").split(",");
		for(String statementString : statementsString) {
			statementsSet.add(Statements.getByName(statementString));
		}
		statementsSet.removeAll(Collections.singleton(null));
		return statementsSet;
	}
}
