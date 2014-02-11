package pk.ssi.dedlain.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import pk.ssi.dedlain.models.User;
import pk.ssi.dedlain.models.UserORM;
import pk.ssi.dedlain.utils.Data;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Validation;


public class UsersController extends ApplicationController {

	@Override
	protected void onBeforeAction(String action, HttpServletRequest req, HttpServletResponse resp) 
					throws Exception {
		super.onBeforeAction(action, req, resp);
		
		if (! currentUser(req).isAdmin()) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	
	@Override
	protected void indexAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserORM orm = new UserORM((Database) getServletContext().getAttribute("database"));
		List<User> users = orm.find(null, "login", null);

		Data data = new Data().
						set("title", "Użytkownicy").
						set("current_page", "users").
						set("users", users);
		renderView("users/index", req, resp, data);
	}

	@Override
	protected void newAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Data data = new Data().
						set("title", "Nowy użytkownik").
						set("current_page", "users").
						set("errors", req.getAttribute("errors") == null ? 
								new Validation.Errors() : req.getAttribute("errors")).
						set("user", req.getAttribute("user") == null ? new User() : req.getAttribute("user"));
		renderView("users/new", req, resp, data);
	}

	@Override
	protected void createAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		User user = new User();
		BeanUtils.populate(user, req.getParameterMap());

		UserORM orm = new UserORM((Database) getServletContext().getAttribute("database"));

		try {
			orm.validate(user);
			orm.save(user);
			message("Konto użytkownika " + user + " zostało utworzone", "info", req);
			redirect("/users", resp);
		} catch (Validation.Errors errors) {
			req.setAttribute("user", user);
			req.setAttribute("errors", errors);
			newAction(req, resp);
		}
	}

	@Override
	protected void editAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserORM orm = new UserORM((Database) getServletContext().getAttribute("database"));
		
		Data data = new Data().
						set("title", "Edycja użytkownika").
						set("current_page", "users").
						set("errors", req.getAttribute("errors") == null ? 
								new Validation.Errors() : req.getAttribute("errors")).
						set("user", req.getAttribute("user") == null ? 
								orm.findById(req.getParameter("id")) : req.getAttribute("user"));
		
		renderView("users/edit", req, resp, data);
	}

	@Override
	protected void updateAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserORM orm = new UserORM((Database) getServletContext().getAttribute("database"));
		User user = orm.findById(req.getParameter("id"));

		BeanUtils.populate(user, req.getParameterMap());
		if (req.getParameter("admin") == null) {
			user.setAdmin(false);
		}

		try {
			orm.validate(user);
			orm.save(user);
			message("Zmiany zostały zapisane", "info", req);
			redirect("/users", resp);
		} catch (Validation.Errors errors) {
			req.setAttribute("user", user);
			req.setAttribute("errors", errors);
			editAction(req, resp);
		}
	}

	@Override
	protected void deleteAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserORM orm = new UserORM((Database) getServletContext().getAttribute("database"));
		User user = orm.findById(req.getParameter("id"));

		orm.delete(user);

		message("Konto użytkownika " + user + " zostało usunięte", "info", req);
		redirect("/users", resp);
	}
}