package hr.fer.zemris.java.dz14.dao.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;

import hr.fer.zemris.java.dz14.dao.DAO;
import hr.fer.zemris.java.dz14.dao.DAOException;
import hr.fer.zemris.java.dz14.model.Poll;
import hr.fer.zemris.java.dz14.model.PollOption;

/**
 * 
 * Implementation of DAO subsystem using SQL technology. This implementation
 * expects that its connection is available through
 * {@link SQLConnectionProvider} class, which means that someone should set it
 * up before usage of this class.
 * 
 * @author Matteo Miloš
 */
public class SQLDAO implements DAO {

	/** state of {@link SQLException} which means that table already exists */
	private static final String TABLE_ALREADY_EXISTS_STATE = "X0Y32";

	@Override
	public void createPolls(Connection con) {

		try (PreparedStatement pst =
				con.prepareStatement("CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + "title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)")) {

			try {
				pst.executeUpdate();
			} catch (SQLException exc) {

				if (!exc.getSQLState().equals(TABLE_ALREADY_EXISTS_STATE)) {
					throw exc;
				}
			}

		} catch (Exception ex) {
			throw new DAOException("Pogreška inicijalizacije baze." + " " + ex.getMessage(), ex);
		}

	}

	@Override
	public void createPollOptions(Connection con) {

		try (PreparedStatement pst =
				con.prepareStatement("CREATE TABLE PollOptions (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, optionTitle VARCHAR(100) NOT NULL, optionLink VARCHAR(150) NOT NULL, pollID BIGINT, votesCount BIGINT, FOREIGN KEY (pollID) REFERENCES Polls(id))");) {

			try {
				pst.executeUpdate();
			} catch (SQLException exc) {

				if (!exc.getSQLState().equals(TABLE_ALREADY_EXISTS_STATE)) {
					throw exc;
				}
			}

		} catch (Exception ex) {
			throw new DAOException("Pogreška inicijalizacije baze.", ex);
		}
	}

	@Override
	public void fillPolls(Connection con, ServletContextEvent sce)
			throws SQLException {
		addRowsToPolls("Voting for favourite band:", "Of the following bands, which is Your favourite? Click on the link to vote!", con, sce.getServletContext().getRealPath("WEB-INF/glasanje-definicija1.txt"));
		addRowsToPolls("Voting for favourite movie:", "Of the following movies, which is Your favourite? Click on the link to vote!", con, sce.getServletContext().getRealPath("WEB-INF/glasanje-definicija2.txt"));
	}

	/**
	 * Private method for filling Polls table with specified information
	 * 
	 * @param title
	 *            title of the poll
	 * @param message
	 *            message for the poll
	 * @param definitionPath
	 *            path to the definition file for the poll
	 * @param con
	 *            connection to the database
	 * @throws SQLException
	 *             if exception occurs during work with database
	 * 
	 */
	private void addRowsToPolls(String title, String message, Connection con, String definitionPath)
			throws SQLException {
		Scanner sc;

		try {
			sc = new Scanner(new FileInputStream(definitionPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		try (PreparedStatement pst =
				con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, title);
			pst.setString(2, message);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating poll failed, no rows affected.");
			}

			try (ResultSet generatedKeys = pst.getGeneratedKeys()) {

				if (generatedKeys.next()) {
					addRowsToPollOptions(generatedKeys.getLong(1), sc, con);

				} else {
					throw new DAOException("Pogreška inicijalizacije baze.");
				}
			}
		}
	}

	/**
	 * Private method used for filling PollOptions table with specified
	 * information.
	 * 
	 * @param id
	 *            id of the poll
	 * @param sc
	 *            scanner containing definition file
	 * @param con
	 *            connection to the database
	 * @throws SQLException
	 *             if exception occurs during work with database
	 * 
	 */
	private void addRowsToPollOptions(long id, Scanner sc, Connection con)
			throws SQLException {

		try (PreparedStatement pst =
				con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

			while (sc.hasNext()) {

				String[] line = sc.nextLine().split("\\t");

				pst.setString(1, line[0]);
				pst.setString(2, line[1]);
				pst.setLong(3, id);
				pst.setLong(4, 0);

				int affectedRows = pst.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Creating table failed, no rows affected.");
				}
			}
		}
	}

	@Override
	public boolean IsPollsEmpty(Connection con) throws SQLException {

		try (PreparedStatement pst =
				con.prepareStatement("SELECT * FROM Polls")) {

			ResultSet rs = pst.executeQuery();

			return !rs.next();
		}
	}

	@Override
	public List<Poll> getPolls() {
		List<Poll> unosi = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement pst =
				con.prepareStatement("select id, title, message from Polls order by id")) {

			ResultSet rs = pst.executeQuery();

			try {
				while (rs != null && rs.next()) {
					unosi.add(new Poll(rs.getLong(1), rs.getString(2), rs.getString(3)));
				}

			} finally {
				try {
					rs.close();
				} catch (Exception ignorable) {
				}
			}

		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste anketa.", ex);
		}

		return unosi;
	}

	@Override
	public List<PollOption> getChoices(String pollId) {
		List<PollOption> bands = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement pst =
				con.prepareStatement("SELECT * FROM PollOptions WHERE pollid=?")) {

			pst.setString(1, pollId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String id = String.valueOf(rs.getLong(1));
				String title = rs.getString(2);
				String link = rs.getString(3);
				int score = rs.getInt(5);

				bands.add(new PollOption(id, title, link, score));
			}
		} catch (SQLException e) {
		}
		return bands;
	}

	@Override
	public void updateVotes(String id) {
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement pst =
				con.prepareStatement("UPDATE PollOptions set votesCount = votesCount + 1 WHERE id=?")) {

			pst.setString(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
		}
	}

	@Override
	public String getPollIDfromVoteId(String id) {
		Connection con = SQLConnectionProvider.getConnection();
		String pollID = null;

		try (PreparedStatement pst =
				con.prepareStatement("Select pollID FROM PollOptions WHERE id=?")) {

			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				pollID = rs.getString(1);
			}
		} catch (SQLException e) {
		}

		return pollID;
	}

	@Override
	public Poll getPoll(String pollId) {
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement pst =
				con.prepareStatement("SELECT * FROM Polls WHERE id=?")) {

			pst.setString(1, pollId);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				return new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
			}

		} catch (SQLException e) {
		}
		return null;
	}
}