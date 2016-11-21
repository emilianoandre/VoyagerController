package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.RuleManagerDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainRuleManager;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.RuleManager;
import com.voyagerproject.model.RuleManagerType;

/**
 * @author EAndre
 *
 */
public class RuleManagerController implements IVoyagerDomainController{

	// DAOs
	RuleManagerDAO ruleManagerDao = new RuleManagerDAO();
	
	private static final Log log = LogFactory.getLog(RuleManagerController.class);
	
	/**
	 * Gets the complete list of ruleManagers
	 * 
	 * @return RuleManagers collection
	 * @throws Exception 
	 */
	public Collection<DomainRuleManager> getRuleManagers() throws Exception {
		Collection<DomainRuleManager> ruleManagerTypes;
		
		try {
			ruleManagerTypes = DomainRuleManager.getDomainRuleManagerList(ruleManagerDao.getList());
		} catch (Exception ex) {
			log.error("Failed to fetch ruleManager list");
			throw ex;
		}
		
		return ruleManagerTypes;
	}
	
	/**
	 * Creates a ruleManager in the system
	 * 
	 * @param name
	 * @param name
	 * @param email
	 * @param password
	 * @param ruleManagerTypeId
	 * @param createdBy
	 * @return createdRuleManager
	 * @throws Exception 
	 */
	public DomainRuleManager createRuleManager(String name, String url, int ruleManagerTypeId) throws Exception {

		// Create RuleManager
		RuleManagerType ruleManagerType = new RuleManagerType();
		ruleManagerType.setIdRuleManagerType(ruleManagerTypeId);
		RuleManager ruleManager = new RuleManager();
		try {
			Integer ruleManagerId = ruleManagerDao.persist(ruleManager);
			ruleManager.setIdRuleManager(ruleManagerId);
		} catch (Exception ex) {
			log.error("Failed to create ruleManager with Name: " + name, ex);			
			throw ex;
		}	
		
		return new DomainRuleManager(ruleManager);
	}
	
	/**
	 * Deletes a ruleManager by it's name
	 * 
	 * @param ruleManagerId
	 * @throws Exception 
	 */
	public void deleteRuleManager(Integer ruleManagerId) throws Exception {
		try {
			RuleManager ruleManager = ruleManagerDao.findById(ruleManagerId);
			ruleManagerDao.remove(ruleManager);
		} catch (ResultNotFoundException rnfe) {
			log.info("No ruleManager found with id: " + ruleManagerId);
			throw rnfe;
		}  catch (Exception ex) {
			log.error("Failed to delete ruleManager with id: " + ruleManagerId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a ruleManager
	 * 
	 * @param DomainRuleManager ruleManager
	 * @return DomainRuleManager updatedRuleManager
	 * @throws Exception 
	 */
	public void updateRuleManager(DomainRuleManager updatedRuleManager) throws Exception {
		try {
			RuleManager ruleManager;
			try {
				// First we look for the ruleManager by the Id
				ruleManager = ruleManagerDao.findById(updatedRuleManager.getIdRuleManager());
			} catch (ResultNotFoundException rnfe) {
				log.info("No ruleManager found with name: " + updatedRuleManager.getName());
				throw rnfe;
			}
			
			// Update the entry values and merge
			ruleManager =  DomainRuleManager.createRuleManagerFromDomainRuleManager(updatedRuleManager, ruleManager);
			ruleManagerDao.merge(ruleManager);
		} catch (Exception ex) {
			log.error("Failed to update ruleManager with name: " + updatedRuleManager.getName(), ex);			
			throw ex;
		}
	}
}