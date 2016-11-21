package com.voyagerproject.domain.entities;

import java.util.ArrayList;
import java.util.Collection;

import com.voyagerproject.model.Type;

/**
 * Domain Type class, user for all Types
 * 
 * @author EAndre
 *
 */
public class DomainType {
	
	private int idType;
	private String name;

	public DomainType() {
	}

	public DomainType(int idType, String name) {
		this.setIdType(idType);
		this.setName(name);
	}
	
	public DomainType(String name) {
		this.setName(name);
	}
	
	/**
	 * Creates a DomainType from a Model Type
	 * 
	 * @param userType
	 */
	public DomainType(Type type) {
		this.setName(type.getName());
		this.setIdType(type.getId());
	}

	/**
	 * @return the idType
	 */
	public int getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(int idType) {
		this.idType = idType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Creates a list of DomainTypes from a list of Types
	 * 
	 * @param typeList the list of types to use
	 * @return
	 */
	public static Collection<DomainType> getDomainTypeList(Collection<Type> typeList) {
		Collection<DomainType> types = new ArrayList<DomainType>();
		
		// Create a DomainType from each entry in the TypeList 
		for (Type type : typeList) {
			types.add(new DomainType(type));
		}
		
		return types;
	}
}
