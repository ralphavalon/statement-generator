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
	private String name;
	private static final String PACKAGE_DOT_SEPARATOR = ".";
	private static final String PACKAGE_SLASH_SEPARATOR = "/";
	private List<ClassField> classFieldList;
	
	public InterpretedClassFile(JavaClass javaClass) {
		this.javaClass = javaClass;
		this.name = removePackageDefinition(javaClass.getClassName(), PACKAGE_DOT_SEPARATOR, false);
		initClassFieldList();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public List<ClassField> getClassFieldList() {
		return classFieldList;
	}

	private void initClassFieldList() {
		classFieldList = new ArrayList<ClassField>();
		for (Field field : javaClass.getFields()) {
			classFieldList.add(getClassField(field));
		}
	}

	private ClassField getClassField(Field field) {
		ClassField classField = new ClassField(getType(field), field.getName());
		AnnotationEntry[] annotationEntries = field.getAnnotationEntries();
		if(annotationEntries.length > 0) {
			classField.setAnnotationMap(getAnnotationMap(annotationEntries));
		}
		return classField;
	}

	private String getType(Field field) {
		String fieldType = field.getType().toString();
		if(fieldType.contains(PACKAGE_DOT_SEPARATOR)) {
			return removePackageDefinition(fieldType, PACKAGE_DOT_SEPARATOR, false);
		}
		return fieldType;
	}

	private String removePackageDefinition(String string, String separator, boolean removeSemicolon) {
		if(removeSemicolon) {
			return string.substring(string.lastIndexOf(separator) + 1, string.length() - 1);	
		}
		return string.substring(string.lastIndexOf(separator) + 1, string.length());
	}
	
	@Override
	public String getClassAnnotationAttribute(String annotation, String attribute) {
		for (AnnotationEntry annotationEntry : javaClass.getAnnotationEntries()) {
			String annotationType = annotationEntry.getAnnotationType();
			if(removePackageDefinition(annotationType, PACKAGE_SLASH_SEPARATOR, true).equals(annotation)) {
				return getAttribute(annotationEntry, attribute); 
			}
		}
		return "";
	}

	private String getAttribute(AnnotationEntry annotationEntry, String attribute) {
		for (ElementValuePair elementValuePair : annotationEntry.getElementValuePairs()) {
			if(elementValuePair.getNameString().equalsIgnoreCase(attribute)) {
				return elementValuePair.getValue().stringifyValue();
			}
		}
		return "";
	}
	
	public boolean hasClassAnnotation(String annotation) {
		for (AnnotationEntry annotationEntry : javaClass.getAnnotationEntries()) {
			String annotationType = annotationEntry.getAnnotationType();
			if(removePackageDefinition(annotationType, PACKAGE_SLASH_SEPARATOR, true).equals(annotation)) {
				return true;
			}
		}
		return false;
	}
	
	

	private Map<String, Map<String, String>> getAnnotationMap(AnnotationEntry[] annotationEntries) {
		Map<String, Map<String, String>> annotationMap = new HashMap<String, Map<String, String>>();
		for (AnnotationEntry annotationEntry : annotationEntries) {
			String annotationType = annotationEntry.getAnnotationType();
			annotationMap.put(removePackageDefinition(annotationType, PACKAGE_SLASH_SEPARATOR, true), getAnnotationAttributesMap(annotationEntry));
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