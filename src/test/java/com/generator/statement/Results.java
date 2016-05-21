package com.generator.statement;

public class Results {
	
	public static String preparedStatementString = ""
			+ "pstm.setInt(1, exampleModel.getIntValue());\n"
			+ "pstm.setString(2, exampleModel.getStringValue());\n"
			+ "pstm.setDate(3, new Date(exampleModel.getDateValue().getTime()));\n"
			+ "pstm.setBoolean(4, exampleModel.isBooleanValue());\n"
			+ "pstm.setLong(5, exampleModel.getLongValue());\n"
			+ "pstm.setFloat(6, exampleModel.getFloatValue());\n"
			+ "pstm.setDouble(7, exampleModel.getDoubleValue());\n"
			+ "pstm.setObject(8, exampleModel.getExampleObject());\n";

	public static String resultSetStringEJB3NamingStrategy = ""
			+ "exampleModel.setIntValue(resultSet.getInt(\"intValue\"));\n"
			+ "exampleModel.setStringValue(resultSet.getString(\"stringValue\"));\n"
			+ "exampleModel.setDateValue(resultSet.getDate(\"dateValue\"));\n"
			+ "exampleModel.setBooleanValue(resultSet.getBoolean(\"bool\"));\n"
			+ "exampleModel.setLongValue(resultSet.getLong(\"longValue\"));\n"
			+ "exampleModel.setFloatValue(resultSet.getFloat(\"floatValue\"));\n"
			+ "exampleModel.setDoubleValue(resultSet.getDouble(\"doubleValue\"));\n"
			+ "exampleModel.setExampleObject(resultSet.getObject(\"exampleObject\"));\n";
	
	public static String resultSetStringImprovedNamingStrategy = ""
			+ "exampleModel.setIntValue(resultSet.getInt(\"int_value\"));\n"
			+ "exampleModel.setStringValue(resultSet.getString(\"string_value\"));\n"
			+ "exampleModel.setDateValue(resultSet.getDate(\"date_value\"));\n"
			+ "exampleModel.setBooleanValue(resultSet.getBoolean(\"bool\"));\n"
			+ "exampleModel.setLongValue(resultSet.getLong(\"long_value\"));\n"
			+ "exampleModel.setFloatValue(resultSet.getFloat(\"float_value\"));\n"
			+ "exampleModel.setDoubleValue(resultSet.getDouble(\"double_value\"));\n"
			+ "exampleModel.setExampleObject(resultSet.getObject(\"example_object\"));\n";

	public static String insertSQLStatementStringEJB3NamingStrategy = "INSERT INTO exampleModelTable(intValue,stringValue,dateValue,bool,longValue,floatValue,doubleValue,exampleObject) VALUES (?,?,?,?,?,?,?,?);\n";
	
	public static String insertSQLStatementStringImprovedNamingStrategy = "INSERT INTO exampleModelTable(int_value,string_value,date_value,bool,long_value,float_value,double_value,example_object) VALUES (?,?,?,?,?,?,?,?);\n";

	public static String updateSQLStatementStringEJB3NamingStrategy = "UPDATE exampleModelTable SET intValue = ?,stringValue = ?,dateValue = ?,bool = ?,longValue = ?,floatValue = ?,doubleValue = ?,exampleObject = ?;\n";
	
	public static String updateSQLStatementStringImprovedNamingStrategy = "UPDATE exampleModelTable SET int_value = ?,string_value = ?,date_value = ?,bool = ?,long_value = ?,float_value = ?,double_value = ?,example_object = ?;\n";

}