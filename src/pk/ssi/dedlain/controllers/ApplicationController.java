package pk.ssi.dedlain.controllers;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pk.ssi.dedlain.models.User;
import pk.ssi.dedlain.utils.BaseController;
import pk.ssi.dedlain.utils.Data;

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
					Data data) {
		super.onBeforeRenderView(name, req, resp, data);
		
		req.setAttribute("current_user", currentUser(req));
	}

	protected List<String> requireLogin()	 {
		return Arrays.<String>asList(
						"new", "create", "index", "update", "edit", "delete");
	}

	protected User currentUser(HttpServletRequest req) {
		User user = (User) req.getSession(true).getAttribute("current_user");
		return user != null ? user : new User();
	}
}
