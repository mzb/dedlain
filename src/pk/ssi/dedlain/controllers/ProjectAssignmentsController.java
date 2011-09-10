package pk.ssi.dedlain.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pk.ssi.dedlain.models.Project;
import pk.ssi.dedlain.models.ProjectAssignment;
import pk.ssi.dedlain.models.ProjectAssignmentORM;
import pk.ssi.dedlain.models.ProjectORM;
import pk.ssi.dedlain.models.User;
import pk.ssi.dedlain.utils.Database;

public class ProjectAssignmentsController extends ApplicationController {

  @Override
  protected void updateAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    Database db = (Database) getServletContext().getAttribute("database");
    ProjectORM projects = new ProjectORM(db);
    ProjectAssignmentORM assignments = new ProjectAssignmentORM(db);
    
    Project project = projects.findById(req.getParameter("project_id"));
    List<String> userIds = Arrays.asList(req.getParameterValues("user_id[]") != null ?
        req.getParameterValues("user_id[]") : new String[]{});
    
    List<User> members = projects.getMembers(project);
    for (User member : members) {
      if (!userIds.contains(member.getId())) {
        assignments.delete(new Object[]{ "project_id = ?", project.getId() });
      }
    }
    
    for (String id : userIds) {
      ProjectAssignment a = new ProjectAssignment();
      a.setProjectId(project.getId());
      a.setUserId(Long.parseLong(id));
      assignments.create(a);
    }
    
    message("Lista członków zespołu została uaktualniona", "info", req);
    redirect("/projects/edit/?id=" + project.getId(), resp);
  }
}
