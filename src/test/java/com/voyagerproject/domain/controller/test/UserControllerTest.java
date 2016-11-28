package com.voyagerproject.domain.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Rule;
import org.junit.Test;

import com.voyagerproject.domain.controller.UserController;
import com.voyagerproject.domain.entities.DomainType;
import com.voyagerproject.domain.entities.DomainUser;
import com.voyagerproject.domain.exceptions.DomainResultNotFoundException;
import com.voyagerproject.exceptions.ResultNotFoundException;

/**
 * @author EAndre
 *
 */
public class UserControllerTest {
	
	private static final Log log = LogFactory.getLog(UserControllerTest.class);
	
	UserController userController = new UserController();
	
	@Rule
	public ModelTestWatcher testWatcher = new ModelTestWatcher();
    
    /**
     * Tests the user creation
     */
    public void createUserTest()
    {
    	DomainUser user;
		try {
			user = userController.createUser("TestUserName", "Name", "email", "testPassword", new DomainType(1, "test"));
			assertNotNull(user);
		} catch (Exception e) {
			assertNotNull(null);
		}        
    }
    
    /**
     * Tests the duplicate user creation
     */
    @Test
    public void createDuplicateUserTest()
    {
    	DomainUser user = null;
    	try{
    		user = userController.createUser("TestUserNameNonDelete", "Name", "email", "testPassword", new DomainType(1, "test"));
    	} catch (Exception ex) {
    		log.debug("createDuplicateUserTest: Exception " + ex.getMessage());
    	}
    	assertNull(user);
    }
    
    /**
     * Tests updating a user
     */
    @Test
    public void updateUserTest()
    {
    	try{
    		DomainUser domainUser = new DomainUser("TestUserNameNonDelete", "Name", "email", "testPassword1", "", 1);
    		userController.updateUser(domainUser);
    	} catch (Exception ex) {
    		log.debug("updateUserTest: Exception " + ex.getMessage());
    		assertTrue(false);
    	}
    	
    	// If no exception was thrown then the update was complete
    	assertTrue(true);
    }
    
    /**
     * Tests the user deletion
     */    
    public void deleteUserTest()
    {
    	try {
    		userController.deleteUser(3);
    	} catch (Exception ex) {
    	  fail(ex.getMessage());
    	}
    	assertTrue(true);
    }
    
    /**
     * Tests the user deletion failure
     * @throws Exception 
     */
    @Test(expected = ResultNotFoundException.class)
    public void deleteUserWrongUserNameTest() throws Exception
    {
    	userController.deleteUser(3);
    }
    
    /**
     * Log in user test
     * 
     */
    @Test
    public void logInTest() {
    	try {
			userController.logIn("TestUserNameNonDelete", "testPassword1");
		} catch (DomainResultNotFoundException e) {
			assertTrue(false);
		}
    }
    
    /**
     * Incorrect Log in test
     * 
     */
    public void logInFailedTest() {
    	try {
			assertNull(userController.logIn("TestUserNameNonDelete", "testPassword2"));
		} catch (DomainResultNotFoundException e) {
			// We are expecting it to fail
			assertTrue(true);
		}
    }

}
