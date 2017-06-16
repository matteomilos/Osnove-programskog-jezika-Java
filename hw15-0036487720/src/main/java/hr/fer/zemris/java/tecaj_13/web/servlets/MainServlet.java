package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * The Class MainServlet used to display all functionalities, logging,
 * registering , and forwarding to authoring of blogs.
 * 
 * @author Matteo Miloš
 */
@WebServlet(urlPatterns = {
		"/servleti/main"
})
public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("registeredUsers", users);

		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}
}
