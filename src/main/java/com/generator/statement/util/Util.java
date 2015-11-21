package com.generator.statement.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.generator.statement.config.Config;
import com.generator.statement.enums.JavaStatementEnum;
import com.generator.statement.enums.SqlStatementEnum;

public class Util {
	
	private static Set<JavaStatementEnum> statementsSet = new HashSet<JavaStatementEnum>();
	private static Set<SqlStatementEnum> sqlsSet = new HashSet<SqlStatementEnum>();
	
	public static String removeLastComma(String string) {
		if (StringUtils.isNotBlank(string)) {
			return string.substring(0, string.lastIndexOf(","));
		}
		return string;
	}
	
	@SuppressWarnings("unchecked")
	public static Set<JavaStatementEnum> getStatements() {
		for(String statementString : removeUselessData(Config.STATEMENTS)) {
			statementsSet.add(JavaStatementEnum.getByName(statementString));
		}
		return (Set<JavaStatementEnum>) removeNull(statementsSet);
	}
	
	@SuppressWarnings("unchecked")
	public static Set<String> removeUselessData(String string) {
		Set<String> usefulData = new HashSet<String>(Arrays.asList(string.replace(" ", "").split(",")));
		return (Set<String>) removeNull(usefulData);
	}

	@SuppressWarnings("unchecked")
	public static Set<SqlStatementEnum> getSqls() {
		for(String sqlsString : removeUselessData(Config.SQLS)) {
			sqlsSet.add(SqlStatementEnum.getByName(sqlsString));
		}
		return (Set<SqlStatementEnum>) removeNull(sqlsSet);
	}
	
	public static Set<?> removeNull(Set<?> set) {
		set.removeAll(Collections.singleton(null));
		return set;
	}
	
}