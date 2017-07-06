package hr.fer.zemris.java.galerija;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for writing full sized image to the screen.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/realImage")
public class RealImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String imageName = req.getParameter("path");
		File imagePath = new File(
				req.getServletContext()
						.getRealPath(ThumbnailServlet.REAL_IMAGES)
						.toString() + File.separator + imageName
		);

		BufferedImage image = ImageIO.read(imagePath);

		resp.setContentType("image/png");
		ImageIO.write(image, "png", resp.getOutputStream());
	}
}
