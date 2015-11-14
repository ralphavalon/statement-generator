package com.generator.statement.model;

import java.util.HashMap;
import java.util.Map;

public class ClassField {
	
	private String name;
	private String type;
	private Map<String, Map<String, String>> annotationMap = new HashMap<String, Map<String,String>>();
	
	public ClassField(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean hasAnnotation(String annotation) {
		return annotationMap.containsKey(annotation);
	}
	
	public String getAnnotationAttribute(String annotation, String attribute) {
		return annotationMap.get(annotation).get(attribute);
	}

	public Map<String, Map<String, String>> getAnnotationMap() {
		return annotationMap;
	}

	public void setAnnotationMap(Map<String, Map<String, String>> annotationMap) {
		this.annotationMap = annotationMap;
	}

}