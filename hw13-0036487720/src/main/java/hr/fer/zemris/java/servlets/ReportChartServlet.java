package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Servlet used for generating simple pie chart with hardcoded information.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet(urlPatterns = {
		"/report", "/reportImage"
})
public class ReportChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getRequestURL().toString().endsWith("report")) {
			req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
		} else {
			resp.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), ChartFactory.createPieChart3D("Which operating system are you using?", createDataset(), true, true, false), 500, 270);
		}
	}

	/**
	 * Method used for creating dataset for chart
	 * 
	 * @return instance of {@link PieDataset}
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 19);
		result.setValue("Mac", 10);
		result.setValue("Windows", 71);
		return result;

	}

}
