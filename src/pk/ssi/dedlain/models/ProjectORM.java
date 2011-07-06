package pk.ssi.dedlain.models;

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
