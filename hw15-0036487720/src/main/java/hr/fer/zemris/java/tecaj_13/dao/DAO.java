package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Data access object interface, provides necessary database operations for this
 * homework.
 * 
 * @author Matteo Miloš
 *
 */
public interface DAO {

	/**
	 * Gets entry with given <code>id</code>. If that entry doesn't exist,
	 * returns <code>null</code>. Dohvaća entry sa zadanim <code>id</code>-em.
	 * 
	 * @param id
	 *            key of the entry
	 * @return entry or <code>null</code> if entry doesn't exist
	 * @throws DAOException
	 *             if exception happens during the data fetch
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Gets the list of all registered blog users
	 * 
	 * @return list of registered blog users
	 * @throws DAOException
	 *             if exception happens during the data fetch
	 */
	List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Returns the user with provided nick
	 * 
	 * @param nick
	 *            user's nick
	 * @return BlogUser associated with provided nick or null
	 */
	BlogUser getUser(String nick);

	/**
	 * This method creates a new user in a database.
	 * 
	 * @param user
	 *            user to be created in a database
	 */
	void createNewUser(BlogUser user);

	/**
	 * Returns all entries associated with provided user
	 * 
	 * @param user
	 *            user to get all the entries for
	 * @return list of blogentries for the user
	 */
	List<BlogEntry> getUserBlogEntries(BlogUser user);

	/**
	 * Creates a new blog entry in a database.
	 * 
	 * @param entry
	 *            entry to be created in a database
	 */
	void addNewBlogEntry(BlogEntry entry);

	/**
	 * Updates an existing blog entry inside a database.
	 * 
	 * @param entry
	 *            entry to be updated
	 */
	void updateBlogEntry(BlogEntry entry);

	/**
	 * Adds a new comment to database.
	 * 
	 * @param comment
	 *            comment to be added to db
	 */
	void addNewComment(BlogComment comment);
}