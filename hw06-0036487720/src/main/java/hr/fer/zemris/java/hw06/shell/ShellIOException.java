package hr.fer.zemris.java.hw06.shell;

/**
 * <code>ShellIOException</code> extends class <code>RuntimeException</code>, it
 * is thrown by methods of the {@linkplain Environment} interface and is used as
 * indicator writing or reading is not possible anymore and shell should be
 * terminated.
 * 
 * @author Matteo Miloš
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * <code>SerialVersionUID</code> of the exception
	 */
	private static final long serialVersionUID = -1491587332828227666L;

	/**
	 * Public constructor which creates new exception with <code>null</code> as
	 * its error message string.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Public constructor of class <code>EmptyStackException</code> which
	 * delegates message which more precisely describes exception
	 * 
	 * @param text
	 *            message that will be printed
	 */
	public ShellIOException(String text) {
		super(text);
	}

}
