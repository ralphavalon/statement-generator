package com.generator.statement.model;

import java.util.List;

public interface InterpretedClass {
	
	String getName();
	
	List<ClassField> getClassFieldList();
	
	boolean hasClassAnnotation(String annotation);
	
	String getClassAnnotationAttribute(String annotation, String attribute);

}