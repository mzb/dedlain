package pk.ssi.dedlain.models;

import pk.ssi.dedlain.utils.BaseModel;
import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.ORM;
import pk.ssi.dedlain.utils.QueryResults;
import pk.ssi.dedlain.utils.Database.Error;

public class ProjectAssignmentORM extends ORM<ProjectAssignment> {

  public ProjectAssignmentORM(Database db) throws Error {
    super(db);
  }

  @Override
  public String getTableName() {
    return "project_assignments";
  }

  @Override
  public ProjectAssignment build(QueryResults row) throws Error {
    ProjectAssignment assign = new ProjectAssignment();
    assign.setProjectId(row.getLong("project_id"));
    assign.setUserId(row.getLong("user_id"));
    return assign;
  }

  @Override
  public ProjectAssignment build(String attr) {
    return null;
  }

  @Override
  public void create(ProjectAssignment model) throws Error {
    db.update("insert or ignore into project_assignments (`project_id`, `user_id`) values (?, ?)", 
        new Object[]{ model.getProjectId(), model.getUserId() });
  }

  @Override
  public void update(ProjectAssignment model) throws Error {

  }

}
