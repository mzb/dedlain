package pk.ssi.dedlain.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pk.ssi.dedlain.utils.Data;

public class HomeController extends ApplicationController {

  @Override
  protected void indexAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    
    Data data = new Data().
      set("title", currentUser(req).getName());
    renderView("home/index", req, resp, data);
  }
}
