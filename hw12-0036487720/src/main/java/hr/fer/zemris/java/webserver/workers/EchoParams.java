package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map.Entry;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class used for printing html table containing context parameters map to the
 * context output stream.
 * 
 * @author Matteo Miloš
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			context.write("<html><body>");
			context.write("<table style=\"width:10%\" border=\"1\">");

			context.write("<tr>");
			context.write("<th>Name: </th>");
			context.write("<th>Value:</th>");
			context.write("</tr>");

			for (Entry<String, String> entry : context.getParameters().entrySet()) {
				context.write("<tr>");
				context.write("<td align=\"center\">" + entry.getKey() + "</td>");
				context.write("<td align=\"center\">" + entry.getValue() + "</td>");
				context.write("</tr>");
			}

			context.write("</table>");

			context.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
