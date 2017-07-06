package hr.fer.zemris.java.galerija;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The Class Initialization used for initialization of all the future neccessary
 * information for our webpage.
 * 
 * @author Matteo Miloš
 */
public class Initialization implements ServletContextListener {

	/** The Constant WEB_INF_OPISNIK_TXT. */
	private static final String WEB_INF_OPISNIK_TXT = "/WEB-INF/opisnik.txt";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path path = Paths.get(sce.getServletContext().getRealPath(WEB_INF_OPISNIK_TXT));

		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException exc) {
			throw new IllegalArgumentException("Can't read file:" + path.getFileName().toString());
		}

		int option = 1;
		path = null;
		String descr = null;
		List<Image> images = new ArrayList<>();
		List<String> tags = new ArrayList<>();
		Map<String, List<Image>> map = new HashMap<>();

		for (String line : lines) {
			switch (option) {
			case 1:
				path = Paths.get(line.trim());
				option = 2;
				break;
			case 2:
				descr = line.trim();
				option = 3;
				break;
			case 3:
				tags = Arrays.asList(line.trim().split("\\s*,\\s*"));
				option = 1;
				Image image = new Image(path, descr, tags);
				images.add(image);

				for (String tag : tags) {
					if (map.containsKey(tag)) {
						List<Image> lista = map.get(tag);
						lista.add(image);
					} else {
						List<Image> lista = new ArrayList<>();
						lista.add(image);
						map.put(tag, lista);
					}
				}
			}
		}

		sce.getServletContext().setAttribute("map", map);
		sce.getServletContext().setAttribute("keys", map.keySet());
		sce.getServletContext().setAttribute("images", images);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
