package com.voyagerproject.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voyagerproject.model.BugSystem;
import com.voyagerproject.model.Project;
import com.voyagerproject.model.RuleManager;

/**
 * Domain Project Class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainProject implements java.io.Serializable {

	private static final long serialVersionUID = -3196981065072616409L;
	private int idProject;
	private String name;
	private DomainBugSystem bugSystem;
	private DomainRuleManager ruleManager;
	private Date createdOn;

	public DomainProject() {
	}
	
	public DomainProject(int idProject, String name, DomainBugSystem bugSystem, DomainRuleManager ruleManager, Date createdOn) {
		this.idProject = idProject;
		this.name = name;
		this.bugSystem = bugSystem;
		this.ruleManager = ruleManager;
		this.createdOn = createdOn;
	}
	
	public DomainProject(String name, DomainBugSystem bugSystem, DomainRuleManager ruleManager, Date createdOn) {
		this.name = name;
		this.bugSystem = bugSystem;
		this.ruleManager = ruleManager;
		this.createdOn = createdOn;
	}
	
	public DomainProject(Project project) {
		this.idProject = project.getIdProject();
		this.name = project.getName();
		this.bugSystem = new DomainBugSystem(project.getBugSystem());
		this.ruleManager = new DomainRuleManager(project.getRuleManager());
		this.createdOn = project.getCreatedOn();
	}

	public int getIdProject() {
		return this.idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DomainBugSystem getBugSystem() {
		return this.bugSystem;
	}

	public void setBugSystem(DomainBugSystem bugSystem) {
		this.bugSystem = bugSystem;
	}

	public DomainRuleManager getRuleManager() {
		return this.ruleManager;
	}

	public void setRuleManager(DomainRuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * Creates a Project from a DomainProject
	 * 
	 * @param DomainProject domainProject with values to update
	 * @param Project (optional) Project in case it already exists in the DB
	 * @return
	 */
	public static Project createProjectFromDomainProject(DomainProject domainProject, Project projectToUpdate) {
		Project project = new Project();
		
		if (projectToUpdate != null) {
			project = projectToUpdate;
		}
		
		project.setName(domainProject.getName());
		project.setBugSystem(new BugSystem(domainProject.getBugSystem().getIdBugSystem(), domainProject.getBugSystem().getName(), 
				domainProject.getBugSystem().getUrl(), null, null));
		project.setRuleManager(new RuleManager(domainProject.getRuleManager().getIdRuleManager(), domainProject.getRuleManager().getName(), 
				domainProject.getRuleManager().getUrl(), null, null));
		
		return project;
	}
	
	/**
	 * Creates a list of DomainProjects from a list of Projects
	 * 
	 * @param projectList
	 * @return
	 */
	public static Collection<DomainProject> getDomainProjectList(Collection<Project> projectList) {
		Collection<DomainProject> projects = new ArrayList<DomainProject>();
		
		// Create a DomainProject from each entry in the projectList 
		for (Project project : projectList) {
			projects.add(new DomainProject(project));			
		}
		
		return projects;
	}
}
