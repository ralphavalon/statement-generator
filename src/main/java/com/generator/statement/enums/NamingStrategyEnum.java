package com.generator.statement.enums;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public enum NamingStrategyEnum {
	
	EJB3("EJB3", new EJB3NamingStrategy()),
	IMPROVED("Improved", new ImprovedNamingStrategy());
	
	private String namingStrategyString;
	private NamingStrategy namingStrategy;
	
	private NamingStrategyEnum(String namingStrategyString, NamingStrategy namingStrategy) {
		this.namingStrategyString = namingStrategyString;
		this.namingStrategy = namingStrategy;
	}
	
	public NamingStrategy getNamingStrategy() {
		return namingStrategy;
	}
	
	public String getNamingStrategyString() {
		return namingStrategyString;
	}
	
	public static NamingStrategy getNamingStrategyByString(String namingStrategyString) {
		for (NamingStrategyEnum namingStrategyEnum : NamingStrategyEnum.values()) {
			if(namingStrategyEnum.getNamingStrategyString().equalsIgnoreCase(namingStrategyString)) {
				return namingStrategyEnum.getNamingStrategy();
			}
		}
		return IMPROVED.getNamingStrategy();
	}

}