package com.voyagerproject.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voyagerproject.model.RuleManager;
import com.voyagerproject.model.RuleManagerType;

/**
 * RuleManager Domain entity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainRuleManager implements java.io.Serializable {

	private static final long serialVersionUID = -6787730676406890862L;
	private int idRuleManager;
	private String name;
	private String url;
	private DomainType ruleManagerType;
	private Date createdOn;

	public DomainRuleManager() {
	}

	public DomainRuleManager(int idRuleManager, String name, String url, DomainType ruleManagerType, Date createdOn) {
		this.idRuleManager = idRuleManager;
		this.name = name;
		this.url = url;
		this.ruleManagerType = ruleManagerType;
		this.createdOn = createdOn;
	}
	
	public DomainRuleManager(String name, String url, DomainType ruleManagerType, Date createdOn) {
		this.name = name;
		this.url = url;
		this.ruleManagerType = ruleManagerType;
		this.createdOn = createdOn;
	}
	
	public DomainRuleManager(RuleManager ruleManager) {
		this.idRuleManager = ruleManager.getIdRuleManager();
		this.name = ruleManager.getName();
		this.url = ruleManager.getUrl();
		this.ruleManagerType = new DomainType(ruleManager.getRuleManagerType().getIdRuleManagerType(), ruleManager.getRuleManagerType().getName());
		this.createdOn = ruleManager.getCreatedOn();
	}

	public int getIdRuleManager() {
		return this.idRuleManager;
	}

	public void setIdRuleManager(int idRuleManager) {
		this.idRuleManager = idRuleManager;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DomainType getRuleManagerType() {
		return this.ruleManagerType;
	}

	public void setRuleManagerType(DomainType ruleManagerType) {
		this.ruleManagerType = ruleManagerType;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * Creates a RuleManager from a DomainRuleManager
	 * 
	 * @param DomainRuleManager domainRuleManager with values to update
	 * @param RuleManager (optional) ruleManagerToUpdate in case it already exists in the DB
	 * @return
	 */
	public static RuleManager createRuleManagerFromDomainRuleManager(DomainRuleManager domainRuleManager, RuleManager ruleManagerToUpdate) {
		RuleManager ruleManager = new RuleManager();
		
		if (ruleManagerToUpdate != null) {
			ruleManager = ruleManagerToUpdate;
		}
		
		ruleManager.setName(domainRuleManager.getName());
		ruleManager.setUrl(domainRuleManager.getUrl());
		ruleManager.setRuleManagerType(new RuleManagerType(domainRuleManager.getRuleManagerType().getIdType(), "", null));
		
		return ruleManager;
	}
	
	/**
	 * Creates a list of DomainRuleManagers from a list of RuleManagers
	 * 
	 * @param ruleManagerList
	 * @return
	 */
	public static Collection<DomainRuleManager> getDomainRuleManagerList(Collection<RuleManager> ruleManagerList) {
		Collection<DomainRuleManager> ruleManagers = new ArrayList<DomainRuleManager>();
		
		// Create a DomainRuleManager from each entry in the ruleManagerList 
		for (RuleManager ruleManager : ruleManagerList) {
			ruleManagers.add(new DomainRuleManager(ruleManager));			
		}
		
		return ruleManagers;
	}
}
