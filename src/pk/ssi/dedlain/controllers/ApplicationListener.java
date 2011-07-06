package pk.ssi.dedlain.controllers;

import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import pk.ssi.dedlain.utils.Database;

public class ApplicationListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    
    Properties config = new Properties();
    config.setProperty("host", context.getRealPath(context.getInitParameter("db.host")));
    config.setProperty("adapter", context.getInitParameter("db.adapter"));
    config.setProperty("driver", context.getInitParameter("db.driver"));
    config.setProperty("name", context.getInitParameter("db.name"));
    
    try {
     context.setAttribute("database", new Database(config));
    } catch (Database.Error e) {
      context.log("Database init", e);
    }
	}

	public void contextDestroyed(ServletContextEvent sce) {
    Database db = (Database) sce.getServletContext().getAttribute("database");
    if (db != null) {
      try {
        db.shutdown();
      } catch (Database.Error e) {
        sce.getServletContext().log("Database shutdown", e);
      }
    }
	}
}
