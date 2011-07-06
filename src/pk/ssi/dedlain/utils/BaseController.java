package pk.ssi.dedlain.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController extends HttpServlet {

	private static interface Action {
	  public void run() throws Exception;
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		String action = "index";
		if (req.getPathInfo() != null) {
			String[] pathInfo = req.getPathInfo().split("/");
			if (pathInfo.length > 0) {
				action = pathInfo[1];
			}
		}

		// TODO: Dodac mozliosc okreslania wewnetrznych aliasow dla akcji, np.:
		//	w kontrolerze Sessions
		// 		/login => new
		//		/logout => delete
		//	przy zalozeniu, ze /sessions/*, /login, /logout => Sessions

		try {	
			dispatchAction(action, req, resp);
		} catch (Exception e) {
			if (e instanceof ORM.ModelNotFoundError) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);					
			} else {
				throw new ServletException(e);
			}
		}
	}

	protected void dispatchAction(String actionName, final HttpServletRequest req, final HttpServletResponse resp) 
					throws Exception {
	  Action action = null;
	  
		if ("GET".equalsIgnoreCase(req.getMethod())) {
			if ("index".equals(actionName)) {
				action = new Action() { public void run() throws Exception { 
				  indexAction(req, resp); 
				}};
			}
			if ("show".equals(actionName)) {
			  action = new Action() { public void run() throws Exception { 
          showAction(req, resp); 
        }};
			}
			if ("edit".equals(actionName)) {
			  action = new Action() { public void run() throws Exception { 
          editAction(req, resp); 
        }};
			}
			if ("new".equals(actionName)) {
			  action = new Action() { public void run() throws Exception { 
          newAction(req, resp); 
        }};
			}
		}
		
		if ("POST".equalsIgnoreCase(req.getMethod())) {
			if ("create".equals(actionName)) {
			  action = new Action() { public void run() throws Exception { 
          createAction(req, resp); 
        }};
			}
			if ("update".equals(actionName)) {
			  action = new Action() { public void run() throws Exception { 
          updateAction(req, resp); 
        }};
			}
			if ("delete".equals(actionName)) {
			  action = new Action() { public void run() throws Exception { 
			    deleteAction(req, resp); 
			  }};
			}
		}

		if (action != null) {
		  onBeforeAction(actionName, req, resp);
		  action.run();
		  onAfterAction(actionName, req, resp);
		} else {
		  resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	protected void renderText(String body, HttpServletResponse resp) throws IOException {
		if (resp.getContentType() == null) {
			resp.setContentType("text/html");
		}
		resp.setContentType(resp.getContentType() + "; charset=utf-8");
		PrintWriter writer = null;
		
		try {
			writer = resp.getWriter();
			writer.println(body);	
		} finally {
			writer.close();	
		}
	}

	protected void renderView(String name, HttpServletRequest req, HttpServletResponse resp, 
	    Data data) throws ServletException, IOException {
		if (resp.isCommitted()) {
			return;
		}
		
	 	onBeforeRenderView(name, req, resp, data); 

	  if (data != null) {
	    for (Map.Entry<String, Object> e : data.getMap().entrySet()) {
	      req.setAttribute(e.getKey(), e.getValue());
	    }
	  }
	 
		req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
	}
	
	protected void renderView(String name, HttpServletRequest req, HttpServletResponse resp) 
	    throws ServletException, IOException {
	  renderView(name, req, resp, null);
	}

	protected void onBeforeRenderView(String name, HttpServletRequest req, HttpServletResponse resp, 
					Data data) {
	  req.setAttribute("message", (RequestMessage) req.getSession().getAttribute("message"));
	  req.setAttribute("controller", req.getServletPath());
	  req.setAttribute("current_page", "");
	}
	
	protected void onBeforeAction(String action, HttpServletRequest req, HttpServletResponse resp) 
					throws Exception {
	  logRequest(req);
	}

	private void logRequest(HttpServletRequest req) {
		getServletContext().log(req.getMethod() + " " + req.getServletPath() + 
						(req.getPathInfo() != null ? req.getPathInfo() : "") + 
				" params=" + req.getParameterMap());
	}
	
	protected void onAfterAction(String action, HttpServletRequest req, HttpServletResponse resp) 
					throws Exception {
	  if (HttpServletResponse.SC_MOVED_TEMPORARILY == resp.getStatus()) {
	    return;
	  }
	  req.getSession().removeAttribute("message");
	}
	
	protected void redirect(String url, HttpServletResponse resp) throws IOException {
	  resp.sendRedirect(getServletContext().getContextPath() + url);
	}
	
	protected void message(String body, String type, HttpServletRequest req) {
	  req.getSession().setAttribute("message", new RequestMessage(body, type));
	}

	protected void indexAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}

	protected void showAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}

	protected void newAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}

	protected void createAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}
	
	protected void editAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}

	protected void updateAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}

	protected void deleteAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		process(req, resp);				
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		process(req, resp);
	}
}
