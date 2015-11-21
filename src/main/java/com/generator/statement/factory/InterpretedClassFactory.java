package com.generator.statement.factory;

import org.apache.bcel.classfile.JavaClass;

import com.generator.statement.model.InterpretedClass;
import com.generator.statement.model.InterpretedClassFile;
import com.generator.statement.model.InterpretedJavaFile;

public class InterpretedClassFactory {
	
	public static InterpretedClass getInterpretedClass(Object object) {
		if(object instanceof JavaClass) {
			return new InterpretedClassFile((JavaClass) object);
		}
		if(object instanceof Class) { //maintained because JavaClass API is Snapshot version
			return new InterpretedJavaFile((Class<?>) object);
		}
		return null;
	}

}