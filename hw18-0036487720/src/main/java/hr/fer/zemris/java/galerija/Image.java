package hr.fer.zemris.java.galerija;

import java.nio.file.Path;
import java.util.List;

/**
 * The Class Image that encapsulates all information about one image.
 */
public class Image {

	/** The path of the image. */
	private Path path;

	/** The description of the image. */
	private String description;

	/** The tags of the image. */
	private List<String> tags;

	/**
	 * Instantiates a new image from given arguments.
	 *
	 * @param path
	 *            the path
	 * @param description
	 *            the description
	 * @param tags
	 *            the tags
	 */
	public Image(Path path, String description, List<String> tags) {
		this.path = path;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Gets the path of the image.
	 *
	 * @return the image path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Sets the path of the image.
	 *
	 * @param path
	 *            the new path
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Gets the description of the image.
	 *
	 * @return the image description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the image.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the tags of the image.
	 *
	 * @return the image tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Sets the tags of the image.
	 *
	 * @param tags
	 *            the new tags
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
