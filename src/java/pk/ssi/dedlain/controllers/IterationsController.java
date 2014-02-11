package pk.ssi.dedlain.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;

import pk.ssi.dedlain.models.Iteration;
import pk.ssi.dedlain.models.IterationORM;
import pk.ssi.dedlain.models.Task;
import pk.ssi.dedlain.models.TaskORM;
import pk.ssi.dedlain.utils.Data;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.ORM.ModelNotFoundError;
import pk.ssi.dedlain.utils.Validation;

public class IterationsController extends ApplicationController {
  
  @Override
  protected void onBeforeAction(String action, HttpServletRequest req,
      HttpServletResponse resp) throws Exception {
    super.onBeforeAction(action, req, resp);

    if (currentProject(req).getId() == null) {
      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }
  
  @Override
  protected void indexAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    IterationORM orm = new IterationORM((Database) getServletContext().getAttribute("database"));
    List<Iteration> iterations = orm.find(
        new Object[]{ "project_id = ?", currentProject(req).getId() }, "start_date", null);
    
    Data data = new Data().
        set("title", "Iteracje").
        set("current_page", "iterations").
        set("iterations", iterations);
    renderView("iterations/index", req, resp, data);
  }

  @Override
  protected void showAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Database db = (Database) getServletContext().getAttribute("database");
    IterationORM iters = new IterationORM(db);
    
    Data data = new Data();
    Iteration iteration = null;
    
    if (req.getParameter("id") == null) {
      try {
        iteration = iters.getCurrent(currentProject(req));
        data.set("current_page", "current_iteration");
      } catch (ModelNotFoundError e) {
        redirect("/iterations", resp);
        return;
      }
    } else {
      iteration = iters.findById(req.getParameter("id"));
      data.set("current_page", "current_iteration");
    }
    
    TaskORM tasks = new TaskORM(db);
    List<Task> iterationTasks = tasks.find(
        new Object[]{ "iteration_id = ?", iteration.getId() }, "id", null);
    
    long totalTimeEst = 0;
    long numDoneTasks = 0;
    for (Task t : iterationTasks) {
      totalTimeEst += t.getTimeEstimation();
      if (t.isDone()) numDoneTasks++;
    }
    
    int progress = iterationTasks.size() > 0 ? 
        Math.round(numDoneTasks * 100 / iterationTasks.size()) : 0;
    
    data.
        set("title", "Iteracja #" + iteration.getId()).
        set("iteration", iteration).
        set("tasks", iterationTasks).
        set("totalTimeEst", totalTimeEst).
        set("numDoneTasks", numDoneTasks).
        set("progress", progress);
    renderView("iterations/show", req, resp, data);
  }
  
  @Override
  protected void newAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    Data data = new Data().
      set("title", "Nowa iteracja").
      set("current_page", "iterations").
      set("errors", req.getAttribute("errors") == null ? 
          new Validation.Errors() : req.getAttribute("errors")).
      set("iteration", req.getAttribute("iteration") == null ? 
          new Iteration() : req.getAttribute("iteration"));
    renderView("iterations/new", req, resp, data);
  }
  
  @Override
  protected void createAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    Iteration iteration = new Iteration();
    IterationORM iterations = new IterationORM((Database) getServletContext().getAttribute("database"));
    
    try {
      try {
        BeanUtils.populate(iteration, req.getParameterMap());
      } catch (ConversionException ce) {
        Validation.Errors errors = new Validation.Errors();
        String msg = "Daty muszą być w formacie YYYY-MM-DD";
        errors.add("startDate", msg);
        errors.add("endDate", msg);
        throw errors;
      }
      
      iteration.setProjectId(currentProject(req).getId());
      
      iterations.validate(iteration);
      iterations.save(iteration);
      message("Iteracja " + iteration + " została utworzona", "info", req);
      redirect("/iterations", resp);
    } catch (Validation.Errors errors) {
      req.setAttribute("iteration", iteration);
      req.setAttribute("errors", errors);
      newAction(req, resp);
    }
  }
  
  @Override
  protected void editAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    IterationORM iterations = new IterationORM((Database) getServletContext().getAttribute("database"));
    Iteration iteration = (Iteration) (req.getAttribute("iteration") == null ? 
        iterations.findById(req.getParameter("id")) : req.getAttribute("iteration"));
    
    Data data = new Data().
      set("title", "Edycja iteracji #" + iteration.getId()).
      set("current_page", "iterations").
      set("errors", req.getAttribute("errors") == null ? 
          new Validation.Errors() : req.getAttribute("errors")).
      set("iteration", iteration);
    renderView("iterations/edit", req, resp, data);
  }
  
  @Override
  protected void updateAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    IterationORM iterations = new IterationORM((Database) getServletContext().getAttribute("database"));
    Iteration iteration = iterations.findById(req.getParameter("id"));

    try {
      try {
        BeanUtils.populate(iteration, req.getParameterMap());
      } catch (ConversionException ce) {
        Validation.Errors errors = new Validation.Errors();
        String msg = "Daty muszą być w formacie YYYY-MM-DD";
        errors.add("startDate", msg);
        errors.add("endDate", msg);
        throw errors;
      }
    
      iterations.validate(iteration);
      iterations.save(iteration);
      message("Zmiany zostały zapisane", "info", req);
      redirect("/iterations", resp);
    } catch (Validation.Errors errors) {
      req.setAttribute("iteration", iteration);
      req.setAttribute("errors", errors);
      editAction(req, resp);
    }
  }
  
  @Override
  protected void deleteAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    IterationORM iterations = new IterationORM(
        (Database) getServletContext().getAttribute("database"));
    Iteration it = iterations.findById(req.getParameter("id"));
    
    iterations.delete(it);
    
    message("Iteracja " + it + " została usunięta", "info", req);
    redirect("/iterations", resp);
  }
}
