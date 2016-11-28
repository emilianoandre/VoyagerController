package com.voyagerproject.domain.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voyagerproject.dao.ProjectDAO;
import com.voyagerproject.domain.controller.interfaces.IVoyagerDomainController;
import com.voyagerproject.domain.entities.DomainBugSystem;
import com.voyagerproject.domain.entities.DomainProject;
import com.voyagerproject.domain.entities.DomainRuleManager;
import com.voyagerproject.exceptions.ResultNotFoundException;
import com.voyagerproject.model.BugSystem;
import com.voyagerproject.model.BugSystemType;
import com.voyagerproject.model.Project;
import com.voyagerproject.model.RuleManager;
import com.voyagerproject.model.RuleManagerType;

/**
 * @author EAndre
 *
 */
public class ProjectController implements IVoyagerDomainController{

	// DAOs
	ProjectDAO projectDao = new ProjectDAO();
	
	private static final Log log = LogFactory.getLog(ProjectController.class);
	
	/**
	 * Gets the complete list of projects
	 * 
	 * @return Projects collection
	 * @throws Exception 
	 */
	public Collection<DomainProject> getProjects() throws Exception {
		Collection<DomainProject> bugSystems;
		
		try {
			bugSystems = DomainProject.getDomainProjectList(projectDao.getList());
		} catch (Exception ex) {
			log.error("Failed to fetch project list");
			throw ex;
		}
		
		return bugSystems;
	}
	
	/**
	 * Creates a project in the system
	 * 
	 * @param name name of the project
	 * @param bugSystemId id of the bug system related to the project
	 * @param ruleManagerId id of the rule manager related to the project
	 * @return createdProject
	 * @throws Exception 
	 */
	public DomainProject createProject(String name, DomainRuleManager domainRuleManager, DomainBugSystem domainBugSystem) throws Exception {

		// Create Project		
		RuleManager ruleManager = new RuleManager();
		ruleManager.setIdRuleManager(domainRuleManager.getIdRuleManager());
		ruleManager.setName(domainRuleManager.getName());
		ruleManager.setRuleManagerType(new RuleManagerType(domainRuleManager.getRuleManagerType().getIdType(), 
				domainRuleManager.getRuleManagerType().getName(), null));
		
		BugSystem bugSystem = new BugSystem();
		bugSystem.setIdBugSystem(domainBugSystem.getIdBugSystem());
		bugSystem.setName(domainBugSystem.getName());
		bugSystem.setBugSystemType(new BugSystemType(domainBugSystem.getBugSystemType().getIdType(), 
				domainBugSystem.getBugSystemType().getName(), null));
		
		Project project = new Project(name, bugSystem, ruleManager, null);
		
		try {
			Integer projectId = projectDao.persist(project);
			project.setIdProject(projectId);
		} catch (Exception ex) {
			log.error("Failed to create project with Name: " + name, ex);			
			throw ex;
		}	
		
		return new DomainProject(project);
	}
	
	/**
	 * Deletes a project by it's id
	 * 
	 * @param projectId project id to delete
	 * @throws Exception 
	 */
	public void deleteProject(Integer projectId) throws Exception {
		try {
			Project project = projectDao.findById(projectId);
			projectDao.remove(project);
		} catch (ResultNotFoundException rnfe) {
			log.info("No project found with id: " + projectId);
			throw rnfe;
		}  catch (Exception ex) {
			log.error("Failed to delete project with id: " + projectId, ex);			
			throw ex;
		}
	}
	
	/**
	 * Updates a project
	 * 
	 * @param DomainProject project
	 * @return DomainProject updatedProject
	 * @throws Exception 
	 */
	public void updateProject(DomainProject updatedProject) throws Exception {
		try {
			Project project;
			try {
				// First we look for the project by the Id
				project = projectDao.findById(updatedProject.getIdProject());
			} catch (ResultNotFoundException rnfe) {
				log.info("No project found with name: " + updatedProject.getName());
				throw rnfe;
			}
			
			// Update the entry values and merge
			project =  DomainProject.createProjectFromDomainProject(updatedProject, project);
			projectDao.merge(project);
		} catch (Exception ex) {
			log.error("Failed to update project with name: " + updatedProject.getName(), ex);			
			throw ex;
		}
	}
}
