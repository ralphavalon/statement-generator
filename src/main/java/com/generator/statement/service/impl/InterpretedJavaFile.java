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
	
	public InterpretedJavaFile(Class<?> klazz) {
		this.klazz = klazz;
	}
	
	@Override
	public List<ClassField> getClassFieldList() {
		List<ClassField> classFieldList = new ArrayList<ClassField>();
		for (Field field : klazz.getDeclaredFields()) {
			classFieldList.add(getClassField(field));
		}
		System.out.println("Java");
		return classFieldList;
	}

	private ClassField getClassField(Field field) {
		ClassField classField = new ClassField(field.getType().getSimpleName(), field.getName());
		Annotation[] annotations = field.getAnnotations();
		if(annotations.length > 0) {
			getAnnotationMap(annotations);
		}
		return classField;
	}

	private Map<String, Map<String, String>> getAnnotationMap(Annotation[] annotations) {
		Map<String, Map<String, String>> annotationMap = new HashMap<String, Map<String, String>>();
		for (Annotation annotation : annotations) {
			annotationMap.put(annotation.annotationType().getName(), getAnnotationAttributesMap(annotation));
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