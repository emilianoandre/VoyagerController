package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.UserDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainType;
import com.voyagerproject.domain.entities.DomainUser;
import com.voyagerproject.domain.exceptions.DomainResultNotFoundException;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.User;
import com.voyagerproject.model.UserType;
import com.voyagerproject.util.DomainUtil;

/**
 * @author EAndre
 *
 */
public class UserController implements IVoyagerDomainController{

	// DAOs
	UserDAO userDao = new UserDAO();
	
	private static final Log log = LogFactory.getLog(UserController.class);
	
	/**
	 * Gets the complete list of users
	 * 
	 * @return Users collection
	 * @throws Exception 
	 */
	public Collection<DomainUser> getUsers() throws Exception {
		Collection<DomainUser> userTypes;
		
		try {
			userTypes = DomainUser.getDomainUserList(userDao.getList());
		} catch (Exception ex) {
			log.error("Failed to fetch user list");
			throw ex;
		}
		
		return userTypes;
	}
	
	/**
	 * Creates a user in the system
	 * 
	 * @param userName of the new user
	 * @param name of the new user
	 * @param email of the new user
	 * @param password of the new user
	 * @param userTypeId of the new user
	 * @return createdUser
	 * @throws Exception 
	 */
	public DomainUser createUser(String userName, String name, String email, String password, DomainType domainUserType) throws Exception {

		// Create User
		UserType userType = new UserType();
		userType.setIdUserType(domainUserType.getIdType());
		userType.setName(domainUserType.getName());
		
		User user = new User(userName, name, email, DomainUtil.calculateHash(password), userType, null);
		try {
			Integer userId = userDao.persist(user);
			user.setIdUser(userId);
		} catch (Exception ex) {
			log.error("Failed to create user with userName: " + userName, ex);			
			throw ex;
		}	
		
		return new DomainUser(user, false);
	}
	
	/**
	 * Deletes a user by it's userName
	 * 
	 * @param userId of the user to delete
	 * @throws Exception 
	 */
	public void deleteUser(Integer userId) throws Exception {
		try {
			User user = userDao.findById(userId);
			userDao.remove(user);
		} catch (ResultNotFoundException rnfe) {
			log.info("No user found with id: " + userId);
			throw rnfe;
		} catch (Exception ex) {
			log.error("Failed to delete user with id: " + userId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Validates the log in of the user in the system
	 * 
	 * @param userName
	 * @param password
	 * @return DomainUser logged in user 
	 */
	public DomainUser logIn(String userName, String password) throws DomainResultNotFoundException{
		// Calculate password hash
		String hashedPassword = DomainUtil.calculateHash(password);
		DomainUser loggedUser = null;
		try {
			// Log in user
			loggedUser = new DomainUser(userDao.logIn(userName, hashedPassword), false);
		} catch (Exception ex) {
			log.error("Failed to log in user: " + userName, ex);
			throw new DomainResultNotFoundException(ex.getMessage());
		}
		return loggedUser;
	}
	
	/**
	 * Logs out the user from the system
	 * 
	 * @param userName
	 * @param password
	 * @return DomainUser logged out user 
	 */
	public DomainUser logOut(String userName, String password) throws DomainResultNotFoundException{
		// Calculate password hash
		String hashedPassword = DomainUtil.calculateHash(password);
		DomainUser loggedUser = null;
		try {
			// Log in user
			loggedUser = new DomainUser(userDao.logOut(userName, hashedPassword), false);
		} catch (Exception ex) {
			log.error("Failed to log out user: " + userName, ex);
			throw new DomainResultNotFoundException(ex.getMessage());
		}
		return loggedUser;
	}
	
	/**
	 * Updates a user
	 * 
	 * @param DomainUser user
	 * @return DomainUser updatedUser
	 * @throws Exception 
	 */
	public void updateUser(DomainUser updatedUser) throws Exception {
		try {
			User user;
			try {
				// First we look for the user by the Id
				user = userDao.findById(updatedUser.getIdUser());
			} catch (ResultNotFoundException rnfe) {
				log.info("No user found with userName: " + updatedUser.getUserName());
				throw rnfe;
			}
			
			// Encode the password
			updatedUser.setPassword(DomainUtil.calculateHash(updatedUser.getPassword()));
			// Update the entry values and merge
			user =  DomainUser.createUserFromDomainUser(updatedUser, user);
			userDao.merge(user);
		} catch (Exception ex) {
			log.error("Failed to update user with userName: " + updatedUser.getUserName(), ex);			
			throw ex;
		}
	}
}
