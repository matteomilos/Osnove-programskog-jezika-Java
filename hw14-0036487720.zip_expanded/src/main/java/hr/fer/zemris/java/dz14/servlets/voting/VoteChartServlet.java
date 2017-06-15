package hr.fer.zemris.java.dz14.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.dz14.model.PollOption;

/**
 * Servlet used for creating chart based on the votes.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class VoteChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("image/png");

		JFreeChart chart =
				ChartFactory.createPieChart3D("Favourite choices", getDataset(req), true, true, false);
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 500, 270);

	}

	/**
	 * Method used for creating dataset from request attributes.
	 * 
	 * @param req
	 *            request
	 * @return instance of {@link PieDataset}
	 */
	@SuppressWarnings("unchecked")
	private PieDataset getDataset(HttpServletRequest req) {
		List<PollOption> choices =
				(List<PollOption>) req.getServletContext().getAttribute("choices");

		DefaultPieDataset result = new DefaultPieDataset();
		for (PollOption choice : choices) {
			result.setValue(choice.getName(), choice.getScore());
		}

		return result;
	}

}
