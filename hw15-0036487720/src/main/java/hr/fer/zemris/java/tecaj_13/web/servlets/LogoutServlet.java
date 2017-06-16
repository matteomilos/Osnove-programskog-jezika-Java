package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles the logout process, simply invalidates the session and
 * redirects the user to landing page.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet(urlPatterns = "/servleti/logout") 
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().setAttribute("current.user.nick", null);
		req.getSession().setAttribute("current.user.id", null);
		req.getSession().setAttribute("current.user.fn", null);
		req.getSession().setAttribute("current.user.ln", null);

		req.getRequestDispatcher("/servleti/main").forward(req, resp);
	}
}
