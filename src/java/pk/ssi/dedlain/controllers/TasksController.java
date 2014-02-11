package pk.ssi.dedlain.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import pk.ssi.dedlain.models.Iteration;
import pk.ssi.dedlain.models.IterationORM;
import pk.ssi.dedlain.models.Task;
import pk.ssi.dedlain.models.TaskORM;
import pk.ssi.dedlain.utils.Data;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Validation;

public class TasksController extends ApplicationController {

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
    TaskORM orm = new TaskORM((Database) getServletContext().getAttribute("database"));
    List<Task> tasks = orm.find(
        new Object[]{ "project_id = ?", currentProject(req).getId() }, "done", null);
    
    Data data = new Data().
      set("title", "Zadania").
      set("current_page", "tasks").
      set("tasks", tasks);
    renderView("tasks/index", req, resp, data);
  }

  @Override
  protected void newAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Data data = new Data().
      set("title", "Nowe zadanie").
      set("current_page", "tasks").
      set("errors", req.getAttribute("errors") == null ? 
          new Validation.Errors() : req.getAttribute("errors")).
      set("task", req.getAttribute("task") == null ? 
          new Task() : req.getAttribute("task"));
    renderView("tasks/new", req, resp, data);
  }

  @Override
  protected void createAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Task task = new Task();
    BeanUtils.populate(task, req.getParameterMap()); 
    task.setProjectId(currentProject(req).getId());

    TaskORM tasks = new TaskORM((Database) getServletContext().getAttribute("database"));
    
    try {
      tasks.validate(task);
      tasks.save(task);
      message("Zadanie " + task + " zostało utworzone", "info", req);
      redirect("/tasks", resp);
    } catch (Validation.Errors errors) {
      req.setAttribute("task", task);
      req.setAttribute("errors", errors);
      newAction(req, resp);
    }
  }
  
  @Override
  protected void editAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    TaskORM tasks = new TaskORM((Database) getServletContext().getAttribute("database"));
    Task task = (Task) (req.getAttribute("task") == null ? 
        tasks.findById(req.getParameter("id")) : req.getAttribute("task"));
    
    IterationORM iterORM = new IterationORM((Database) getServletContext().getAttribute("database"));
    List<Iteration> iterations = iterORM.find(
        new Object[]{ "project_id = ?", currentProject(req).getId() }, "start_date desc", null);
    
    Data data = new Data().
      set("title", "Edycja zadania #" + task.getId()).
      set("current_page", "tasks").
      set("errors", req.getAttribute("errors") == null ? 
          new Validation.Errors() : req.getAttribute("errors")).
      set("task", task).
      set("iterations", iterations);
    renderView("tasks/edit", req, resp, data);
  }
  
  @Override
  protected void updateAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    TaskORM tasks = new TaskORM((Database) getServletContext().getAttribute("database"));
    Task task = tasks.findById(req.getParameter("id"));

    BeanUtils.populate(task, req.getParameterMap());
    
    if (req.getParameter("update_iteration") == null) {
      if (task.getIterationId() > 0 && req.getParameter("done") == null) {
        task.setDone(false);
      }
    }

    try {
      tasks.validate(task);
      tasks.save(task);
      message("Zmiany zostały zapisane", "info", req);
      redirect("/tasks", resp);
    } catch (Validation.Errors errors) {
      req.setAttribute("task", task);
      req.setAttribute("errors", errors);
      editAction(req, resp);
    }
  }
  
  @Override
  protected void deleteAction(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    TaskORM tasks = new TaskORM((Database) getServletContext().getAttribute("database"));
    Task task = tasks.findById(req.getParameter("id"));
    tasks.delete(task);
    
    message("Zadanie " + task + " zostało usunięte", "info", req);
    redirect("/tasks", resp);
  }
}
