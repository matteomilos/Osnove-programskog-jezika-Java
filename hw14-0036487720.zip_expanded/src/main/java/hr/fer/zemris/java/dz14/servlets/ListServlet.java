package hr.fer.zemris.java.dz14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dz14.dao.DAOProvider;
import hr.fer.zemris.java.dz14.model.Poll;

/**
 * Servlet which represents the homepage, lists all the polls. User can choose
 * the poll which he wants to vote.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/servleti/index.html")
public class ListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Poll> polls = DAOProvider.getDao().getPolls();
		
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/PollList.jsp").forward(req, resp);
	}
}
