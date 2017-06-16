package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The Class JPAEMFProvider used to provide entity manager factory.
 * 
 * @author Matteo Miloš
 */
public class JPAEMFProvider {

	/** The entity manager factory. */
	public static EntityManagerFactory emf;

	/**
	 * Gets the The entity manager factory
	 *
	 * @return the The entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the The entity manager factory
	 *
	 * @param emf
	 *            the new The entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}