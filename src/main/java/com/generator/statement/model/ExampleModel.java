package com.generator.statement.model;

import java.util.Date;

public class ExampleModel {
	
	private int intValue;
	private String stringValue;
	private Date dateValue;
	private boolean booleanValue;
	private Long longValue;
	private float floatValue;
	private Double doubleValue;
	private ExampleObject exampleObject;
	
	public Double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	public boolean isBooleanValue() {
		return booleanValue;
	}
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
	public Long getLongValue() {
		return longValue;
	}
	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}
	public float getFloatValue() {
		return floatValue;
	}
	public void setFloatValue(float floatValue) {
		this.floatValue = floatValue;
	}
	public ExampleObject getExampleObject() {
		return exampleObject;
	}
	public void setExampleObject(ExampleObject exampleObject) {
		this.exampleObject = exampleObject;
	}

}