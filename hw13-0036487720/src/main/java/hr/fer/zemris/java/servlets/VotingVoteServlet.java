package hr.fer.zemris.java.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.VotingServlet.Band;

/**
 * This servlet serves as a voting simulator for Bands. Voting results are
 * stored in a file named glasanjerezultati.txt on a server.
 * 
 * @author Matteo Miloš
 */
@WebServlet("/glasanje-glasaj")
public class VotingVoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		Map<String, Integer> score = new HashMap<>();
		Path file = Paths.get(fileName);

		if (Files.exists(file)) {
			score = getScores(file);
		} else {
			createBands(req, score);
		}

		score.put(req.getParameter("id"), score.get(req.getParameter("id")) + 1);

		writeToFile(score, file);

		Map<String, Integer> sorted = sortScores(score);

		req.getServletContext().setAttribute("results", sorted);
		req.setAttribute("winning", findWinners(sorted));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method used for writing band information to the file.
	 *
	 * @param score
	 *            the score of the band
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeToFile(Map<String, Integer> score, Path file)
			throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(file);
		for (Map.Entry<String, Integer> entry : score.entrySet()) {
			writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
		}
		writer.close();
	}

	/**
	 * Creates the bands from request attributes.
	 *
	 * @param req
	 *            the request
	 * @param score
	 *            the scores of the bands
	 */
	@SuppressWarnings("unchecked")
	private void createBands(HttpServletRequest req, Map<String, Integer> score) {
		List<Band> bands = (List<Band>) req.getServletContext().getAttribute("bands");
		for (Band band : bands) {
			score.put(band.getId(), 0);
		}
	}

	/**
	 * Method used for finding bands with most votes.
	 *
	 * @param bands
	 *            the sorted bands
	 * @return the list of winners
	 */
	private List<Integer> findWinners(Map<String, Integer> bands) {
		int maxVotes = Collections.max(bands.values());
		List<Integer> winners = new ArrayList<>();
		for (Entry<String, Integer> entry : bands.entrySet()) {
			if (entry.getValue() == maxVotes) {
				winners.add(Integer.valueOf(entry.getKey()) - 1);
			} else {
				break;/*-ordered so no more winners*/
			}
		}
		return winners;
	}

	/**
	 * Method used for sorting bands based on their score.
	 *
	 * @param score
	 *            the score of each band
	 * @return the map of band id-s and scores
	 */
	private Map<String, Integer> sortScores(Map<String, Integer> score) {
		Map<String, Integer> sorted = score.entrySet().stream().sorted(Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sorted;
	}

	/**
	 * Gets the scores from specified file.
	 *
	 * @param file
	 *            the file from where scores are loaded
	 * @return the scores
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Map<String, Integer> getScores(Path file) throws IOException {
		Map<String, Integer> score = new HashMap<>();

		List<String> lines = Files.readAllLines(file);
		for (String line : lines) {
			String[] parts = line.split("\\t");
			score.put(parts[0], Integer.valueOf(parts[1]));
		}

		return score;
	}

}
