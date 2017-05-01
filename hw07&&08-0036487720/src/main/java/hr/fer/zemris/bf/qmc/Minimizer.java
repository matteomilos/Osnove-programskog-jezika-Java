package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;

/**
 * Class <code>Minimizer</code> is used for performing Quine-McCluskey
 * minimization proccess with Pyne-McCluskey approach. Instance of this class
 * contains set of minterms and set of dontcares, list of boolean function
 * variables, and list of minimal forms of boolean function.
 * 
 * @author Matteo Miloš
 *
 */
public class Minimizer {

	/** set of all minterms of the boolean function */
	private Set<Integer> mintermSet;

	/** set of all dontCares of the boolean function */
	private Set<Integer> dontCareSet;

	/** list of function variables */
	private List<String> variables;

	/** list of minimal forms of the boolean function */
	private List<Set<Mask>> minimalForms;

	/**
	 * instance of logger class, used for logging events during minimization
	 * proccess
	 */
	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	/**
	 * Public constructor used for minimization of boolean function specified by
	 * set of minterms, set of dont cares, and list of function variables.
	 * 
	 * @param mintermSet
	 *            set of minterms
	 * @param dontCareSet
	 *            set of dont cares
	 * @param variables
	 *            list of function variables
	 * @throws IllegalArgumentException
	 *             if sets of minterms and dont cares are overlapping or there
	 *             are illegal minterm indexes in the boolean function
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		if (!checkNonOverlapping(mintermSet, dontCareSet)) {
			throw new IllegalArgumentException("Error: set of minterms and don't cares is not disjunct.");
		}
		if (!checkIllegallIndexes(mintermSet, dontCareSet, variables.size())) {
			throw new IllegalArgumentException("There are more than allowed variables.");
		}

		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;

		if (mintermSet.size() > 0) {
			Set<Mask> primCover = findPrimaryImplicants();
			minimalForms = chooseMinimalCover(primCover);
		}

	}

	/**
	 * Method used for first step of Quine-McCluskey algorithm, finding all the
	 * primary implicants of the boolean function.
	 * 
	 * @return set of primary implicants
	 */
	private Set<Mask> findPrimaryImplicants() {
		List<Set<Mask>> newImplicants = createFirstColumn();
		Set<Mask> primaryImplicants = new LinkedHashSet<>();

		while (true) {
			Set<Mask> temporaryPrimary = new LinkedHashSet<>();

			List<Set<Mask>> currentImplicants = new LinkedList<>(newImplicants);
			newImplicants = findNewImplicants(temporaryPrimary, currentImplicants);

			logBeginningOfTheTable();

			logTableColumn(currentImplicants);

			/*-log primaries if we have found any*/
			if (temporaryPrimary.size() > 0) {
				logCurrentPrimaries(temporaryPrimary);
			}

			/*-add newly found primaries*/
			primaryImplicants.addAll(temporaryPrimary);

			/*-if no new implicant found, exit while loop*/
			if (newImplicants.size() == 0) {
				break;
			}
		}

		/*-log all primaries*/
		logPrimaryImplicants(primaryImplicants);

		return primaryImplicants;
	}

	/**
	 * Method used for encapsulating two steps of Quine-McCluskey algorithm.
	 * First, it creates table of minterm coverage by primary implicants, and
	 * using that table it searches for important primary implicants. After
	 * that, if there are still uncovered minterms, it build coverage function p
	 * in form of product of sums, and then, using that product, returns set of
	 * product that have least variables.
	 * 
	 * @param primCover
	 *            set of primary implicants
	 * @return list of minimal forms of the function
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		// Izgradi polja implikanata i minterma (rub tablice):
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);

		// Mapiraj minterm u stupac u kojem se nalazi:
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}

		// Napravi praznu tablicu pokrivenosti:
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);

		// Donji redak tablice: koje sam minterme pokrio?
		boolean[] coveredMinterms = new boolean[minterms.length];

		// Pronađi primarne implikante...
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants, mintermToColumnMap, table,
				coveredMinterms);

		logImportantImplicants(importantSet);

		// Izgradi funkciju pokrivenosti:
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);

		logPFunction(pFunction);

		Set<BitSet> minset = new HashSet<>();
		// // Pronađi minimalne dopune:
		if (pFunction.size() != 0) {
			minset = findMinimalSet(pFunction);
		}
		logMinimalSet(minset);

		// // Izgradi minimalne zapise funkcije:
		List<Set<Mask>> minimalForms = new ArrayList<>();
		if (minset.size() > 0) {
			for (BitSet bs : minset) {
				Set<Mask> set = new LinkedHashSet<>(importantSet);
				bs.stream().forEach(i -> set.add(implicants[i]));
				minimalForms.add(set);
			}
		} else {
			minimalForms.add(importantSet);
		}
		logMinimalForms(minimalForms);
		return minimalForms;
	}

	/**
	 * Getter method used for getting list of minimal forms of boolean function.
	 * 
	 * @return list of minimal forms
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}

	/**
	 * Method used for transforming minimal forms that are in form of sets of
	 * masks, to the list of the strings.
	 * 
	 * @return list of the string representation of minimal forms
	 */
	public List<String> getMinimalFormsAsString() {

		List<String> list = new ArrayList<>();

		if (mintermSet.size() == 0) {
			list.add("Funkcija je kontradikcija");
			return list;
		}

		if (mintermSet.size() + dontCareSet.size() == Math.pow(variables.size(), 2)) {
			list.add("Funkcija je tautologija");
			return list;
		}

		StringBuilder sb = new StringBuilder();
		for (Set<Mask> set : minimalForms) {
			for (Mask mask : set) {
				for (int i = 0; i < mask.size(); i++) {
					if (mask.getValueAt(i) == 0) {
						sb.append("NOT ");

					}
					if (mask.getValueAt(i) != 2) {
						sb.append(variables.get(i));
						sb.append(" AND ");
					}
				}
				if (sb.length() >= 4) {
					sb.setLength(sb.length() - 4);
				}
				sb.append("OR ");
			}
			if (sb.length() >= 3) {
				sb.setLength(sb.length() - 3);
			}
			list.add(sb.toString());
			sb.setLength(0);
		}

		return list;
	}

	/**
	 * Public method used for transforming minimal forms that are in form of
	 * sets of masks to the list of {@link Node} objects.
	 * 
	 * @return list of nodes
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> list = new ArrayList<>();

		if (mintermSet.size() == 0) {
			list.add(new ConstantNode(false));
			return list;
		}

		if (mintermSet.size() + dontCareSet.size() == Math.pow(variables.size(), 2)) {
			list.add(new ConstantNode(true));
			return list;
		}

		for (String string : getMinimalFormsAsString()) {
			Parser parser = new Parser(string);
			list.add(parser.getExpression());
		}

		return list;
	}

	/**
	 * Method that creates minimal set of primary implicants that cover
	 * uncovered minterms.
	 * 
	 * @param pFunction
	 *            list of possible combination that cover uncovered minterms
	 * @return minimal combination that cover uncovered minterms
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {

		Set<BitSet> minimalSet = new LinkedHashSet<>(pFunction.get(0));
		Set<BitSet> helpSet = new LinkedHashSet<>(pFunction.get(0));
		int pFunctionSize = pFunction.size();

		for (int i = 0; i < pFunctionSize - 1; i++) {
			minimalSet.clear();

			for (BitSet bitSet : helpSet) {

				Set<BitSet> set = pFunction.get(i + 1);

				for (BitSet bitSet2 : set) {
					BitSet help = (BitSet) bitSet.clone();
					help.or(bitSet2);
					minimalSet.add(help);
				}
			}

			helpSet.clear();
			helpSet.addAll(minimalSet);
		}
		logSumOfProducts(minimalSet);

		int cardinality = minimalSet.stream().min((a, b) -> Integer.compare(a.cardinality(), b.cardinality()))
				.map(BitSet::cardinality).get();

		minimalSet = minimalSet.stream().filter(e -> e.cardinality() == cardinality)
				.collect(Collectors.toCollection(LinkedHashSet::new));

		return minimalSet;
	}

	/**
	 * Method used for creating function of coverage which is used in next steps
	 * of Quine-McCluskey minimization. It creates list of possible combinations
	 * for covering minterms that weren't covered by primary implicants.
	 * 
	 * @param table
	 *            cover table
	 * @param coveredMinterms
	 *            array of covered minterms
	 * @return list of combination for covering minterms
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {

		List<Set<BitSet>> pFunction = new ArrayList<>();

		for (int i = 0; i < coveredMinterms.length; i++) {

			Set<BitSet> currentColumn = new LinkedHashSet<>();

			for (int j = 0; j < table.length; j++) {

				if (table[j][i] && !coveredMinterms[i]) {
					BitSet current = new BitSet();
					current.set(j);
					currentColumn.add(current);

				}
			}
			if (currentColumn.size() > 0) {
				pFunction.add(currentColumn);
			}
		}
		return pFunction;
	}

	/**
	 * Method used for selecting important primary implicants. It searches for
	 * primary implicants which are the only ones covering some minterm and
	 * returns set of these implicants.
	 * 
	 * @param implicants
	 *            array of all implicants
	 * @param mintermToColumnMap
	 *            map of minterms and implicants
	 * @param table
	 *            cover table of the function
	 * @param coveredMinterms
	 *            array of covered minterms
	 * @return set of all the important primary implicants
	 */
	private Set<Mask> selectImportantPrimaryImplicants(Mask[] implicants, Map<Integer, Integer> mintermToColumnMap,
			boolean[][] table, boolean[] coveredMinterms) {

		Set<Mask> importantPrimaryImplicants = new HashSet<>();

		for (int i = 0; i < table[0].length; i++) {
			int counter = 0;
			int implicantIndex = 0;
			for (int j = 0; j < table.length; j++) {
				if (table[j][i] == true) {
					counter++;
					implicantIndex = j;
				}
			}
			if (counter == 1) {

				Mask primaryImplicant = implicants[implicantIndex];
				importantPrimaryImplicants.add(primaryImplicant);

				for (int minterm : primaryImplicant.getIndexes()) {
					if (mintermToColumnMap.get(minterm) != null) {
						coveredMinterms[mintermToColumnMap.get(minterm)] = true;
					}
				}
			}
		}
		return importantPrimaryImplicants;
	}

	/**
	 * Method that creates cover table of the boolean function. Rows are
	 * representing primary implicants and columns are representing minterms of
	 * the function. Value on the position (i,j) is true if i-th primary
	 * implicant is covering minterm from j-th table column. Otherwise, value is
	 * false.
	 * 
	 * @param implicants
	 *            array of implicants
	 * @param minterms
	 *            array of minterms
	 * @param mintermToColumnMap
	 *            map of minterms and implicants
	 * @return cover table of function
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms,
			Map<Integer, Integer> mintermToColumnMap) {

		boolean[][] coverTable = new boolean[implicants.length][minterms.length];

		for (int i = 0; i < implicants.length; i++) {
			for (int j = 0; j < minterms.length; j++) {
				if (implicants[i].getIndexes().contains(minterms[j])) {
					coverTable[i][j] = true;
				}
			}
		}
		return coverTable;
	}

	/**
	 * Method called from the method {@link #findPrimaryImplicants()}, used for
	 * finding all possible new implicant after each iteration over the
	 * Quine-McCluskey table.
	 * 
	 * @param temporaryPrimary
	 *            current primary implicants
	 * @param currentImplicants
	 *            all current implicants
	 * @return list of new implicants
	 */
	private List<Set<Mask>> findNewImplicants(Set<Mask> temporaryPrimary, List<Set<Mask>> currentImplicants) {

		List<Set<Mask>> newImplicants = new ArrayList<>();
		int currentImplicantsSize = currentImplicants.size();

		for (int i = 0; i < currentImplicantsSize; i++) {
			Set<Mask> maskSet = new LinkedHashSet<>();

			/*-each mask with 'i' number of 'ones' ...(continued 4 lines later)*/
			for (Mask mask : currentImplicants.get(i)) {

				/*-if our current mask has max number of zeroes, we stop finding masks with more zeroes*/
				if (i + 1 != currentImplicantsSize) {

					/*-is trying to be combined with each mask with 'i+1' number of zeroes*/
					for (Mask mask2 : currentImplicants.get(i + 1)) {

						Optional<Mask> opt = mask.combineWith(mask2);
						if (opt.isPresent()) {
							mask.setCombined(true);
							mask2.setCombined(true);
							maskSet.add(opt.get());
						}
					}
				}
				/*-add to primaries all that are not combined and are not "don't care"*/
				if (!mask.isCombined() && !mask.isDontCare()) {
					temporaryPrimary.add(mask);
				}

			}
			/*-add newly founded implicants*/
			if (maskSet.size() != 0) {
				newImplicants.add(maskSet);
			}
		}
		return newImplicants;
	}

	/**
	 * Method for creating first column of Quine-McCluskey table from sets of
	 * minterms and dontcares.
	 * 
	 * @return list of products which represent first column
	 */
	private List<Set<Mask>> createFirstColumn() {

		List<Set<Mask>> firstColumn = new LinkedList<>();

		Map<Integer, Boolean> mintermAndDontCareMap = new LinkedHashMap<>();
		for (int minterm : mintermSet) {
			mintermAndDontCareMap.put(minterm, false);
		}
		for (int dontCare : dontCareSet) {
			mintermAndDontCareMap.put(dontCare, true);
		}

		int variablesSize = variables.size();
		for (int key : mintermAndDontCareMap.keySet()) {

			Mask currentMask = new Mask(key, variablesSize, mintermAndDontCareMap.get(key));
			int countOfOnes = currentMask.countOfOnes();
			while (firstColumn.size() < countOfOnes) {
				firstColumn.add(new LinkedHashSet<>());
			}

			if (firstColumn.size() == countOfOnes || firstColumn.get(countOfOnes) == null) {
				firstColumn.add(countOfOnes, new LinkedHashSet<Mask>());
			}

			firstColumn.get(countOfOnes).add(currentMask);
		}

		return firstColumn;
	}

	/**
	 * Method used in constructor for checking if there are illegal minterms
	 * given. Illegal minterms are those whose index is greater than biggest
	 * possible from given list of variables
	 * 
	 * @param mintermSet
	 *            list of minterms
	 * @param dontCareSet
	 *            list of dont cares
	 * @param size
	 *            number of variables
	 * @return true if everything is okay, false otherwise
	 */
	private boolean checkIllegallIndexes(Set<Integer> mintermSet, Set<Integer> dontCareSet, int size) {
		Set<Integer> mintermAndDontCareUnion = new HashSet<>(mintermSet);
		mintermAndDontCareUnion.addAll(dontCareSet);

		double maxMinterm = Math.pow(2, size) - 1;
		for (Integer integer : mintermAndDontCareUnion) {
			if (integer > maxMinterm) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method used for checking if sets of minterms and dont cares have common
	 * elements which is not allowed.
	 * 
	 * @param mintermSet
	 *            set of minterms
	 * @param dontCareSet
	 *            set of dont cares
	 * @return true if there is no common elements, false otherwise
	 */
	private boolean checkNonOverlapping(Set<Integer> mintermSet, Set<Integer> dontCareSet) {
		return Collections.disjoint(mintermSet, dontCareSet);
	}

	/**
	 * Method used for logging beginning of the table.
	 */
	private void logBeginningOfTheTable() {
		LOG.log(Level.FINER, () -> "Stupac tablice:");
		LOG.log(Level.FINER, () -> "=================================");
	}

	/**
	 * Method used for logging all the primary implicants
	 * 
	 * @param primary
	 *            primary implicants
	 */
	private void logPrimaryImplicants(Set<Mask> primary) {
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Svi primarni implikanti:");
		for (Mask mask : primary) {
			LOG.log(Level.FINE, mask::toString);
		}
	}

	/**
	 * Method used for logging columns of the table from implicants.
	 * 
	 * @param current
	 *            current implicants
	 */
	private void logTableColumn(List<Set<Mask>> current) {
		int i = 1;
		for (Set<Mask> set : current) {
			for (Mask mask : set) {
				LOG.log(Level.FINER, mask::toString);
			}
			if (i++ != current.size()) {
				LOG.log(Level.FINER, () -> "-------------------------------");
			}
		}
		LOG.log(Level.FINER, () -> "");

	}

	/**
	 * Method used for logging current primary implicants.
	 * 
	 * @param primaryHelp
	 *            list of primary implicants
	 */
	private void logCurrentPrimaries(Set<Mask> primaryHelp) {
		for (Mask mask : primaryHelp) {
			LOG.log(Level.FINEST, () -> "Pronašao primarni implikant: " + mask.toString());
		}
		LOG.log(Level.FINEST, () -> "");

	}

	/**
	 * Method used for logging minimal forms of the function.
	 * 
	 * @param minimalForms
	 *            list of minimal forms of the function
	 */
	private void logMinimalForms(List<Set<Mask>> minimalForms) {
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Minimalni oblici funkcije su:");
		for (Set<Mask> set : minimalForms) {
			LOG.log(Level.FINE, set::toString);

		}
	}

	/**
	 * Method used for logging minimal coverages.
	 * 
	 * @param minset
	 *            minimal coverages
	 */
	private void logMinimalSet(Set<BitSet> minset) {
		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "Minimalna pokrivanja još trebaju:");
		LOG.log(Level.FINER, minset::toString);
	}

	/**
	 * Method used for logging list that was created by
	 * {@link #buildPFunction(boolean[][], boolean[])}.
	 * 
	 * @param pFunction
	 *            list of p function
	 */
	private void logPFunction(List<Set<BitSet>> pFunction) {
		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "p funkcija je: ");
		LOG.log(Level.FINER, pFunction::toString);
	}

	/**
	 * Method used for logging p-function as sum of products.
	 * 
	 * @param minimalSet
	 *            minimal set of p-function
	 */
	private void logSumOfProducts(Set<BitSet> minimalSet) {
		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "Nakon prevorbe p-funkcije u sumu produkata:");
		LOG.log(Level.FINER, minimalSet::toString);
	}

	/**
	 * Method used for logging all the important primary implicants.
	 * 
	 * @param importantSet
	 *            set of important primary implicants
	 */
	private void logImportantImplicants(Set<Mask> importantSet) {
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Bitni primarni implikanti su: ");
		for (Mask mask : importantSet) {
			LOG.log(Level.FINE, mask::toString);
		}

	}
}
