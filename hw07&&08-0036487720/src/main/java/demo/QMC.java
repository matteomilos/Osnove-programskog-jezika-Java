package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.utils.Util;

/**
 * Class <code>QMC</code> is used as demonstration program for implementation of
 * Quine-McCluskey minimizator with Pyne-McCluskey approach.
 * 
 * @author Matteo Miloš
 *
 */
public class QMC {
	/**
	 * Entry point to a program.
	 *
	 * @param args
	 *            command line arguments are not uses in this program
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print(">");

		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			if (line.equals("quit")) {
				System.exit(0);
			}

			if (!line.contains("=")) {
				System.out.println("Error: function is in invalid form.");
				continue;
			}

			try {
				String[] split = line.split("=", 2);
				String leftSide = split[0].substring(split[0].indexOf('(') + 1, split[0].indexOf(')')).trim();
				List<String> variables = Arrays.asList(leftSide.split("\\s*,\\s*"));
				Set<Integer> minterms = new HashSet<>();
				Set<Integer> dontCares = new HashSet<>();

				String rightSide = split[1].trim();

				if (rightSide.split("\\s*\\|\\s*").length > 1) {
					minterms = loadMintermsOrDontCares(rightSide.split("\\s*\\|\\s*")[0], variables);
					dontCares = loadMintermsOrDontCares(rightSide.split("\\s*\\|\\s*")[1], variables);

				} else {
					minterms = loadMintermsOrDontCares(rightSide, variables);
				}

				Minimizer m = new Minimizer(minterms, dontCares, variables);

				int i = 1;
				List<String> minimalForms = m.getMinimalFormsAsString();

				for (String str : minimalForms) {
					System.out.println(i++ + ". " + str);
				}
				System.out.print(">");

			} catch (Exception exc) {
				System.out.println(exc.getMessage());
				System.out.print(">");

			}
		}
		sc.close();
	}

	/**
	 * Method used for loading minterms or dontcares from passed string.
	 * 
	 * @param rightSide
	 *            string which represents minterms or dontcares
	 * @param variables
	 *            list of function variables
	 * @return set of minterms/dont cares.
	 */
	private static Set<Integer> loadMintermsOrDontCares(String rightSide, List<String> variables) {
		Set<Integer> mintOrDonts = new HashSet<>();

		if (rightSide.trim().charAt(0) == '[') {

			if (rightSide.split(",").length > 1) {
				String[] mint = rightSide.trim().substring(1, rightSide.length() - 1).split("\\s*,\\s*");

				for (String integer : mint) {
					mintOrDonts.add(new Integer(integer));
				}

			} else {
				mintOrDonts.add(new Integer(rightSide.trim().substring(1, rightSide.length() - 1)));
			}

		} else {
			Parser parser = new Parser(rightSide.trim());
			mintOrDonts = Util.toSumOfMinterms(variables, parser.getExpression());
		}

		return mintOrDonts;
	}

}
