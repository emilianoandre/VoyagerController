package com.voyagerproject.domain.entities;

import java.util.ArrayList;
import java.util.Collection;

import com.voyagerproject.model.UserType;

/**
 * Domain UserType class
 * 
 * @author EAndre
 *
 */
public class DomainUserType {
	
	private int idUserType;
	private String name;

	public DomainUserType() {
	}

	public DomainUserType(int idUserType, String name) {
		this.setIdUserType(idUserType);
	}
	
	public DomainUserType(String name) {
		this.setName(name);
	}
	
	/**
	 * Creates a DomainUserType from a Model UserType
	 * 
	 * @param userType
	 */
	public DomainUserType(UserType userType) {
		this.setName(userType.getName());
		this.setIdUserType(userType.getIdUserType());
	}

	/**
	 * @return the idUserType
	 */
	public int getIdUserType() {
		return idUserType;
	}

	/**
	 * @param idUserType the idUserType to set
	 */
	public void setIdUserType(int idUserType) {
		this.idUserType = idUserType;
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
	 * Creates a list of DomainUserTypes from a list of UserTypes
	 * 
	 * @param userTypeList
	 * @return
	 */
	public static Collection<DomainUserType> getDomainUserTypeList(Collection<UserType> userTypeList) {
		Collection<DomainUserType> userTypes = new ArrayList<DomainUserType>();
		
		// Create a DomainUserType from each entry in the userTypeList 
		for (UserType userType : userTypeList) {
			userTypes.add(new DomainUserType(userType));			
		}
		
		return userTypes;
	}

}
