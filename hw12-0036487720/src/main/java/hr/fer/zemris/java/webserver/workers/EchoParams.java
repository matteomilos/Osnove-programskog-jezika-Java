package hr.fer.zemris.java.webserver.workers;

import java.util.Map.Entry;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		context.write("<html><body>");
		context.write("<table style=\"width:10%\" border=\"1\">");
		context.write("<tr>");
		context.write("<th>Name: </th>");
		context.write("<th>Value:</th>");
		context.write("</tr>");
		for (Entry<String, String> name : context.getParameters().entrySet()) {
			context.write("<tr>");
			context.write("<td align=\"center\">" + name.getKey() + "</td>");
			context.write("<td align=\"center\">" + name.getValue() + "</td>");
			context.write("</tr>");
		}
		context.write("</table>");
		context.write("</body></html>");
	}

}
