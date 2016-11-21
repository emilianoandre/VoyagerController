package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.BugSystemDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainBugSystem;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.BugSystem;
import com.voyagerproject.model.BugSystemType;

/**
 * @author EAndre
 *
 */
public class BugSystemController implements IVoyagerDomainController{

	// DAOs
	BugSystemDAO bugSystemDao = new BugSystemDAO();
	
	private static final Log log = LogFactory.getLog(BugSystemController.class);
	
	/**
	 * Gets the complete list of bugSystems
	 * 
	 * @return BugSystems collection
	 * @throws Exception 
	 */
	public Collection<DomainBugSystem> getBugSystems() throws Exception {
		Collection<DomainBugSystem> bugSystemTypes;
		
		try {
			bugSystemTypes = DomainBugSystem.getDomainBugSystemList(bugSystemDao.getList());
		} catch (Exception ex) {
			log.error("Failed to fetch bugSystem list");
			throw ex;
		}
		
		return bugSystemTypes;
	}
	
	/**
	 * Creates a bugSystem in the system
	 * 
	 * @param name
	 * @param name
	 * @param email
	 * @param password
	 * @param bugSystemTypeId
	 * @param createdBy
	 * @return createdBugSystem
	 * @throws Exception 
	 */
	public DomainBugSystem createBugSystem(String name, String url, int bugSystemTypeId) throws Exception {

		// Create BugSystem
		BugSystemType bugSystemType = new BugSystemType();
		bugSystemType.setIdBugSystemType(bugSystemTypeId);
		BugSystem bugSystem = new BugSystem();
		try {
			Integer bugSystemId = bugSystemDao.persist(bugSystem);
			bugSystem.setIdBugSystem(bugSystemId);
		} catch (Exception ex) {
			log.error("Failed to create bugSystem with Name: " + name, ex);			
			throw ex;
		}	
		
		return new DomainBugSystem(bugSystem);
	}
	
	/**
	 * Deletes a bugSystem by it's name
	 * 
	 * @param bugSystemId
	 * @throws Exception 
	 */
	public void deleteBugSystem(Integer bugSystemId) throws Exception {
		try {
			BugSystem bugSystem = bugSystemDao.findById(bugSystemId);
			bugSystemDao.remove(bugSystem);
		} catch (ResultNotFoundException rnfe) {
			log.info("No bugSystem found with id: " + bugSystemId);
			throw rnfe;
		}  catch (Exception ex) {
			log.error("Failed to delete bugSystem with id: " + bugSystemId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a bugSystem
	 * 
	 * @param DomainBugSystem bugSystem
	 * @return DomainBugSystem updatedBugSystem
	 * @throws Exception 
	 */
	public void updateBugSystem(DomainBugSystem updatedBugSystem) throws Exception {
		try {
			BugSystem bugSystem;
			try {
				// First we look for the bugSystem by the Id
				bugSystem = bugSystemDao.findById(updatedBugSystem.getIdBugSystem());
			} catch (ResultNotFoundException rnfe) {
				log.info("No bugSystem found with name: " + updatedBugSystem.getName());
				throw rnfe;
			}
			
			// Update the entry values and merge
			bugSystem =  DomainBugSystem.createBugSystemFromDomainBugSystem(updatedBugSystem, bugSystem);
			bugSystemDao.merge(bugSystem);
		} catch (Exception ex) {
			log.error("Failed to update bugSystem with name: " + updatedBugSystem.getName(), ex);			
			throw ex;
		}
	}
}
