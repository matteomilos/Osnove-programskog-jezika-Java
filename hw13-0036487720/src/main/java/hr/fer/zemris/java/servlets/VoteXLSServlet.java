package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.VotingServlet.Band;

/**
 * Servlet used for creating .xls file containing information about voting.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/glasanje-xls")
public class VoteXLSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		List<Band> bands = (List<Band>) req.getServletContext().getAttribute("bands");
		Map<String, Integer> scores = (Map<String, Integer>) req.getServletContext().getAttribute("results");

		HSSFSheet sheet = hwb.createSheet("Results");
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Band name");
		firstRow.createCell(1).setCellValue("Song");
		int i = 0;
		for (Map.Entry<String, Integer> entry : scores.entrySet()) {
			HSSFRow rowhead = sheet.createRow(++i);
			rowhead.createCell(0).setCellValue(bands.get(Integer.valueOf(entry.getKey()) - 1).getName());
			rowhead.createCell(1).setCellValue(entry.getValue());
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=rezultatiGlasanja.xls");
		hwb.write(resp.getOutputStream());
		hwb.close();

	}

}
