package pk.ssi.dedlain.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class Database {

	protected static final Logger log = Logger.getLogger("DB");
  
	protected Properties config;
  protected Connection connection;

  public Database(Properties config) throws Database.Error {
    try {
			this.config = config;
			
      Class.forName(config.getProperty("driver"));
			
			String url = String.format("%s:%s%s", config.getProperty("adapter"), 
							config.getProperty("host"), config.getProperty("name"));
      connection = DriverManager.getConnection(url);

      load();
    } catch (SQLException e) {
      throw new Database.Error(e);
    } catch (ClassNotFoundException e) {
      throw new Database.Error(e);
    }
  }
  
  public void shutdown() throws Database.Error {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new Database.Error(e);
      }
    }
  }

  // FIXME: Wrazliwe na newlines
  private void load() throws Database.Error {
    update(read(config.getProperty("host") + "/schema.sql"));
		update(read(config.getProperty("host") + "/seeds.sql"));			
	}
  
  public QueryResults query(String sql, Object... bindings) throws Database.Error {
    PreparedStatement stmt = preparedStatement(sql, bindings);
		log.info(sql);			
    try {
      return new QueryResults(stmt.executeQuery());
    } catch (SQLException e) {
      throw new Database.Error(e);
    }
  }
  
  public void update(String sql, Object... bindings) throws Database.Error {
    PreparedStatement stmt = preparedStatement(sql, bindings);
		log.info(sql);
    try {
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new Database.Error(e);
    }
  }
  
  protected PreparedStatement preparedStatement(String sql, Object... bindings) 
      throws Database.Error {
    try {
      PreparedStatement stmt = connection.prepareStatement(sql);
      int i = 1;
      for (Object b : bindings) {
        stmt.setObject(i, b);
        i += 1;
      }
      return stmt;
    } catch (SQLException e) {
      throw new Database.Error(e);
    }
  }
  
  protected String read(String fileName) throws Database.Error {
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader file = new BufferedReader(
          new FileReader(new File(fileName).getAbsoluteFile()));
      try {
        String line = null;
        while ((line = file.readLine()) != null) {
          sb.append(line).append(System.getProperty("line.separator"));
        }
      } finally {
        file.close();
      }
    } catch (IOException e) {
      throw new Database.Error(e);
    }
    return sb.toString();
  }
  
  public static class Error extends Exception {
    public Error(String msg) { super(msg); }
    public Error(Exception e) { super(e); }
  }
}
