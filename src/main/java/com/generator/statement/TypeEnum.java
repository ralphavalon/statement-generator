package com.generator.statement;

import java.util.Arrays;
import java.util.List;

public enum TypeEnum {

	STRING(Arrays.asList("String"), "String"), 
	INT(Arrays.asList("int", "Integer"), "Int"), 
	DATE(Arrays.asList("Date"), "Date"),
	DOUBLE(Arrays.asList("double", "Double"), "Double"),
	BOOLEAN(Arrays.asList("boolean", "Boolean"), "Boolean"),
	FLOAT(Arrays.asList("float", "Float"), "Float"),
	LONG(Arrays.asList("long", "Long"), "Long"),
	OBJECT(Arrays.asList("Object"), "Object");

	private List<String> types;
	private String value;

	private TypeEnum(List<String> types, String value) {
		this.types = types;
		this.value = value;
	}

	public List<String> getTypes() {
		return types;
	}

	public String getValue() {
		return value;
	}
	
	public static TypeEnum getTypeEnumByType(String type) {
		for (TypeEnum typeEnum : TypeEnum.values()) {
			if(typeEnum.types.contains(type)) {
				return typeEnum;
			}
		}
		return OBJECT;
	}

}