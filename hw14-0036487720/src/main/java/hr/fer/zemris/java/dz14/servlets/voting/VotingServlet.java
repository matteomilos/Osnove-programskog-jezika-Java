package hr.fer.zemris.java.dz14.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dz14.dao.DAOProvider;
import hr.fer.zemris.java.dz14.model.Poll;
import hr.fer.zemris.java.dz14.model.PollOption;

/**
 * Servlet used for showing possible choices to the user.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/servleti/glasanje")
public class VotingServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pollId = (String) req.getParameter("pollid");

		List<PollOption> choices = DAOProvider.getDao().getChoices(pollId);
		req.getServletContext().setAttribute("choices", choices);

		Poll poll = DAOProvider.getDao().getPoll(pollId);

		req.getServletContext().setAttribute("title", poll.getTitle());
		req.getServletContext().setAttribute("message", poll.getMessage());

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
