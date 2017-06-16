package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This Java Persistence API data access object implementation is used for
 * communication with database.
 * 
 * @author Matteo Miloš
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry =
				JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		List<BlogUser> lista =
				(List<BlogUser>) JPAEMProvider.getEntityManager().createQuery("select b from BlogUser b").getResultList();
		JPAEMProvider.close();
		return lista;
	}

	@Override
	public BlogUser getUser(String nick) {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = null;
		try {
			user = (BlogUser) em.createQuery("select u from BlogUser u where u.nick=:nick").setParameter("nick", nick).getSingleResult();
		} catch (NoResultException e) {
		}
		JPAEMProvider.close();
		return user;
	}

	@Override
	public void createNewUser(BlogUser user) {
		insert(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUserBlogEntries(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogEntry> entries = null;
		try {
			entries =
					(List<BlogEntry>) em.createQuery("select u from BlogEntry u where u.creator=:creator").setParameter("creator", user).getResultList();
		} catch (NoResultException e) {
		}
		JPAEMProvider.close();
		return entries;
	}

	@Override
	public void addNewBlogEntry(BlogEntry entry) {
		insert(entry);
	}

	@Override
	public void updateBlogEntry(BlogEntry entry) {
		insert(entry);
	}

	@Override
	public void addNewComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
	}

	/**
	 * Persists the object to database and commits.
	 * 
	 * @param obj
	 *            object to be persisted to database
	 */
	private void insert(Object obj) {
		JPAEMProvider.getEntityManager().persist(obj);
		JPAEMProvider.close();
	}

}