package pk.ssi.dedlain.controllers;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pk.ssi.dedlain.models.Project;
import pk.ssi.dedlain.models.User;
import pk.ssi.dedlain.models.UserORM;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.ORM.ModelNotFoundError;

public class SessionsController extends ApplicationController {
	private static final long serialVersionUID = 1L;

	@Override
	protected List<String> requireLogin() {
		return Arrays.asList("delete");
	}
       
	@Override
	protected void newAction(HttpServletRequest req, HttpServletResponse resp)
	    throws Exception {
	  renderView("sessions/new", req, resp);
	}
	
	@Override
	protected void createAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  Database db = (Database) getServletContext().getAttribute("database");
	  UserORM users = new UserORM(db);
	  
	  try {
      User user = users.findFirst(new Object[]{ "login = ? and password = ?", 
            req.getParameter("login"), req.getParameter("password") }, null);
      req.getSession(true).setAttribute("current_user", user);
      message("Witaj " + user.getName(), "info", req);
      redirect("/", resp);
	  } catch (ModelNotFoundError e) {
      message("Nieprawidłowy login lub hasło", "error", req);
      redirect("/sessions/new", resp);
	  }
	}
	
	@Override
	protected void deleteAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  req.getSession().removeAttribute("current_user");
    
    message("Do zobaczenia", "info", req);
    redirect("/sessions/new", resp);
	}
}
