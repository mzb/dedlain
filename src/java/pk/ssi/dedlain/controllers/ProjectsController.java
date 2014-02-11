package pk.ssi.dedlain.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import pk.ssi.dedlain.models.Project;
import pk.ssi.dedlain.models.ProjectORM;
import pk.ssi.dedlain.models.UserORM;

import pk.ssi.dedlain.utils.Data;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Validation;

public class ProjectsController extends ApplicationController {

	@Override
	protected void indexAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  ProjectORM orm = new ProjectORM(
        (Database) getServletContext().getAttribute("database"));
	  
	  if ("POST".equalsIgnoreCase(req.getMethod())) {
	    Project currentProject = orm.findById(req.getParameter("current_project"));
	    if (!orm.getMembers(currentProject).contains(currentUser(req))) {
	      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
	    }
	    req.getSession(true).setAttribute("current_project", currentProject);
	    message("Aktywny projekt: " + currentProject.getName(), "info", req);
	    redirect("/iterations/show/current", resp);
	    return;
	  }
	  
		List<Project> projects = orm.find(null, "name", null);
		
	  Data data = new Data().
        set("title", "Projekty").
        set("current_page", "projects").
        set("projects", projects).
        set("orm", orm);
	  renderView("projects/index", req, resp, data);
	}
	
	@Override
	protected void newAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  Data data = new Data().
        set("title", "Nowy projekt").
        set("current_page", "projects").
        set("errors", req.getAttribute("errors") == null ? 
            new Validation.Errors() : req.getAttribute("errors")).
        set("project", req.getAttribute("project") == null ? 
            new Project() : req.getAttribute("project"));
	  renderView("projects/new", req, resp, data);
	}

	@Override
	protected void createAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Project project = new Project();
		BeanUtils.populate(project, req.getParameterMap());			

		ProjectORM orm = new ProjectORM((Database) getServletContext().getAttribute("database"));
		try {
			orm.validate(project);
			orm.save(project);
			message("Projekt " + project + " został utworzony", "info", req);
			redirect("/projects", resp);
		} catch (Validation.Errors errors) {
		  req.setAttribute("project", project);
          req.setAttribute("errors", errors);
          newAction(req, resp);
		}
	}
	
	@Override
	protected void editAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  Database db = (Database) getServletContext().getAttribute("database");
	  ProjectORM projects = new ProjectORM(db);
	  UserORM users = new UserORM(db);
	  Project project = (Project) (req.getAttribute("project") == null ? 
          projects.findById(req.getParameter("id")) : req.getAttribute("project"));
	  
	  Data data = new Data().
	      set("title", "Edycja projektu").
	      set("current_page", "projects").
	      set("errors", req.getAttribute("errors") == null ? 
	          new Validation.Errors() : req.getAttribute("errors")).
	      set("project", project).
	      set("users", users.find(null, "name", null)).
	      set("members", projects.getMembers(project));
	  
	  renderView("projects/edit", req, resp, data);
	}
	
	@Override
	protected void updateAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  ProjectORM orm = new ProjectORM((Database) getServletContext().getAttribute("database"));
	  Project project = orm.findById(req.getParameter("id"));
	  
	  BeanUtils.populate(project, req.getParameterMap());
	  
	  try {
	    orm.validate(project);
	    orm.save(project);
	    message("Zmiany zostały zapisane", "info", req);
	    redirect("/projects", resp);
	  } catch (Validation.Errors errors) {
	    req.setAttribute("project", project);
	    req.setAttribute("errors", errors);
	    editAction(req, resp);
	  }
	}
	
	@Override
	protected void deleteAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  ProjectORM orm = new ProjectORM((Database) getServletContext().getAttribute("database"));
	  Project project = orm.findById(req.getParameter("id"));
	  
	  if (project.equals(currentProject(req))) {
	    req.getSession().removeAttribute("current_project");
	  }
	  orm.delete(project);
	  
	  message("Projekt " + project + " został usunięty", "info", req);
	  redirect("/projects", resp);

	}
}
