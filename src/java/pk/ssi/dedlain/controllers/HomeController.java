package pk.ssi.dedlain.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import pk.ssi.dedlain.models.User;
import pk.ssi.dedlain.models.UserORM;
import pk.ssi.dedlain.utils.Data;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.ORM;
import pk.ssi.dedlain.utils.Validation;

public class HomeController extends ApplicationController {

  @Override
  protected void indexAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    
    if ("POST".equalsIgnoreCase(req.getMethod())) {
      UserORM users = new UserORM((Database) getServletContext().getAttribute("database"));
      User currentUser = currentUser(req);
      
      BeanUtils.populate(currentUser, req.getParameterMap());
      
      try {
        users.validate(currentUser);
        users.save(currentUser);
        message("Zmiany zostały zapisane", "info", req);
        redirect("/", resp);
      } catch (Validation.Errors errors) {
        req.setAttribute("errors", errors);
      }
    }
    
    Data data = new Data().
      set("title", currentUser(req).getName()).
      set("errors", req.getAttribute("errors") == null ? 
          new Validation.Errors() : req.getAttribute("errors"));
    renderView("home/index", req, resp, data);
  }
  
  @Override
  protected void process(HttpServletRequest req, HttpServletResponse resp)
  throws ServletException, IOException {
    String action = "index";

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
}
