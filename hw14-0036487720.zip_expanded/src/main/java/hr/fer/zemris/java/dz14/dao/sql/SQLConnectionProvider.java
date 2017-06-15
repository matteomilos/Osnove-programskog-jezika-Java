package hr.fer.zemris.java.dz14.dao.sql;

import java.sql.Connection;

/**
 * Class used for storing connections to the database in {@link ThreadLocal}
 * object. That is actually a map whose keys are id-s for thread that is
 * executing an operation on the map.
 * 
 * @author Matteo Miloš
 *
 */
public class SQLConnectionProvider {

	/** connections to the database */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets connection for current thread (or deletes record from the map if
	 * argument is null).
	 * 
	 * @param con
	 *            connection to the database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Fetches connection which can be currently used by thread (caller).
	 * 
	 * @return connection to the database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}