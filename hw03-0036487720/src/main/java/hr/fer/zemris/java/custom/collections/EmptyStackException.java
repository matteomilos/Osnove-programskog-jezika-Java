package hr.fer.zemris.java.custom.collections;

/**
 * <code>EmptyStackException</code> extends class <code>RuntimeException</code>,
 * it is thrown by methods in the <code>ObjectStack</code> and is used as
 * indicator that stack is empty.
 * 
 * @author Matteo Miloš
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * <code>SerialVersionUID</code> of the exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which creates new exception with <code>null</code> as
	 * its error message string.
	 */
	public EmptyStackException() {
	}

	/**
	 * Public constructor of class <code>EmptyStackException</code> which
	 * delegates message which more precisely describes exception
	 * 
	 * @param string
	 *            message that will be printed
	 */
	public EmptyStackException(String string) {
		super(string);
	}
}
