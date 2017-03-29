package hr.fer.zemris.java.hw04.db;

public class QueryParserException extends RuntimeException {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3667191983754123032L;

	public QueryParserException(){
		super();
	}
	
	public QueryParserException(String message){
		super(message);
	}
}
