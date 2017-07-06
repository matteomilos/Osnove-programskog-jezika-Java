package hr.fer.zemris.java.galerija;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet used for retrieving set of all tags and sending it as a json object.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/tags")
public class TagsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		String json = gson.toJson(req.getServletContext().getAttribute("keys"));
		resp.getWriter().write(json);
		resp.getWriter().flush();
	}

}
