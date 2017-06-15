package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for creating table for numbers from a to b, and their sinus and
 * cosinus values.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet(urlPatterns = {
		"/trigonometric"
})
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class used as an wrapper for sinus and cosinus value.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	public static class SinusAndCosinus {

		/** Sinus value */
		double sinus;

		/** Cosinus value */
		double cosinus;

		/**
		 * Constructor for creating sinus and cosinus value.
		 * 
		 * @param sinus
		 *            sinus value
		 * @param cosinus
		 *            cosinus value
		 */
		public SinusAndCosinus(double sinus, double cosinus) {
			super();
			this.sinus = sinus;
			this.cosinus = cosinus;
		}

		/**
		 * Getter for sinus value.
		 * 
		 * @return sinus value
		 */
		public double getSinus() {
			return sinus;
		}

		/**
		 * Getter for cosinus value
		 * 
		 * @return cosinus value
		 */
		public double getCosinus() {
			return cosinus;
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int a = req.getParameter("a") == null	? 0
												: Integer.valueOf(req.getParameter("a"));
		int b = req.getParameter("b") == null	? 360
												: Integer.valueOf(req.getParameter("b"));
		if (a > b) {
			int help = a;
			a = b;
			b = help;
		}
		if (b > (a + 720)) {
			b = 720;
		}
		Map<Integer, SinusAndCosinus> results = new HashMap<>();
		for (int i = a; i <= b; i++) {
			results.put(i, new SinusAndCosinus(Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}
		req.setAttribute("results", results);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
