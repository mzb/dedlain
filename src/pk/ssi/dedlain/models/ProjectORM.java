package pk.ssi.dedlain.models;

import java.util.ArrayList;
import java.util.List;

import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Database.Error;
import pk.ssi.dedlain.utils.Validation;
import pk.ssi.dedlain.utils.Validation.Errors;
import pk.ssi.dedlain.utils.ORM;
import pk.ssi.dedlain.utils.QueryResults;

public class ProjectORM extends ORM<Project> {

	public ProjectORM(Database db) throws Database.Error {
		super(db);
	}
	
	@Override
	public String getTableName() {
		return "projects";
	}

	@Override
	public Project build(QueryResults row) throws Error {
		Project project = new Project();
		project.setId(row.getLong("id"));
		project.setName((String) row.get("name"));
		project.setDescription((String) row.get("description"));
		return project;
	}

	@Override
	public Project build(String attr) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void create(Project project) throws Error {
		db.update("insert into projects (`id`, `name`, `description`) values (null, ?, ?)", 
						new Object[]{ project.getName(), project.getDescription() });
	}

	@Override
	public void update(Project project) throws Error {
	  db.update("update " + getTableName() + 
	            " set `name` = ?, `description` = ? " + 
	            " where (id = ?)", 
	            new Object[]{ project.getName(), project.getDescription(), project.getId() });
	}
	
	public List<User> getMembers(Project project) throws Error {
	  QueryResults results = db.query(
	      "select users.* from users join project_assignments pa on users.id = pa.user_id where (project_id = ?)", 
	      new Object[]{ project.getId() });
	  
	  List<User> members = new ArrayList<User>();
	  UserORM users = new UserORM(db);
      while (results.next()) {
        members.add(users.build(results));
      }
      results.close();
      
	  return members;
	}
	
	public Long countMembers(Project project) throws Error {
	  ProjectAssignmentORM assignments = new ProjectAssignmentORM(db);
	  return assignments.count(new Object[]{ "project_id = ?", project.getId() });
	}
	
	@Override
	public void validate(Project model) throws Validation.Errors, Database.Error {
	  Validation.Errors errors = new Validation.Errors();
	  
	  if (Validation.isBlank(model.getName())) {
	    errors.add("name", "Nazwa nie może być pusta");
	  }
	  
	  if (errors.any()) {
	    throw errors;
	  }
	}
}
