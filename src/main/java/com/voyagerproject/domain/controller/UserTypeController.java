package com.voyagerproject.domain.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.UserTypeDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainUserType;
import com.voyagerproject.model.UserType;

/**
 * Controller that handles all the requests for User Type
 * @author EAndre
 *
 */
public class UserTypeController implements IVoyagerDomainController {
	
	// DAOs
	UserTypeDAO userTypeDao = new UserTypeDAO();
	
	private static final Log log = LogFactory.getLog(UserTypeController.class);
	
	public Collection<UserType> getUserTypes() {
		Collection<UserType> userTypes;
		userTypes = userTypeDao.getList();
		return userTypes;
	}

}