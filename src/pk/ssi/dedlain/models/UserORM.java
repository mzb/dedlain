package pk.ssi.dedlain.models;

import pk.ssi.dedlain.utils.Database;
import pk.ssi.dedlain.utils.Database.Error;
import pk.ssi.dedlain.utils.ORM;
import pk.ssi.dedlain.utils.QueryResults;
import pk.ssi.dedlain.utils.Validation;

public class UserORM extends ORM<User> {

	public UserORM(Database db) throws Database.Error {
		super(db);	
	}
	
	@Override
	public String getTableName() {
		return "users";
	}

	@Override
	public User build(QueryResults row) throws Error {
		User user = new User();
		user.setId(row.getLong("id"));
		user.setLogin((String) row.get("login"));
		user.setPassword((String) row.get("password"));
		user.setName((String) row.get("name"));
		user.setEmail((String) row.get("email"));
		user.setAdmin(((Integer) row.get("admin")) == 1);
		return user;
	}

	@Override
	public User build(String attr) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void create(User user) throws Error {
		db.update("insert into " + getTableName() + " (`id`, `login`, `password`, `email`, `name`, `admin`) " + 
							"values (null, ?, ?, ?, ?, ?)", 
								new Object[]{ user.getLogin(), user.getPassword(), user.getEmail(), user.getName(), 
								user.isAdmin() ? 1 : 0 });
	}

	@Override
	public void update(User user) throws Error {
		db.update("update " + getTableName() + 
							" set `login` = ?, `password` = ?, `email` = ?, `name` = ?, `admin` = ? " + 
							" where (id = ?)", new Object[]{ 
								user.getLogin(), user.getPassword(), user.getEmail(), user.getName(), 
								user.isAdmin() ? 1 : 0, user.getId() });
	}

	@Override
	public void validate(User user) throws Validation.Errors, Database.Error {
		Validation.Errors errors = new Validation.Errors();
		
		if (Validation.isBlank(user.getLogin())) {
			errors.add("login", "Login nie może być pusty");
		} else {
			try {
				User userWithThisLogin = findFirst(new Object[]{ "login = ?", user.getLogin() }, null);
				if (! userWithThisLogin.getId().equals(user.getId())) {
					errors.add("login", "Login jest już zajęty");	
				}
			} catch (ModelNotFoundError ok) {}
		}
		
		if (Validation.isBlank(user.getPassword())) {
			errors.add("password", "Hasło nie może być puste");
		}
		
		if (!Validation.isBlank(user.getEmail())) {
			if (!Validation.isEmail(user.getEmail())) {
				errors.add("email", "Adres ma nieprawidłowy format");
			}
		}
		
		if (errors.any()) {
			throw errors;	
		}
	}
}
