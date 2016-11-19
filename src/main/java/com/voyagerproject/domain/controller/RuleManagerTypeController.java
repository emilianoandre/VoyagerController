package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.RuleManagerTypeDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainType;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.RuleManagerType;
import com.voyagerproject.util.ModelUtils;

/**
 * Controller that handles all the requests for RuleManager Type
 * @author EAndre
 *
 */
public class RuleManagerTypeController implements IVoyagerDomainController {
	
	// DAOs
	RuleManagerTypeDAO ruleManagerTypeDao = new RuleManagerTypeDAO();
	
	private static final Log log = LogFactory.getLog(RuleManagerTypeController.class);
	
	/**
	 * Gets the complete list of ruleManager types
	 * 
	 * @return RuleManagerType collection
	 * @throws Exception 
	 */
	public Collection<DomainType> getRuleManagerTypes() throws Exception {
		Collection<DomainType> ruleManagerTypes;
		
		try {
			// Convert the list to generic type and get the Domain Type List
			ruleManagerTypes = DomainType.getDomainTypeList(ModelUtils.getGenericTypeList(ruleManagerTypeDao.getList()));
		} catch (Exception ex) {
			log.error("Failed to fetch ruleManager type list");
			throw ex;
		}
		
		return ruleManagerTypes;
	}
	
	/**
	 * Creates a ruleManager type in the system
	 * 
	 * @param name the name of the ruleManager type
	 * @return createdRuleManagerType
	 * @throws Exception 
	 */
	public DomainType createRuleManagerType(String name) throws Exception {

		// Create RuleManager
		RuleManagerType ruleManagerType = new RuleManagerType();
		ruleManagerType.setName(name);
		try {
			Integer ruleManagerTypeId = ruleManagerTypeDao.persist(ruleManagerType);
			ruleManagerType.setIdRuleManagerType(ruleManagerTypeId);
		} catch (Exception ex) {
			log.error("Failed to create ruleManager type with name: " + name, ex);
			throw ex;
		}	
		
		// Create object to return
		DomainType domainRuleManagerType = new DomainType(ruleManagerType);
		
		return domainRuleManagerType;
	}
	
	/**
	 * Deletes a ruleManager type by it's id
	 * 
	 * @param ruleManagerTypeId
	 * @throws Exception 
	 */
	public void deleteRuleManagerType(Integer ruleManagerTypeId) throws Exception {		
		try {
			RuleManagerType ruleManagerType = ruleManagerTypeDao.findById(ruleManagerTypeId);
			ruleManagerTypeDao.remove(ruleManagerType);
		} catch (ResultNotFoundException rnfe) {
			log.info("No ruleManager type found with id: " + ruleManagerTypeId);
			throw rnfe;
		} catch (Exception ex) {
			log.error("Failed to delete ruleManager type with ID: " + ruleManagerTypeId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a ruleManager type
	 * 
	 * @param ruleManagerType to update
	 * @return updatedRuleManagerType
	 * @throws Exception 
	 */
	public void updateRuleManagerType(DomainType updatedRuleManagerType) throws Exception {
		try {
			RuleManagerType ruleManagerType;
			try {
				// First we look for the ruleManager type by the ruleManagerName
				ruleManagerType = ruleManagerTypeDao.findById(updatedRuleManagerType.getIdType());
			} catch (ResultNotFoundException rnfe) {
				log.info("No ruleManager type found with id: " + updatedRuleManagerType.getIdType());
				throw rnfe;
			}
			
			// Update the entry values and merge
			ruleManagerType.setName(updatedRuleManagerType.getName());
			ruleManagerTypeDao.merge(ruleManagerType);
		} catch (Exception ex) {
			log.error("Failed to update ruleManager with id: " + updatedRuleManagerType.getIdType(), ex);			
			throw ex;
		}
	}

}