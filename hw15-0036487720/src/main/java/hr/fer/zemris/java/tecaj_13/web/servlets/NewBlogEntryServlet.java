package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that handles creating new blog entries and stores them properly into
 * the database for the dear user.
 * 
 * @author Matteo Miloš
 */
@WebServlet(urlPatterns = "/servleti/createNew")
public class NewBlogEntryServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String nick = (String) req.getSession().getAttribute("current.user.nick");
		BlogUser creator = DAOProvider.getDAO().getUser(nick);

		BlogEntry entry = new BlogEntry();
		entry.setCreator(creator);
		entry.setTitle(title);
		entry.setText(text);
		entry.setCreatedAt(new Date());

		DAOProvider.getDAO().addNewBlogEntry(entry);
		resp.sendRedirect("author/" + nick);
	}
}
