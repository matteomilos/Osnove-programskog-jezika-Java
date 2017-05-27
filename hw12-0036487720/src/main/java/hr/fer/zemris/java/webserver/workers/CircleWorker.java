package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker class used for printing circle to the context output stream in form of
 * png image. It produces single png image with dimensions 200 x 200 and blue
 * filled circle.
 * 
 * @author Matteo Miloš
 *
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("image/png");
		try {
			BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g2d = bim.createGraphics();
			g2d.setColor(Color.BLUE);
			g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
			g2d.dispose();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
