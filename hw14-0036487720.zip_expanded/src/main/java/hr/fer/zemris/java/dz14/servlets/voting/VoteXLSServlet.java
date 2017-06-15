package hr.fer.zemris.java.dz14.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.dz14.model.PollOption;

/**
 * Servlet used for creating .xls file containing information about voting.
 * 
 * @author Matteo Miloš
 *
 */
@WebServlet("/servleti/glasanje-xls")
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

		List<PollOption> choices =
				(List<PollOption>) req.getServletContext().getAttribute("choices");

		HSSFSheet sheet = hwb.createSheet("Results");
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Choice name");
		firstRow.createCell(1).setCellValue("Num of votes");
		int i = 0;

		for (PollOption choice : choices) {
			HSSFRow rowhead = sheet.createRow(++i);
			rowhead.createCell(0).setCellValue(choice.getName());
			rowhead.createCell(1).setCellValue(choice.getScore());
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=rezultatiGlasanja.xls");
		hwb.write(resp.getOutputStream());
		hwb.close();

	}

}
