package hr.fer.zemris.java.galerija;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet used for getting information (description and tags) about image and
 * sending it as a json object.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/information")
public class InformationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");

		List<Image> images = (List<Image>) req.getServletContext().getAttribute("images");
		Image image =
				images.stream().filter((i) -> i.getPath().toString().equals(name)).collect(Collectors.toList()).get(0);

		String description = image.getDescription();
		String tags = image.getTags().toString();
		resp.getWriter().write(
				new Gson().toJson(
						new String[] {
								description, tags
						}
				)
		);
		resp.getWriter().flush();
	}

}
