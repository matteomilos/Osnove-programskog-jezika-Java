package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting background color of the browser.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet(urlPatterns = {
		"/setcolor", "/colors"
})
public class ColorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (!req.getRequestURL().toString().endsWith("colors")) {
			String color = req.getParameter("color") == null	? "white"
																: (String) req.getParameter("color");

			if (!(color.equals("red") || color.equals("green") || color.equals("cyan"))) {
				color = "white";
			}

			req.getSession(true).setAttribute("pickedBgCol", color);
		}
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);

	}
}