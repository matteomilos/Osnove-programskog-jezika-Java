package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * The Class DAOProvider used as a singleton for JPADAOIMPl wrapped as DAO.
 * @author Matteo Miloš
 * 
 */
public class DAOProvider {

	/** The data access object. */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Gets the dao.
	 *
	 * @return the dao
	 */
	public static DAO getDAO() {
		return dao;
	}

}