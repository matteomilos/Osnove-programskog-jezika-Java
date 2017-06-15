package hr.fer.zemris.java.dz14.model;

/**
 * Class used as an encapsulator for information about the choice.
 * 
 * @author Matteo Miloš
 *
 */
public class PollOption {

	/** Id of the band. */
	private String id;

	/** The name of the choice. */
	private String name;

	/** The link to the example of the choice. */
	private String link;

	/** The band's score */
	private int score;

	/**
	 * Instantiates a new band with given parameters.
	 *
	 * @param id
	 *            the id of the band
	 * @param name
	 *            the name of the band
	 * @param link
	 *            The link to the example of the choice.
	 * @param score
	 *            band's score
	 */
	public PollOption(String id, String name, String link, int score) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.score = score;
	}

	/**
	 * Gets the id of the band.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the name of the band.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the link to the example of the choice.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Gets the band's score
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

}