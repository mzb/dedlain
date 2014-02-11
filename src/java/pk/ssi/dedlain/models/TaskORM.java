package pk.ssi.dedlain.models;

import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Database.Error;
import pk.ssi.dedlain.utils.ORM;
import pk.ssi.dedlain.utils.QueryResults;
import pk.ssi.dedlain.utils.Validation;

public class TaskORM extends ORM<Task> {

  public TaskORM(Database db) throws Error {
    super(db);
  }

  @Override
  public String getTableName() {
    return "tasks";
  }

  @Override
  public Task build(QueryResults row) throws Error {
    Task task = new Task();
    task.setId(row.getLong("id"));
    task.setProjectId(row.getLong("project_id"));
    task.setName((String) row.get("name"));
    task.setDescription((String) row.get("description"));
    task.setIterationId(row.getLong("iteration_id"));
    task.setTimeEstimation(row.getLong("time_est"));
    task.setDone((Integer) row.get("done") == 1);
    return task;
  }

  @Override
  public Task build(String attr) {
    return null;
  }

  @Override
  public void create(Task task) throws Error {
    db.update("insert into tasks (`id`, `project_id`, `name`, `description`, `iteration_id`, `time_est`, `done`) values (null, ?, ?, ?, ?, ?, ?)", 
        new Object[]{ task.getProjectId(), task.getName(), task.getDescription(), task.getIterationId(), 
        task.getTimeEstimation(), task.isDone() ? 1 : 0 });
    
  }

  @Override
  public void update(Task task) throws Error {
    db.update("update " + getTableName() + 
        " set `name` = ?, `description` = ?, `iteration_id` = ?, `time_est` = ?, `done` = ? " + 
        " where (id = ?)", 
        new Object[]{ task.getName(), task.getDescription(), task.getIterationId(), 
        task.getTimeEstimation(), task.isDone() ? 1 : 0, task.getId() });
  }
  
  @Override
  public void validate(Task task) throws Validation.Errors, Database.Error {
    Validation.Errors errors = new Validation.Errors();
    
    if (Validation.isBlank(task.getName())) {
      errors.add("name", "Nazwa nie może być pusta");
    }
    
    if (errors.any()) {
      throw errors;
    }
  }

}
