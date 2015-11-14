package com.generator.statement.service;

import java.util.List;

import com.generator.statement.model.ClassField;

public interface InterpretedClass {
	
	String getName();
	
	List<ClassField> getClassFieldList();
	
	boolean hasClassAnnotation(String annotation);
	
	String getClassAnnotationAttribute(String annotation, String attribute);

}