package pk.ssi.dedlain.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pk.ssi.dedlain.utils.Data;

public class IterationsController extends ApplicationController {
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void indexAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Data data = new Data().
        set("title", "Lista iteracji").
        set("current_page", "iterations");
    renderView("iterations/index", req, resp, data);
  }

  @Override
  protected void showAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Data data = new Data().
        set("title", "Bieżąca iteracja").
        set("current_page", "current_iteration");
    renderView("iterations/show", req, resp, data);
  }
}
