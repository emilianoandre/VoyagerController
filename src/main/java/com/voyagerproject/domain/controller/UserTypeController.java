package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.UserTypeDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainType;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.UserType;
import com.voyagerproject.util.ModelUtils;

/**
 * Controller that handles all the requests for User Type
 * @author EAndre
 *
 */
public class UserTypeController implements IVoyagerDomainController {
	
	// DAOs
	UserTypeDAO userTypeDao = new UserTypeDAO();
	
	private static final Log log = LogFactory.getLog(BugSystemTypeController.class);
	
	/**
	 * Gets the complete list of user types
	 * 
	 * @return UserType collection
	 * @throws Exception 
	 */
	public Collection<DomainType> getUserTypes() throws Exception {
		Collection<DomainType> userTypes;
		
		try {
			// Convert the list to generic type and get the Domain Type List
			userTypes = DomainType.getDomainTypeList(ModelUtils.getGenericTypeList(userTypeDao.getList()));
		} catch (Exception ex) {
			log.error("Failed to fetch user type list");
			throw ex;
		}
		
		return userTypes;
	}
	
	/**
	 * Creates a user type in the system
	 * 
	 * @param name the name of the user type
	 * @return createdUserType
	 * @throws Exception 
	 */
	public DomainType createUserType(String name) throws Exception {

		// Create User
		UserType userType = new UserType();
		userType.setName(name);
		try {
			Integer userTypeId = userTypeDao.persist(userType);
			userType.setIdUserType(userTypeId);
		} catch (Exception ex) {
			log.error("Failed to create user type with name: " + name, ex);
			throw ex;
		}	
		
		// Create object to return
		DomainType domainUserType = new DomainType(userType);
		
		return domainUserType;
	}
	
	/**
	 * Deletes a user type by it's id
	 * 
	 * @param userTypeId
	 * @throws Exception 
	 */
	public void deleteUserType(Integer userTypeId) throws Exception {		
		try {
			UserType userType = userTypeDao.findById(userTypeId);
			userTypeDao.remove(userType);
		} catch (ResultNotFoundException rnfe) {
			log.info("No user type found with id: " + userTypeId);
			throw rnfe;
		} catch (Exception ex) {
			log.error("Failed to delete user type with ID: " + userTypeId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a user type
	 * 
	 * @param userType to update
	 * @return updatedUserType
	 * @throws Exception 
	 */
	public void updateUserType(DomainType updatedUserType) throws Exception {
		try {
			UserType userType;
			try {
				// First we look for the user type by the userName
				userType = userTypeDao.findById(updatedUserType.getIdType());
			} catch (ResultNotFoundException rnfe) {
				log.info("No user type found with id: " + updatedUserType.getIdType());
				throw rnfe;
			}
			
			// Update the entry values and merge
			userType.setName(updatedUserType.getName());
			userTypeDao.merge(userType);
		} catch (Exception ex) {
			log.error("Failed to update user with id: " + updatedUserType.getIdType(), ex);			
			throw ex;
		}
	}

}