package com.voyagerproject.domain.entities;

import java.util.ArrayList;
import java.util.Collection;

import com.voyagerproject.model.Permission;

/**
 * Domain Permission class, user for all Permissions
 * 
 * @author EAndre
 *
 */
public class DomainPermission {
	
	private int idPermission;
	private String name;

	public DomainPermission() {
	}

	public DomainPermission(int idPermission, String name) {
		this.setIdPermission(idPermission);
	}
	
	public DomainPermission(String name) {
		this.setName(name);
	}
	
	/**
	 * Creates a DomainPermission from a Model Permission
	 * 
	 * @param userPermission
	 */
	public DomainPermission(Permission permission) {
		this.setName(permission.getName());
		this.setIdPermission(permission.getIdPermission());
	}

	/**
	 * @return the idPermission
	 */
	public int getIdPermission() {
		return idPermission;
	}

	/**
	 * @param idPermission the idPermission to set
	 */
	public void setIdPermission(int idPermission) {
		this.idPermission = idPermission;
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
	 * Creates a list of DomainPermissions from a list of Permissions
	 * 
	 * @param permissionList the list of permissions to use
	 * @return
	 */
	public static Collection<DomainPermission> getDomainPermissionList(Collection<Permission> permissionList) {
		Collection<DomainPermission> permissions = new ArrayList<DomainPermission>();
		
		// Create a DomainPermission from each entry in the PermissionList 
		for (Permission permission : permissionList) {
			permissions.add(new DomainPermission(permission));
		}
		
		return permissions;
	}
}
