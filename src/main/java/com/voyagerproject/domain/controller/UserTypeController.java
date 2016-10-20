package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.UserTypeDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainUserType;

/**
 * Controller that handles all the requests for User Type
 * @author EAndre
 *
 */
public class UserTypeController implements IVoyagerDomainController {
	
	// DAOs
	UserTypeDAO userTypeDao = new UserTypeDAO();
	
	private static final Log log = LogFactory.getLog(UserTypeController.class);
	
	/**
	 * Gets the complete list of user types
	 * 
	 * @return UserType collection
	 * @throws Exception 
	 */
	public Collection<DomainUserType> getUserTypes() throws Exception {
		Collection<DomainUserType> userTypes;
		
		try {
			userTypes = DomainUserType.getDomainUserTypeList(userTypeDao.getList());
		} catch (Exception ex) {
			log.error("Failed to fetch user type list");
			throw ex;
		}
		
		return userTypes;
	}

}