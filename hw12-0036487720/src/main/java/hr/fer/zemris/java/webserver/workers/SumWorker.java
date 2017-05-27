package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker class used for calculating the sum of passed parameters "a" and "b".
 * If some parameter is not passed, than its value is set to 1. Worker prints
 * the result and passed parameters using "calc.smscr" script, which prints
 * content as an html table in this way:
 * 
 * <table border="1">
 * 
 * 	<tbody>
 * 		<tr>
 *			<td>a</td>
 * 			<td>11</td>
 * 		</tr>
 * 		<tr>
 * 			<td>b</td>
 *			<td>22</td>
 * 		</tr>
 * 		<tr>
 * 			<td>a+b</td>
 * 			<td>33</td>
 * 		</tr>
 * 
 *	 </tbody>
 * 
 * </table>
 * 
 * @author Matteo Miloš
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		int a = getIntParam(context.getParameter("a"));
		int b = getIntParam(context.getParameter("b"));

		context.setTemporaryParameter("zbroj", Integer.toString(a + b));
		context.setTemporaryParameter("a", Integer.toString(a));
		context.setTemporaryParameter("b", Integer.toString(b));

		try {
			context.getDispatcher().dispatchRequest("/private/calc.smscr");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used for loading integer from passed string, and setting value to
	 * 1 if no parameter is passed.
	 * 
	 * @param value
	 *            string representation of the parameters value
	 * @return int value of the parameter
	 */
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
