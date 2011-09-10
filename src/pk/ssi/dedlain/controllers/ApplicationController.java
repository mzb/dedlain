package pk.ssi.dedlain.controllers;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pk.ssi.dedlain.models.Project;
import pk.ssi.dedlain.models.User;
import pk.ssi.dedlain.models.UserORM;
import pk.ssi.dedlain.utils.BaseController;
import pk.ssi.dedlain.utils.Data;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Database.Error;

public class ApplicationController extends BaseController {

  @Override
  protected void onBeforeAction(String action, HttpServletRequest req, HttpServletResponse resp) 
  throws Exception {
    super.onBeforeAction(action, req, resp);

    boolean requiresLogin = requireLogin().contains(action);	
    boolean loggedIn = currentUser(req).getId() != null;
    if (requiresLogin && !loggedIn) {
      message("Akcja wymaga zalogowania", "info", req);
      redirect("/sessions/new", resp);
    }
  }

  @Override
  protected void onBeforeRenderView(String name, HttpServletRequest req, HttpServletResponse resp,
      Data data) throws Exception {
    super.onBeforeRenderView(name, req, resp, data);

    User currentUser = currentUser(req);
    UserORM users = new UserORM((Database) getServletContext().getAttribute("database"));
    List<Project> userProjects = users.getProjects(currentUser);
    
    req.setAttribute("user_projects", userProjects);
    req.setAttribute("current_project", currentProject(req));
    req.setAttribute("current_user", currentUser);
  }

  protected List<String> requireLogin()	 {
    return Arrays.<String>asList(
        "new", "create", "index", "update", "edit", "delete", "show");
  }

  protected User currentUser(HttpServletRequest req) {
    User user = (User) req.getSession(true).getAttribute("current_user");
    return user != null ? user : new User();
  }
  
  protected Project currentProject(HttpServletRequest req) {
    Project project = (Project) req.getSession(true).getAttribute("current_project");
    return project != null ? project : new Project();
  }
}
