package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used for creating .xls file that contains numbers from a to b and
 * their n-th powers.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet(urlPatterns = {
		"/powers"
})
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int a, b, n;
		try {
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
			n = Integer.valueOf(req.getParameter("n"));
		} catch (NumberFormatException exc) {
			req.getRequestDispatcher("/WEB-INF/pages/invalid.jsp").forward(req, resp);
			return;
		}

		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/invalid.jsp").forward(req, resp);
			return;
		}

		if (a > b) {
			b = a + b;
			a = b - a;
			b = b - a;
		}
		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + i);
			HSSFRow firstRow = sheet.createRow(0);
			firstRow.createCell(0).setCellValue("Value: ");
			firstRow.createCell(1).setCellValue(i + ". power");
			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(j - a + 1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=rezultati.xls");

		hwb.write(resp.getOutputStream());
		hwb.close();

	}
}
