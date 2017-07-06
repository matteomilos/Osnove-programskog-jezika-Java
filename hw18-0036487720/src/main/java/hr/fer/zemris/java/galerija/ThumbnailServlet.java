package hr.fer.zemris.java.galerija;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * The Class ThumbnailServlet is used for creating neccessary thumbnails from
 * images that have chosen tag if they are not already created. Also, this
 * servlet sends list of paths to these thumbnails as a json object.
 * 
 * @author Matteo Miloš
 * 
 * 
 */
@WebServlet("/thumbnail")
public class ThumbnailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant THUMBNAIL_DIM. */
	private static final int THUMBNAIL_DIM = 150;

	/** The Constant THUMBNAILS. */
	public static final String THUMBNAILS = "/WEB-INF/thumbnails/";

	/** The Constant REAL_IMAGES. */
	public static final String REAL_IMAGES = "/WEB-INF/slike/";

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tag");

		List<Image> images = ((Map<String, List<Image>>) req.getServletContext().getAttribute("map")).get(tag);

		Path path = Paths.get(req.getServletContext().getRealPath(THUMBNAILS));

		try {
			Files.createDirectory(path);
		} catch (FileAlreadyExistsException ignorable) {
		}

		for (Image image : images) {

			File thumb = Paths.get(path.toString(), image.getPath().toString()).toFile();

			if (!thumb.exists()) {

				File first = new File(
						Paths.get(req.getServletContext().getRealPath(REAL_IMAGES)).toString() + File.separator + image
								.getPath().toString()
				);

				createThumbnail(thumb, first);
			}

		}
		List<String> paths = images.stream().map(Image::getPath).map(e -> e.toString()).collect(Collectors.toList());
		Gson gson = new Gson();
		String text = gson.toJson(paths);

		resp.getWriter().write(text);
		resp.getWriter().flush();
	}

	/**
	 * Method used for creating thumbnail from given path.
	 * 
	 * @param thumb
	 *            path to the new thumbnail
	 * @param first
	 *            path of the image
	 * @throws IOException
	 *             if I/O exception of some sort has occurred
	 */
	private void createThumbnail(File thumb, File first) throws IOException {
		BufferedImage thumbImg;
		BufferedImage firstImg = ImageIO.read(first);
		thumbImg = new BufferedImage(THUMBNAIL_DIM, THUMBNAIL_DIM, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = thumbImg.createGraphics();
		double sx = THUMBNAIL_DIM * 1. / firstImg.getWidth();
		double sy = THUMBNAIL_DIM * 1. / firstImg.getHeight();
		AffineTransform at = AffineTransform.getScaleInstance(sx, sy);
		g.drawRenderedImage(firstImg, at);
		ImageIO.write(thumbImg, "jpg", thumb);
	}
}
