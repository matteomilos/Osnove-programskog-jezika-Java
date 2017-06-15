package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for showing possible choices to the user.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/glasanje")
public class VotingServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Class used as an encapsulator for band information.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	public static class Band {

		/** Id of the band. */
		private String id;

		/** The name of the band. */
		private String name;

		/** The band's song. */
		private String song;

		/**
		 * Instantiates a new band with given parameters.
		 *
		 * @param id
		 *            the id of the band
		 * @param name
		 *            the name of the band
		 * @param song
		 *            band's song
		 */
		public Band(String id, String name, String song) {
			this.id = id;
			this.name = name;
			this.song = song;
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
		 * Gets the band's song.
		 *
		 * @return the song
		 */
		public String getSong() {
			return song;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));

		req.getServletContext().setAttribute("bands", loadBands(lines));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * Load bands from given list of lines.
	 *
	 * @param lines
	 *            the lines
	 * @return the band list
	 */
	private List<Band> loadBands(List<String> lines) {
		List<Band> bands = new ArrayList<>();

		for (String line : lines) {
			String[] parts = line.split("\\t");
			bands.add(new Band(parts[0], parts[1], parts[2]));
		}

		return bands;
	}
}
