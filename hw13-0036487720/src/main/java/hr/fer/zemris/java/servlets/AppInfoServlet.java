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
		"/appinfo"
})
public class AppInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long started = (Long) req.getServletContext().getAttribute("started");
		req.setAttribute("duration", prettyTimeStamp(System.currentTimeMillis() - started));
		req.getRequestDispatcher("/WEB-INF/pages/appinfo.jsp").forward(req, resp);

	}

	/**
	 * Simple method used for converting time in milliseconds to string
	 * representation which tells us how many days, hours, minutes, seconds and
	 * milliseconds have passed.
	 * 
	 * @param time
	 *            time in milliseconds
	 * @return String representation of time
	 */
	private String prettyTimeStamp(long time) {
		long seconds = time / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		StringBuilder sb = new StringBuilder();
		sb.append(
				days > 0 ? days + " days " : ""
		).append(
				hours % 24 > 0 ? hours % 24 + " hours " : ""
		).append(
				minutes % 60 > 0 ? minutes % 60 + " minutes " : ""
		).append(
				seconds % 60 > 0 ? seconds % 60 + " seconds " : ""
		).append("and " + time % 1000 + " milliseconds");
		return sb.toString();
	}
}