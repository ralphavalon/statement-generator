package com.generator.statement;

public class Results {
	
	protected String preparedStatementString = ""
			+ "pstm.setInt(1, exampleModel.getIntValue());\n"
			+ "pstm.setString(2, exampleModel.getStringValue());\n"
			+ "pstm.setDate(3, new Date(exampleModel.getDateValue().getTime()));\n"
			+ "pstm.setBoolean(4, exampleModel.isBooleanValue());\n"
			+ "pstm.setLong(5, exampleModel.getLongValue());\n"
			+ "pstm.setFloat(6, exampleModel.getFloatValue());\n"
			+ "pstm.setDouble(7, exampleModel.getDoubleValue());\n"
			+ "pstm.setObject(8, exampleModel.getExampleObject());\n";

	protected String resultSetStringEJB3NamingStrategy = ""
			+ "exampleModel.setIntValue(resultSet.getInt(\"intValue\"));\n"
			+ "exampleModel.setStringValue(resultSet.getString(\"stringValue\"));\n"
			+ "exampleModel.setDateValue(resultSet.getDate(\"dateValue\"));\n"
			+ "exampleModel.setBooleanValue(resultSet.getBoolean(\"bool\"));\n"
			+ "exampleModel.setLongValue(resultSet.getLong(\"longValue\"));\n"
			+ "exampleModel.setFloatValue(resultSet.getFloat(\"floatValue\"));\n"
			+ "exampleModel.setDoubleValue(resultSet.getDouble(\"doubleValue\"));\n"
			+ "exampleModel.setExampleObject(resultSet.getObject(\"exampleObject\"));\n";
	
	protected String resultSetStringImprovedNamingStrategy = ""
			+ "exampleModel.setIntValue(resultSet.getInt(\"int_value\"));\n"
			+ "exampleModel.setStringValue(resultSet.getString(\"string_value\"));\n"
			+ "exampleModel.setDateValue(resultSet.getDate(\"date_value\"));\n"
			+ "exampleModel.setBooleanValue(resultSet.getBoolean(\"bool\"));\n"
			+ "exampleModel.setLongValue(resultSet.getLong(\"long_value\"));\n"
			+ "exampleModel.setFloatValue(resultSet.getFloat(\"float_value\"));\n"
			+ "exampleModel.setDoubleValue(resultSet.getDouble(\"double_value\"));\n"
			+ "exampleModel.setExampleObject(resultSet.getObject(\"example_object\"));\n";

	protected String insertSQLStatementStringEJB3NamingStrategy = "INSERT INTO tableName(intValue,stringValue,dateValue,bool,longValue,floatValue,doubleValue,exampleObject) VALUES (?,?,?,?,?,?,?,?);\n";
	
	protected String insertSQLStatementStringImprovedNamingStrategy = "INSERT INTO tableName(int_value,string_value,date_value,bool,long_value,float_value,double_value,example_object) VALUES (?,?,?,?,?,?,?,?);\n";

}