package com.generator.statement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;

public class InterpretedClassFile implements InterpretedClass {
	
	private JavaClass javaClass;
	private static final String PACKAGE_SEPARATOR = ".";
//	private static final String COLUMN_ANNOTATION = "javax/persistence/Column";
	
	public InterpretedClassFile(JavaClass javaClass) {
		this.javaClass = javaClass;
	}
	
	@Override
	public List<ClassField> getClassFieldList() {
		List<ClassField> classFieldList = new ArrayList<ClassField>();
		for (Field field : javaClass.getFields()) {
			classFieldList.add(getClassField(field));
		}
		System.out.println("Class");
		return classFieldList;
	}

	private ClassField getClassField(Field field) {
		ClassField classField = new ClassField(getType(field), field.getName());
		AnnotationEntry[] annotationEntries = field.getAnnotationEntries();
		if(annotationEntries.length > 0) {
			getAnnotationMap(annotationEntries);
		}
		return classField;
	}

	private String getType(Field field) {
		String fieldType = field.getType().toString();
		if(fieldType.contains(PACKAGE_SEPARATOR)) {
			return fieldType.substring(fieldType.lastIndexOf(PACKAGE_SEPARATOR) + 1, fieldType.length());
		}
		return fieldType;
	}

	private Map<String, Map<String, String>> getAnnotationMap(AnnotationEntry[] annotationEntries) {
		Map<String, Map<String, String>> annotationMap = new HashMap<String, Map<String, String>>();
		for (AnnotationEntry annotationEntry : annotationEntries) {
			annotationMap.put(annotationEntry.getAnnotationType(), getAnnotationAttributesMap(annotationEntry));
		}
		return annotationMap;
	}

	private Map<String, String> getAnnotationAttributesMap(AnnotationEntry annotationEntry) {
		Map<String, String> annotationsAttributesMap = new HashMap<String, String>();
		for (ElementValuePair elementValuePair : annotationEntry.getElementValuePairs()) {
			annotationsAttributesMap.put(elementValuePair.getNameString(), elementValuePair.getValue().stringifyValue());
		}
		return annotationsAttributesMap;
	}

}