package hr.fer.zemris.java.dz14.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContextEvent;

import hr.fer.zemris.java.dz14.model.Poll;
import hr.fer.zemris.java.dz14.model.PollOption;

/**
 * Interface which provides all the necessary methods for communication with
 * database.
 * 
 * @author Matteo Miloš
 *
 */
public interface DAO {

	/**
	 * Method used for creating table Polls.
	 * 
	 * @param con
	 *            connection to the database
	 * 
	 */
	public void createPolls(Connection con);

	/**
	 * Method used for creating table PollOptions.
	 * 
	 * @param con
	 *            connection to the database
	 * 
	 */
	public void createPollOptions(Connection con);

	/**
	 * Method used for filling Polls table with specified data
	 * 
	 * @param sce
	 *            context event of the servlet
	 * @param con
	 *            connection to the database
	 * @throws SQLException
	 *             if exception occurs during work with database
	 */
	public void fillPolls(Connection con, ServletContextEvent sce)
			throws SQLException;

	/**
	 * Method that checks if table Polls is empty.
	 * 
	 * @return true if table Polls is empty, false otherwise
	 * @param con
	 *            connection to the database
	 * @throws SQLException
	 *             if exception occurs during work with database
	 */
	public boolean IsPollsEmpty(Connection con) throws SQLException;

	/**
	 * Method for getting list of all the polls in table Polls.
	 * 
	 * @return list of polls
	 */
	public List<Poll> getPolls();

	/**
	 * Method for getting list of all the possible choices for specified poll
	 * id.
	 * 
	 * @param pollId
	 *            id of the poll
	 * @return list of choices
	 */
	public List<PollOption> getChoices(String pollId);

	/**
	 * Method used for updating(incrementing) votes for specified if of the
	 * choice.
	 * 
	 * @param id
	 *            id of the choice
	 */
	public void updateVotes(String id);

	/**
	 * Method used for getting the id of the poll from the id of the choice
	 * 
	 * @param pollID
	 *            of the choice
	 * @return id of the poll
	 */
	public String getPollIDfromVoteId(String pollID);

	/**
	 * Method used for getting poll from specified poll id
	 * 
	 * @param pollId
	 *            id of the poll
	 * @return poll with given id
	 */
	public Poll getPoll(String pollId);

}