package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.PermissionDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainPermission;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.Permission;

/**
 * Controller that handles all the requests for Permission
 * @author EAndre
 *
 */
public class PermissionController implements IVoyagerDomainController {
	
	// DAOs
	PermissionDAO permissionDao = new PermissionDAO();
	
	private static final Log log = LogFactory.getLog(BugSystemTypeController.class);
	
	/**
	 * Gets the complete list of permissions
	 * 
	 * @return Permission collection
	 * @throws Exception 
	 */
	public Collection<DomainPermission> getPermissions() throws Exception {
		Collection<DomainPermission> permissions;
		
		try {
			// Convert the list to generic type and get the Domain Type List
			permissions = DomainPermission.getDomainPermissionList(permissionDao.getList());
		} catch (Exception ex) {
			log.error("Failed to fetch permission list");
			throw ex;
		}
		
		return permissions;
	}
	
	/**
	 * Creates a permission in the system
	 * 
	 * @param name the name of the permission
	 * @return createdPermission
	 * @throws Exception 
	 */
	public DomainPermission createPermission(String name) throws Exception {

		// Create Permission
		Permission permission = new Permission();
		permission.setName(name);
		try {
			Integer permissionId = permissionDao.persist(permission);
			permission.setIdPermission(permissionId);
		} catch (Exception ex) {
			log.error("Failed to create permission with name: " + name, ex);
			throw ex;
		}	
		
		// Create object to return
		DomainPermission domainPermission = new DomainPermission(permission);
		
		return domainPermission;
	}
	
	/**
	 * Deletes a permission by it's id
	 * 
	 * @param permissionId
	 * @throws Exception 
	 */
	public void deletePermission(Integer permissionId) throws Exception {		
		try {
			Permission permission = permissionDao.findById(permissionId);
			permissionDao.remove(permission);
		} catch (ResultNotFoundException rnfe) {
			log.info("No permission found with id: " + permissionId);
			throw rnfe;
		} catch (Exception ex) {
			log.error("Failed to delete permission with ID: " + permissionId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a permission
	 * 
	 * @param permission to update
	 * @return updatedPermission
	 * @throws Exception 
	 */
	public void updatePermission(DomainPermission updatedPermission) throws Exception {
		try {
			Permission permission;
			try {
				// First we look for the permission by the permissionName
				permission = permissionDao.findById(updatedPermission.getIdPermission());
			} catch (ResultNotFoundException rnfe) {
				log.info("No permission found with id: " + updatedPermission.getIdPermission());
				throw rnfe;
			}
			
			// Update the entry values and merge
			permission.setName(updatedPermission.getName());
			permissionDao.merge(permission);
		} catch (Exception ex) {
			log.error("Failed to update permission with id: " + updatedPermission.getIdPermission(), ex);			
			throw ex;
		}
	}

}