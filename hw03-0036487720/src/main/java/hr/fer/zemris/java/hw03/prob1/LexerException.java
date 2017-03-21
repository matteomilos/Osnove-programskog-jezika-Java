package hr.fer.zemris.java.hw03.prob1;

import javax.naming.LimitExceededException;

public class LexerException extends RuntimeException {

	public LexerException(String string) {
		super(string);
	}
	
	public LexerException(){
		super();
	}

}
