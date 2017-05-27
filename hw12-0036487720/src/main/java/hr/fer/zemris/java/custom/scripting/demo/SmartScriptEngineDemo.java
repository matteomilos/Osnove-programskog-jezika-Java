package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class used for demonstration of functionalities of {@link SmartScriptEngine}
 * class. This class was copied from homework assignment.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Method which is called at the start of this program.
	 * 
	 * @param args
	 *            command line arguments, not used in this method
	 * @throws IOException
	 *             if I/O exception occurs
	 */
	public static void main(String[] args)
			throws IOException {
		test1();
		System.out.println("\n___________________________________________");
		test2();
		System.out.println("\n___________________________________________");
		test3();
		System.out.println("\n___________________________________________");
		test4();
	}

	/* no javadoc - demo methods */
	@SuppressWarnings("javadoc")
	private static void test1()
			throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/osnovni.smscr")), StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/* no javadoc - demo methods */
	@SuppressWarnings("javadoc")
	private static void test2()
			throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/zbrajanje.smscr")), StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/* no javadoc - demo methods */
	@SuppressWarnings("javadoc")
	private static void test3()
			throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/brojPoziva.smscr")), StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("\nVrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));

	}

	/* no javadoc - demo methods */
	@SuppressWarnings("javadoc")
	private static void test4() throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/fibonacci.smscr")), StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

}
