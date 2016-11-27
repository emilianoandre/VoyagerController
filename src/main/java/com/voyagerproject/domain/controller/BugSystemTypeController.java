package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.BugSystemTypeDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainType;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.BugSystemType;
import com.voyagerproject.util.ModelUtils;

/**
 * Controller that handles all the requests for BugSystem Type
 * @author EAndre
 *
 */
public class BugSystemTypeController implements IVoyagerDomainController {
	
	// DAOs
	BugSystemTypeDAO bugSystemTypeDao = new BugSystemTypeDAO();
	
	private static final Log log = LogFactory.getLog(BugSystemTypeController.class);
	
	/**
	 * Gets the complete list of bugSystem types
	 * 
	 * @return BugSystemType collection
	 * @throws Exception 
	 */
	public Collection<DomainType> getBugSystemTypes() throws Exception {
		Collection<DomainType> bugSystemTypes;
		
		try {
			// Convert the list to generic type and get the Domain Type List
			bugSystemTypes = DomainType.getDomainTypeList(ModelUtils.getGenericTypeList(bugSystemTypeDao.getList()));
		} catch (Exception ex) {
			log.error("Failed to fetch bugSystem type list");
			throw ex;
		}
		
		return bugSystemTypes;
	}
	
	/**
	 * Creates a bugSystem type in the system
	 * 
	 * @param name the name of the bugSystem type
	 * @return createdBugSystemType
	 * @throws Exception 
	 */
	public DomainType createBugSystemType(String name) throws Exception {

		// Create BugSystem
		BugSystemType bugSystemType = new BugSystemType();
		bugSystemType.setName(name);
		try {
			Integer bugSystemTypeId = bugSystemTypeDao.persist(bugSystemType);
			bugSystemType.setIdBugSystemType(bugSystemTypeId);
		} catch (Exception ex) {
			log.error("Failed to create bugSystem type with name: " + name, ex);
			throw ex;
		}	
		
		// Create object to return
		DomainType domainBugSystemType = new DomainType(bugSystemType);
		
		return domainBugSystemType;
	}
	
	/**
	 * Deletes a bugSystem type by it's id
	 * 
	 * @param bugSystemTypeId if of the bug system type to delete
	 * @throws Exception 
	 */
	public void deleteBugSystemType(Integer bugSystemTypeId) throws Exception {		
		try {
			BugSystemType bugSystemType = bugSystemTypeDao.findById(bugSystemTypeId);
			bugSystemTypeDao.remove(bugSystemType);
		} catch (ResultNotFoundException rnfe) {
			log.info("No bugSystem type found with id: " + bugSystemTypeId);
			throw rnfe;
		} catch (Exception ex) {
			log.error("Failed to delete bugSystem type with ID: " + bugSystemTypeId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a bugSystem type
	 * 
	 * @param bugSystemType to update
	 * @return updatedBugSystemType
	 * @throws Exception 
	 */
	public void updateBugSystemType(DomainType updatedBugSystemType) throws Exception {
		try {
			BugSystemType bugSystemType;
			try {
				// First we look for the bugSystem type by the bugSystemName
				bugSystemType = bugSystemTypeDao.findById(updatedBugSystemType.getIdType());
			} catch (ResultNotFoundException rnfe) {
				log.info("No bugSystem type found with id: " + updatedBugSystemType.getIdType());
				throw rnfe;
			}
			
			// Update the entry values and merge
			bugSystemType.setName(updatedBugSystemType.getName());
			bugSystemTypeDao.merge(bugSystemType);
		} catch (Exception ex) {
			log.error("Failed to update bugSystem with id: " + updatedBugSystemType.getIdType(), ex);			
			throw ex;
		}
	}

}