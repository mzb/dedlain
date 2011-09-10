package pk.ssi.dedlain.models;

import java.text.SimpleDateFormat;
import java.sql.Date;

import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.ORM;
import pk.ssi.dedlain.utils.QueryResults;
import pk.ssi.dedlain.utils.Validation;
import pk.ssi.dedlain.utils.Database.Error;
import pk.ssi.dedlain.utils.Validation.Errors;

public class IterationORM extends ORM<Iteration> {

  public IterationORM(Database db) throws Error {
    super(db);
  }

  @Override
  public String getTableName() {
    return "iterations";
  }

  @Override
  public Iteration build(QueryResults row) throws Error {
    Iteration iteration = new Iteration();
    iteration.setId(row.getLong("id"));
    iteration.setProjectId(row.getLong("project_id"));
    iteration.setName((String) row.get("name"));
    iteration.setDescription((String) row.get("description"));
    iteration.setStartDate(new Date(row.getLong("start_date")));
    iteration.setEndDate(new Date(row.getLong("end_date")));
    return iteration;
  }

  @Override
  public Iteration build(String attr) {
    return null;
  }

  @Override
  public void create(Iteration it) throws Error {
    db.update("insert into iterations (`id`, `project_id`, `name`, `description`, `start_date`, `end_date`) values (null, ?, ?, ?, ?, ?)", 
        new Object[]{ it.getProjectId(), it.getName(), it.getDescription(), 
        it.getStartDate().getTime(), it.getEndDate().getTime() });
  }

  @Override
  public void update(Iteration it) throws Error {
    db.update("update " + getTableName() + 
        " set `name` = ?, `description` = ?, `start_date` = ?, `end_date` = ? " + 
        " where (id = ?)", 
        new Object[]{ it.getName(), it.getDescription(), 
        it.getStartDate().getTime(), it.getEndDate().getTime(), it.getId() });
  }

  @Override
  public void validate(Iteration iteration) throws Errors, Error {
    Validation.Errors errors = new Validation.Errors();
    
    if (Validation.isBlank(iteration.getName())) {
      errors.add("name", "Nazwa nie może być pusta");
    }
    
    if (iteration.getEndDate().before(iteration.getStartDate())) {
      errors.add("endDate", "Data zakończenia nie może być wcześniejsza niż data rozpoczęcia");
    }
    
    if (errors.any()) {
      throw errors; 
    }
  }
  
  public Iteration getCurrent(Project project) throws Exception {
    return findFirst(
        new Object[]{ "project_id = ? and strftime('%s','now') * 1000 >= start_date and strftime('%s','now') * 1000 <= end_date", project.getId() }, 
        "start_date desc");
  }
}
