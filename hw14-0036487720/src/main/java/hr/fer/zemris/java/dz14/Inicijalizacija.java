package hr.fer.zemris.java.dz14;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.dz14.dao.DAOProvider;

/**
 * Class used as a listener that waits for start of the webapp, and when app is
 * started, it creates necessary data (tables) for proper app functionalities.
 * It connects to the database, creates tables and fills them with data.
 * 
 * @author Matteo Miloš
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties")));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String dbName = prop.getProperty("name");
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		String connectionURL =
				"jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password=" + password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		try {
			DAOProvider.getDao().createPolls(cpds.getConnection());
			DAOProvider.getDao().createPollOptions(cpds.getConnection());

			if (DAOProvider.getDao().IsPollsEmpty(cpds.getConnection())) {
				DAOProvider.getDao().fillPolls(cpds.getConnection(), sce);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije baze podataka", e);
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds =
				(ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}