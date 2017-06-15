package hr.fer.zemris.java.dz14.model;

/**
 * The Class Poll which encapsulates one poll.
 */
public class Poll {

	/** The id of the poll. */
	private long id;

	/** The title of the poll. */
	private String title;

	/** The message of the poll. */
	private String message;

	/**
	 * Instantiates a new poll.
	 *
	 * @param id
	 *            the id of the poll
	 * @param title
	 *            the title of the poll
	 * @param message
	 *            the message of the poll
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Gets the id of the poll.
	 *
	 * @return the id of the poll
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * /** Gets the title of the poll.
	 *
	 * @return the title of the poll
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the message of the poll.
	 *
	 * @return the message of the poll
	 */
	public String getMessage() {
		return message;
	}

}
