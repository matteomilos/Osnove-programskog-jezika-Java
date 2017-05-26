package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a = getIntParam(context.getParameter("a"));
		int b = getIntParam(context.getParameter("b"));

		context.setTemporaryParameter("zbroj", Integer.toString(a + b));
		context.setTemporaryParameter("a", Integer.toString(a));
		context.setTemporaryParameter("b", Integer.toString(b));

		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

	private int getIntParam(String value) {
		int number;

		try {
			number = Integer.parseInt(value);

		} catch (NumberFormatException exc) {
			number = 1;
		}

		return number;
	}

}
