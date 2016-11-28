package com.voyagerproject.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voyagerproject.model.BugSystem;
import com.voyagerproject.model.BugSystemType;

/**
 * BugSystem Domain entity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainBugSystem implements java.io.Serializable {

	private static final long serialVersionUID = -6787730676406890862L;
	private int idBugSystem;
	private String name;
	private String url;
	private DomainType bugSystemType;
	private Date createdOn;

	public DomainBugSystem() {
	}

	public DomainBugSystem(int idBugSystem, String name, String url, DomainType bugSystemType, Date createdOn) {
		this.idBugSystem = idBugSystem;
		this.name = name;
		this.url = url;
		this.bugSystemType = bugSystemType;
		this.createdOn = createdOn;
	}
	
	public DomainBugSystem(String name, String url, DomainType bugSystemType, Date createdOn) {
		this.name = name;
		this.url = url;
		this.bugSystemType = bugSystemType;
		this.createdOn = createdOn;
	}
	
	public DomainBugSystem(BugSystem bugSystem) {
		this.idBugSystem = bugSystem.getIdBugSystem();
		this.name = bugSystem.getName();
		this.url = bugSystem.getUrl();
		this.bugSystemType = new DomainType(bugSystem.getBugSystemType().getIdBugSystemType(), bugSystem.getBugSystemType().getName());
		this.createdOn = bugSystem.getCreatedOn();
	}

	public int getIdBugSystem() {
		return this.idBugSystem;
	}

	public void setIdBugSystem(int idBugSystem) {
		this.idBugSystem = idBugSystem;
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

	public DomainType getBugSystemType() {
		return this.bugSystemType;
	}

	public void setBugSystemType(DomainType bugSystemType) {
		this.bugSystemType = bugSystemType;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * Creates a BugSystem from a DomainBugSystem
	 * 
	 * @param DomainBugSystem domainBugSystem with values to update
	 * @param BugSystem (optional) bugSystemToUpdate in case it already exists in the DB
	 * @return
	 */
	public static BugSystem createBugSystemFromDomainBugSystem(DomainBugSystem domainBugSystem, BugSystem bugSystemToUpdate) {
		BugSystem bugSystem = new BugSystem();
		
		if (bugSystemToUpdate != null) {
			bugSystem = bugSystemToUpdate;
		}
		
		bugSystem.setName(domainBugSystem.getName());
		bugSystem.setUrl(domainBugSystem.getUrl());
		bugSystem.setBugSystemType(new BugSystemType(domainBugSystem.getBugSystemType().getIdType(), "", null));
		
		return bugSystem;
	}
	
	/**
	 * Creates a list of DomainBugSystems from a list of BugSystems
	 * 
	 * @param bugSystemList
	 * @return
	 */
	public static Collection<DomainBugSystem> getDomainBugSystemList(Collection<BugSystem> bugSystemList) {
		Collection<DomainBugSystem> bugSystems = new ArrayList<DomainBugSystem>();
		
		// Create a DomainBugSystem from each entry in the bugSystemList 
		for (BugSystem bugSystem : bugSystemList) {
			bugSystems.add(new DomainBugSystem(bugSystem));			
		}
		
		return bugSystems;
	}
}
