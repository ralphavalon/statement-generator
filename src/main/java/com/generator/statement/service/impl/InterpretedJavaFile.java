package com.generator.statement.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generator.statement.model.ClassField;
import com.generator.statement.service.InterpretedClass;

public class InterpretedJavaFile implements InterpretedClass {
	
	private Class<?> klazz;
	private String name;
	private List<ClassField> classFieldList;
	
	public InterpretedJavaFile(Class<?> klazz) {
		this.klazz = klazz;
		this.name = klazz.getSimpleName();
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
		for (Field field : klazz.getDeclaredFields()) {
			classFieldList.add(getClassField(field));
		}
	}

	private ClassField getClassField(Field field) {
		ClassField classField = new ClassField(field.getType().getSimpleName(), field.getName());
		Annotation[] annotations = field.getAnnotations();
		if(annotations.length > 0) {
			classField.setAnnotationMap(getAnnotationMap(annotations));
		}
		return classField;
	}
	
	public boolean hasClassAnnotation(String annotation) {
		for (Annotation classAnnotation : klazz.getAnnotations()) {
			if(classAnnotation.annotationType().getSimpleName().equals(annotation)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getClassAnnotationAttribute(String annotation, String attribute) {
		for (Annotation classAnnotation : klazz.getAnnotations()) {
			if(classAnnotation.annotationType().getSimpleName().equals(annotation)) {
				Method[] methods = classAnnotation.annotationType().getDeclaredMethods();
				for (Method method : methods) {
					if(method.getName().equalsIgnoreCase(attribute)) {
						try {
							return method.invoke(classAnnotation).toString();
						} catch (Exception e) {}
					}
				}
			}
		}
		return "";
	}
	
	private Map<String, Map<String, String>> getAnnotationMap(Annotation[] annotations) {
		Map<String, Map<String, String>> annotationMap = new HashMap<String, Map<String, String>>();
		for (Annotation annotation : annotations) {
			annotationMap.put(annotation.annotationType().getSimpleName(), getAnnotationAttributesMap(annotation));
		}
		return annotationMap;
	}

	private Map<String, String> getAnnotationAttributesMap(Annotation annotation) {
		Map<String, String> annotationsAttributesMap = new HashMap<String, String>();
		Method[] methods = annotation.annotationType().getDeclaredMethods();
		for (Method method : methods) {
			try {
				annotationsAttributesMap.put(method.getName(), method.invoke(annotation).toString());
			} catch (Exception e) {}
		}
		return annotationsAttributesMap;
	}

}