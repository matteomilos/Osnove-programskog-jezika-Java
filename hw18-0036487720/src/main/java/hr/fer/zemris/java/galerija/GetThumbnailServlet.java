package hr.fer.zemris.java.galerija;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that is used only for writing thumbnail to the screen.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/getThumbnail")
public class GetThumbnailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/jpg");
		ImageIO.write(
				ImageIO.read(
						new File(
								req.getServletContext()
										.getRealPath(ThumbnailServlet.THUMBNAILS)
										.toString() + File.separator + req.getParameter("name")
						)
				), "jpg", resp.getOutputStream()
		);
	}
}
