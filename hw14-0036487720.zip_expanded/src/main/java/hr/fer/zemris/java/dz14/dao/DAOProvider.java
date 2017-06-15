package hr.fer.zemris.java.dz14.dao;

import hr.fer.zemris.java.dz14.dao.sql.SQLDAO;

/**
 * Singleton class which knows which instance should be return to enable
 * communication with database.
 * 
 * @author Matteo Miloš
 *
 */
public class DAOProvider {

	/** instance of {@link DAO}(Data Access Object) */
	private static DAO dao = new SQLDAO();

	/**
	 * Fetching the instance.
	 * 
	 * @return object that encapsulates access to the {@link DAO}.
	 */
	public static DAO getDao() {
		return dao;
	}

}