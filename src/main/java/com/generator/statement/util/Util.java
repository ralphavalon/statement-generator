package com.generator.statement.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.generator.statement.config.SqlsEnum;
import com.generator.statement.config.StatementsEnum;

public class Util {
	
	private static Set<StatementsEnum> statementsSet = new HashSet<StatementsEnum>();
	private static Set<SqlsEnum> sqlsSet = new HashSet<SqlsEnum>();
	
	public static String removeLastComma(String string) {
		if (StringUtils.isNotBlank(string)) {
			return string.substring(0, string.lastIndexOf(","));
		}
		return string;
	}
	
	public static Set<StatementsEnum> getStatements() {
		for(String statementString : removeUselessData(PropertyReader.getProperty("statements"))) {
			statementsSet.add(StatementsEnum.getByName(statementString));
		}
		statementsSet.removeAll(Collections.singleton(null));
		return statementsSet;
	}
	
	public static Set<String> removeUselessData(String string) {
		Set<String> usefulData = new HashSet<String>(Arrays.asList(string.replace(" ", "").split(",")));
		usefulData.removeAll(Collections.singleton(null));
		return usefulData;
	}

	public static Set<SqlsEnum> getSqls() {
		for(String sqlsString : removeUselessData(PropertyReader.getProperty("sqls"))) {
			sqlsSet.add(SqlsEnum.getByName(sqlsString));
		}
		sqlsSet.removeAll(Collections.singleton(null));
		return sqlsSet;
	}
	
}