package hr.fer.zemris.java.dz14.servlets.voting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dz14.dao.DAOProvider;
import hr.fer.zemris.java.dz14.model.PollOption;

/**
 * This servlet serves as a voting simulator for Bands. Voting results are
 * stored in a file named glasanjerezultati.txt on a server.
 * 
 * @author Matteo Miloš
 */
@WebServlet("/servleti/glasanje-glasaj")
public class VotingVoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		DAOProvider.getDao().updateVotes(req.getParameter("id"));

		String pollId =
				DAOProvider.getDao().getPollIDfromVoteId(req.getParameter("id"));

		List<PollOption> choices = new ArrayList<>();

		choices = sortChoices(DAOProvider.getDao().getChoices(pollId));

		req.getServletContext().setAttribute("choices", choices);
		req.setAttribute("winning", findWinners(choices));

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method used for sorting choices based on the count of the votes.
	 * 
	 * @param choices
	 *            list of choices
	 * @return sorted list of choices
	 */
	private List<PollOption> sortChoices(List<PollOption> choices) {
		return choices.stream().sorted((b1, b2) -> Integer.compare(b2.getScore(), b1.getScore())).collect(Collectors.toList());
	}

	/**
	 * Method used for finding choices with most votes.
	 *
	 * @param choices
	 *            the sorted choices
	 * @return the list of winners
	 */
	private List<PollOption> findWinners(List<PollOption> choices) {
		return choices.stream().filter((b) -> b.getScore() == choices.get(0).getScore()).collect(Collectors.toList());
	}

}
