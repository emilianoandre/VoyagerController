package com.voyagerproject.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voyagerproject.model.User;
import com.voyagerproject.model.UserType;

/**
 * Domain User Class
 *
 * @author EAndre
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainUser implements Serializable{

	private static final long serialVersionUID = -4935082656288280812L;
	
	private int idUser;
	private String userName;
	private String name;
	private String email;
	private String password;
	private DomainType userType;
	private String token;

	public DomainUser() {
	}

	public DomainUser(int idUser, String userName, String name, String email, String password, String token, int idUserType) {
		this.idUser = idUser;
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.token = token;
		
		DomainType userType = new DomainType();
		userType.setIdType(idUserType);
		
		this.userType = userType;
	}
	
	public DomainUser(String userName, String name, String email, String password, String token, int idUserType) {
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.token = token;
		
		DomainType userType = new DomainType();
		userType.setIdType(idUserType);
		
		this.userType = userType;
	}
	
	/**
	 * Creates a DomainUser from a model user
	 * 
	 * @param user
	 * @param boolean. If true the password will be saved
	 */
	public DomainUser(User user, Boolean setPassword) {
		this.idUser = user.getIdUser();
		this.userName = user.getUserName();
		this.name = user.getName();
		this.email = user.getEmail();
		this.token = user.getToken();
		if (setPassword) {
			this.password = user.getPassword();
		}
		
		DomainType userType = new DomainType(user.getUserType().getIdUserType(), user.getUserType().getName());
		this.userType = userType;
	}
	
	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userType
	 */
	public DomainType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(DomainType userType) {
		this.userType = userType;
	}
	
	/**
	 * 
	 * @return the user token for this session
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * 
	 * @param token to use in this session
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * Creates a User from a DomainUser
	 * 
	 * @param DomainUser domainUser with values to update
	 * @param User (optional) userToUpdate in case it already exists in the DB
	 * @return
	 */
	public static User createUserFromDomainUser(DomainUser domainUser, User userToUpdate) {
		User user = new User();
		
		if (userToUpdate != null) {
			user = userToUpdate;
		}
		
		user.setName(domainUser.getName());
		user.setPassword(domainUser.getPassword());
		user.setUserName(domainUser.getUserName());
		user.setUserType(new UserType(domainUser.getUserType().getIdType(), "", null));
		
		return user;
	}
	
	/**
	 * Creates a list of DomainUsers from a list of Users
	 * 
	 * @param userList
	 * @return
	 */
	public static Collection<DomainUser> getDomainUserList(Collection<User> userList) {
		Collection<DomainUser> users = new ArrayList<DomainUser>();
		
		// Create a DomainUser from each entry in the userList 
		for (User user : userList) {
			users.add(new DomainUser(user, true));			
		}
		
		return users;
	}
}
